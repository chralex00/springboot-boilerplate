package com.zeniapp.segmentmiddleware.controllers;

import jakarta.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.zeniapp.segmentmiddleware.dtos.ExerciseDto;
import com.zeniapp.segmentmiddleware.dtos.ExerciseQueryParamsDto;
import com.zeniapp.segmentmiddleware.dtos.CountResponseDto;
import com.zeniapp.segmentmiddleware.dtos.CreateExerciseDto;
import com.zeniapp.segmentmiddleware.dtos.ErrorResponseDto;
import com.zeniapp.segmentmiddleware.dtos.FindManyResponseDto;
import com.zeniapp.segmentmiddleware.dtos.UpdateExerciseDto;
import com.zeniapp.segmentmiddleware.entities.Exercise;
import com.zeniapp.segmentmiddleware.entities.Muscle;
import com.zeniapp.segmentmiddleware.exceptions.DuplicateFieldsException;
import com.zeniapp.segmentmiddleware.exceptions.ResourceNotFoundException;
import com.zeniapp.segmentmiddleware.exceptions.WrongPayloadException;
import com.zeniapp.segmentmiddleware.services.ExerciseService;
import com.zeniapp.segmentmiddleware.services.MuscleService;
import com.zeniapp.segmentmiddleware.utils.ExerciseUtils;

@Slf4j
@RestController
@RequestMapping("/api/v1/admin/exercises")
public class AdminExerciseController {
    @Autowired
    private ExerciseService exerciseService;
    
    @Autowired
    private MuscleService muscleService;
    
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/count")
    public ResponseEntity<?> count(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String difficulty,
        @RequestParam(required = false) String category,
        @RequestParam(required = false) String type,
        @RequestParam(required = false) List<String> agonistMuscles,
        @RequestParam(required = false) List<String> antagonistMuscles,
        @RequestParam(required = false) List<String> synergisticMuscles,
        @RequestParam(required = false) List<String> fixatorMuscles,
        @RequestParam(required = false) Boolean isPublished
    ) {
        try {
            ExerciseQueryParamsDto exerciseQueryParamsDto = new ExerciseQueryParamsDto(
                null, null, null, null,
                name, difficulty, category,
                type, agonistMuscles, antagonistMuscles,
                synergisticMuscles, fixatorMuscles,
                isPublished
            );
            
            ExerciseUtils.validateExerciseQueryParamsDto(exerciseQueryParamsDto);

            CountResponseDto countDto = new CountResponseDto();
            countDto.setTotal(this.exerciseService.count(exerciseQueryParamsDto));

            return new ResponseEntity<CountResponseDto>(countDto, HttpStatus.OK);
        }
        catch (WrongPayloadException wrongPayloadException) {
            return new ResponseEntity<ErrorResponseDto>(wrongPayloadException.getErrorResponseDto(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception exception) {
            AdminExerciseController.log.error("error occurred counting the exercises");
            AdminExerciseController.log.error("error message is " + exception.getMessage());
            return new ResponseEntity<ErrorResponseDto>(ExerciseUtils.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateExerciseDto createExerciseDto, BindingResult bindingResult) {
        try {
            ExerciseUtils.validateCreateOrUpdateExerciseDto(bindingResult);

            Optional<Exercise> exerciseByName = this.exerciseService.findByName(createExerciseDto.getName());

            ExerciseUtils.manageDuplicateFields(exerciseByName);

            Exercise exerciseToCreate = this.modelMapper.map(createExerciseDto, Exercise.class);
            exerciseToCreate.setIsDeleted(false);

            Set<String> agonistMuscleIdsSet = new HashSet<String>(createExerciseDto.getAgonistMuscles());
            List<Muscle> agonistMuscleIds = this.muscleService.findAllByIds(agonistMuscleIdsSet);
            exerciseToCreate.setAgonistMuscles(agonistMuscleIds);

            Set<String> antagonistMuscleIdsSet = new HashSet<String>(createExerciseDto.getAntagonistMuscles());
            List<Muscle> antagonistMuscleIds = this.muscleService.findAllByIds(antagonistMuscleIdsSet);
            exerciseToCreate.setAntagonistMuscles(antagonistMuscleIds);

            Set<String> synergisticMuscleIdsSet = new HashSet<String>(createExerciseDto.getSynergisticMuscles());
            List<Muscle> synergisticMuscleIds = this.muscleService.findAllByIds(synergisticMuscleIdsSet);
            exerciseToCreate.setSynergisticMuscles(synergisticMuscleIds);

            Set<String> fixatorMuscleIdsSet = new HashSet<String>(createExerciseDto.getFixatorMuscles());
            List<Muscle> fixatorMuscleIds = this.muscleService.findAllByIds(fixatorMuscleIdsSet);
            exerciseToCreate.setFixatorMuscles(fixatorMuscleIds);

            Exercise createdExercise = this.exerciseService.save(exerciseToCreate);

            return new ResponseEntity<ExerciseDto>(this.modelMapper.map(createdExercise, ExerciseDto.class), HttpStatus.CREATED);
        }
        catch (WrongPayloadException wrongPayloadException) {
            return new ResponseEntity<ErrorResponseDto>(wrongPayloadException.getErrorResponseDto(), HttpStatus.BAD_REQUEST);
        }
        catch (DuplicateFieldsException duplicateFieldsException) {
            return new ResponseEntity<ErrorResponseDto>(duplicateFieldsException.getErrorResponseDto(), HttpStatus.CONFLICT);
        }
        catch (Exception exception) {
            AdminExerciseController.log.error("error occurred creating the exercise");
            AdminExerciseController.log.error("error message is " + exception.getMessage());
            return new ResponseEntity<ErrorResponseDto>(ExerciseUtils.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findOne(@PathVariable String id) {
        try {
            Exercise exerciseFound = this.exerciseService.findOne(id).orElseThrow(ResourceNotFoundException::new);

            ExerciseDto exerciseDto = this.modelMapper.map(exerciseFound, ExerciseDto.class);

            return new ResponseEntity<ExerciseDto>(exerciseDto, HttpStatus.OK);
        }
        catch (ResourceNotFoundException resourceNotFoundException) {
            return new ResponseEntity<ErrorResponseDto>(resourceNotFoundException.getErrorResponseDto(), HttpStatus.NOT_FOUND);
        }
        catch (Exception exception) {
            AdminExerciseController.log.error("error occurred retrieving the exercise");
            AdminExerciseController.log.error("error message is " + exception.getMessage());
            return new ResponseEntity<ErrorResponseDto>(ExerciseUtils.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping()
    public ResponseEntity<?> findMany(
        @RequestParam(required = false, defaultValue = "0") Integer offset,
        @RequestParam(required = false, defaultValue = "10") Integer limit,
        @RequestParam(required = false, defaultValue = "name") String sortField,
        @RequestParam(required = false, defaultValue = "asc") String sortDirection,
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String difficulty,
        @RequestParam(required = false) String category,
        @RequestParam(required = false) String type,
        @RequestParam(required = false) List<String> agonistMuscles,
        @RequestParam(required = false) List<String> antagonistMuscles,
        @RequestParam(required = false) List<String> synergisticMuscles,
        @RequestParam(required = false) List<String> fixatorMuscles,
        @RequestParam(required = false) Boolean isPublished
    ) {
        try {
            ExerciseQueryParamsDto exerciseQueryParamsDto = new ExerciseQueryParamsDto(
                offset, limit, sortField, sortDirection,
                name, difficulty, category,
                type, agonistMuscles, antagonistMuscles,
                synergisticMuscles, fixatorMuscles,
                isPublished
            );
            
            ExerciseUtils.validateExerciseQueryParamsDto(exerciseQueryParamsDto);

            List<Exercise> exercisesFound = this.exerciseService.findMany(exerciseQueryParamsDto);
            List<ExerciseDto> exerciseDtos = exercisesFound.stream().map(e -> this.modelMapper.map(e, ExerciseDto.class)).toList();
            Long total = this.exerciseService.count(exerciseQueryParamsDto);

            FindManyResponseDto<ExerciseDto> findAllResponseDto = new FindManyResponseDto<ExerciseDto>(total, exerciseDtos);

            return new ResponseEntity<FindManyResponseDto<ExerciseDto>>(findAllResponseDto, HttpStatus.OK);
        }
        catch (WrongPayloadException wrongPayloadException) {
            return new ResponseEntity<ErrorResponseDto>(wrongPayloadException.getErrorResponseDto(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception exception) {
            AdminExerciseController.log.error("error occurred retrieving the exercises");
            AdminExerciseController.log.error("error message is " + exception.getMessage());
            return new ResponseEntity<ErrorResponseDto>(ExerciseUtils.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOne(@PathVariable String id, @Valid @RequestBody UpdateExerciseDto updateExerciseDto, BindingResult bindingResult) {
        try {
            ExerciseUtils.validateCreateOrUpdateExerciseDto(bindingResult);

            Exercise exerciseFound = this.exerciseService.findOne(id).orElseThrow(ResourceNotFoundException::new);

            Optional<Exercise> exerciseByName = this.exerciseService.findByIdNotAndName(id, updateExerciseDto.getName());
            
            ExerciseUtils.manageDuplicateFields(exerciseByName);

            exerciseFound.setName(updateExerciseDto.getName());
            exerciseFound.setExecution(updateExerciseDto.getExecution());
            exerciseFound.setCommonErrors(updateExerciseDto.getCommonErrors());
            exerciseFound.setDifficulty(updateExerciseDto.getDifficulty());
            exerciseFound.setCategory(updateExerciseDto.getCategory());
            exerciseFound.setType(updateExerciseDto.getType());

            
            Set<String> agonistMuscleIdsSet = new HashSet<String>(updateExerciseDto.getAgonistMuscles());
            List<Muscle> agonistMuscleIds = this.muscleService.findAllByIds(agonistMuscleIdsSet);
            exerciseFound.setAgonistMuscles(agonistMuscleIds);

            Set<String> antagonistMuscleIdsSet = new HashSet<String>(updateExerciseDto.getAntagonistMuscles());
            List<Muscle> antagonistMuscleIds = this.muscleService.findAllByIds(antagonistMuscleIdsSet);
            exerciseFound.setAntagonistMuscles(antagonistMuscleIds);

            Set<String> synergisticMuscleIdsSet = new HashSet<String>(updateExerciseDto.getSynergisticMuscles());
            List<Muscle> synergisticMuscleIds = this.muscleService.findAllByIds(synergisticMuscleIdsSet);
            exerciseFound.setSynergisticMuscles(synergisticMuscleIds);

            Set<String> fixatorMuscleIdsSet = new HashSet<String>(updateExerciseDto.getFixatorMuscles());
            List<Muscle> fixatorMuscleIds = this.muscleService.findAllByIds(fixatorMuscleIdsSet);
            exerciseFound.setFixatorMuscles(fixatorMuscleIds);

            exerciseFound.setIsPublished(updateExerciseDto.getIsPublished());

            Exercise exerciseUpdated = this.exerciseService.save(exerciseFound);

            return new ResponseEntity<ExerciseDto>(this.modelMapper.map(exerciseUpdated, ExerciseDto.class), HttpStatus.OK);
        }
        catch (WrongPayloadException wrongPayloadException) {
            return new ResponseEntity<ErrorResponseDto>(wrongPayloadException.getErrorResponseDto(), HttpStatus.BAD_REQUEST);
        }
        catch (ResourceNotFoundException resourceNotFoundException) {
            return new ResponseEntity<ErrorResponseDto>(resourceNotFoundException.getErrorResponseDto(), HttpStatus.NOT_FOUND);
        }
        catch (DuplicateFieldsException duplicateFieldsException) {
            return new ResponseEntity<ErrorResponseDto>(duplicateFieldsException.getErrorResponseDto(), HttpStatus.NOT_FOUND);
        }
        catch (Exception exception) {
            AdminExerciseController.log.error("error occurred updating the exercise");
            AdminExerciseController.log.error("error message is " + exception.getMessage());
            return new ResponseEntity<ErrorResponseDto>(ExerciseUtils.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable String id) {
        try {
            Exercise exerciseToDelete = this.exerciseService.findOne(id).orElseThrow(ResourceNotFoundException::new);

            exerciseToDelete.setIsDeleted(true);

            this.exerciseService.save(exerciseToDelete);

            ExerciseDto exerciseDto = this.modelMapper.map(exerciseToDelete, ExerciseDto.class);

            return new ResponseEntity<ExerciseDto>(exerciseDto, HttpStatus.OK);
        }
        catch (ResourceNotFoundException resourceNotFoundException) {
            return new ResponseEntity<ErrorResponseDto>(resourceNotFoundException.getErrorResponseDto(), HttpStatus.NOT_FOUND);
        }
        catch (Exception exception) {
            AdminExerciseController.log.error("error occurred during the exercise deletion");
            AdminExerciseController.log.error("error message is " + exception.getMessage());
            return new ResponseEntity<ErrorResponseDto>(ExerciseUtils.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
