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
import com.zeniapp.segmentmiddleware.dtos.MuscleDto;
import com.zeniapp.segmentmiddleware.dtos.MuscleQueryParamsDto;
import com.zeniapp.segmentmiddleware.dtos.CountResponseDto;
import com.zeniapp.segmentmiddleware.dtos.CreateMuscleDto;
import com.zeniapp.segmentmiddleware.dtos.ErrorResponseDto;
import com.zeniapp.segmentmiddleware.dtos.FindManyResponseDto;
import com.zeniapp.segmentmiddleware.dtos.UpdateMuscleDto;
import com.zeniapp.segmentmiddleware.entities.Exercise;
import com.zeniapp.segmentmiddleware.entities.Muscle;
import com.zeniapp.segmentmiddleware.exceptions.DuplicateFieldsException;
import com.zeniapp.segmentmiddleware.exceptions.ResourceNotFoundException;
import com.zeniapp.segmentmiddleware.exceptions.WrongPayloadException;
import com.zeniapp.segmentmiddleware.services.ExerciseService;
import com.zeniapp.segmentmiddleware.services.MuscleService;
import com.zeniapp.segmentmiddleware.utils.MuscleUtils;

@Slf4j
@RestController
@RequestMapping("/api/v1/admin/muscles")
public class AdminMuscleController {
    @Autowired
    private MuscleService muscleService;
    
    @Autowired
    private ExerciseService exerciseService;
    
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/count")
    public ResponseEntity<?> count(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String origin,
        @RequestParam(required = false) String insertion,
        @RequestParam(required = false) String functions,
        @RequestParam(required = false) String form,
        @RequestParam(required = false) String dimension,
        @RequestParam(required = false) Boolean isPublished
    ) {
        try {
            MuscleQueryParamsDto muscleQueryParamsDto = new MuscleQueryParamsDto(
                null, null, null, null,
                name, origin, insertion,
                functions, form, dimension,
                isPublished
            );
            
            MuscleUtils.validateMuscleQueryParamsDto(muscleQueryParamsDto);

            CountResponseDto countDto = new CountResponseDto();
            countDto.setTotal(this.muscleService.count(muscleQueryParamsDto));

            return new ResponseEntity<CountResponseDto>(countDto, HttpStatus.OK);
        }
        catch (WrongPayloadException wrongPayloadException) {
            return new ResponseEntity<ErrorResponseDto>(wrongPayloadException.getErrorResponseDto(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception exception) {
            AdminMuscleController.log.error("error occurred counting the muscles");
            AdminMuscleController.log.error("error message is " + exception.getMessage());
            return new ResponseEntity<ErrorResponseDto>(MuscleUtils.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateMuscleDto createMuscleDto, BindingResult bindingResult) {
        try {
            MuscleUtils.validateCreateOrUpdateMuscleDto(bindingResult);

            Optional<Muscle> muscleByName = this.muscleService.findByName(createMuscleDto.getName());

            MuscleUtils.manageDuplicateFields(muscleByName);

            Muscle muscleToCreate = this.modelMapper.map(createMuscleDto, Muscle.class);
            muscleToCreate.setIsDeleted(false);
            
            Set<String> exerciseIdsSet = new HashSet<String>(createMuscleDto.getMainExercises());
            List<Exercise> exercisesByIds = this.exerciseService.findAllByIds(exerciseIdsSet);
            muscleToCreate.setMainExercises(exercisesByIds);

            Muscle createdMuscle = this.muscleService.save(muscleToCreate);

            return new ResponseEntity<MuscleDto>(this.modelMapper.map(createdMuscle, MuscleDto.class), HttpStatus.CREATED);
        }
        catch (WrongPayloadException wrongPayloadException) {
            return new ResponseEntity<ErrorResponseDto>(wrongPayloadException.getErrorResponseDto(), HttpStatus.BAD_REQUEST);
        }
        catch (DuplicateFieldsException duplicateFieldsException) {
            return new ResponseEntity<ErrorResponseDto>(duplicateFieldsException.getErrorResponseDto(), HttpStatus.CONFLICT);
        }
        catch (Exception exception) {
            AdminMuscleController.log.error("error occurred creating the muscle");
            AdminMuscleController.log.error("error message is " + exception.getMessage());
            return new ResponseEntity<ErrorResponseDto>(MuscleUtils.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findOne(@PathVariable String id) {
        try {
            Muscle muscleFound = this.muscleService.findOne(id).orElseThrow(ResourceNotFoundException::new);

            MuscleDto muscleDto = this.modelMapper.map(muscleFound, MuscleDto.class);

            return new ResponseEntity<MuscleDto>(muscleDto, HttpStatus.OK);
        }
        catch (ResourceNotFoundException resourceNotFoundException) {
            return new ResponseEntity<ErrorResponseDto>(resourceNotFoundException.getErrorResponseDto(), HttpStatus.NOT_FOUND);
        }
        catch (Exception exception) {
            AdminMuscleController.log.error("error occurred retrieving the muscle");
            AdminMuscleController.log.error("error message is " + exception.getMessage());
            return new ResponseEntity<ErrorResponseDto>(MuscleUtils.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping()
    public ResponseEntity<?> findMany(
        @RequestParam(required = false, defaultValue = "0") Integer offset,
        @RequestParam(required = false, defaultValue = "10") Integer limit,
        @RequestParam(required = false, defaultValue = "name") String sortField,
        @RequestParam(required = false, defaultValue = "asc") String sortDirection,
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String origin,
        @RequestParam(required = false) String insertion,
        @RequestParam(required = false) String functions,
        @RequestParam(required = false) String form,
        @RequestParam(required = false) String dimension,
        @RequestParam(required = false) Boolean isPublished
    ) {
        try {
            MuscleQueryParamsDto muscleQueryParamsDto = new MuscleQueryParamsDto(
                offset, limit, sortField, sortDirection,
                name, origin, insertion,
                functions, form, dimension,
                isPublished
            );
            
            MuscleUtils.validateMuscleQueryParamsDto(muscleQueryParamsDto);

            List<Muscle> musclesFound = this.muscleService.findMany(muscleQueryParamsDto);
            List<MuscleDto> muscleDtos = musclesFound.stream().map(e -> this.modelMapper.map(e, MuscleDto.class)).toList();
            Long total = this.muscleService.count(muscleQueryParamsDto);

            FindManyResponseDto<MuscleDto> findAllResponseDto = new FindManyResponseDto<MuscleDto>(total, muscleDtos);

            return new ResponseEntity<FindManyResponseDto<MuscleDto>>(findAllResponseDto, HttpStatus.OK);
        }
        catch (WrongPayloadException wrongPayloadException) {
            return new ResponseEntity<ErrorResponseDto>(wrongPayloadException.getErrorResponseDto(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception exception) {
            AdminMuscleController.log.error("error occurred retrieving the muscles");
            AdminMuscleController.log.error("error message is " + exception.getMessage());
            return new ResponseEntity<ErrorResponseDto>(MuscleUtils.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOne(@PathVariable String id, @Valid @RequestBody UpdateMuscleDto updateMuscleDto, BindingResult bindingResult) {
        try {
            MuscleUtils.validateCreateOrUpdateMuscleDto(bindingResult);

            Muscle muscleFound = this.muscleService.findOne(id).orElseThrow(ResourceNotFoundException::new);

            Optional<Muscle> muscleByName = this.muscleService.findByIdNotAndName(id, updateMuscleDto.getName());
            
            MuscleUtils.manageDuplicateFields(muscleByName);

            muscleFound.setName(updateMuscleDto.getName());
            muscleFound.setOrigin(updateMuscleDto.getOrigin());
            muscleFound.setInsertion(updateMuscleDto.getInsertion());
            muscleFound.setFunctions(updateMuscleDto.getFunctions());
            muscleFound.setDimension(updateMuscleDto.getDimension());
            muscleFound.setForm(updateMuscleDto.getForm());

            Set<String> exerciseIdsSet = new HashSet<String>(updateMuscleDto.getMainExercises());
            List<Exercise> exercisesByIds = this.exerciseService.findAllByIds(exerciseIdsSet);
            muscleFound.setMainExercises(exercisesByIds);

            muscleFound.setIsPublished(updateMuscleDto.getIsPublished());

            Muscle muscleUpdated = this.muscleService.save(muscleFound);

            return new ResponseEntity<MuscleDto>(this.modelMapper.map(muscleUpdated, MuscleDto.class), HttpStatus.OK);
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
            AdminMuscleController.log.error("error occurred updating the muscle");
            AdminMuscleController.log.error("error message is " + exception.getMessage());
            return new ResponseEntity<ErrorResponseDto>(MuscleUtils.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable String id) {
        try {
            Muscle muscleToDelete = this.muscleService.findOne(id).orElseThrow(ResourceNotFoundException::new);

            muscleToDelete.setIsDeleted(true);

            this.muscleService.save(muscleToDelete);

            MuscleDto muscleDto = this.modelMapper.map(muscleToDelete, MuscleDto.class);

            return new ResponseEntity<MuscleDto>(muscleDto, HttpStatus.OK);
        }
        catch (ResourceNotFoundException resourceNotFoundException) {
            return new ResponseEntity<ErrorResponseDto>(resourceNotFoundException.getErrorResponseDto(), HttpStatus.NOT_FOUND);
        }
        catch (Exception exception) {
            AdminMuscleController.log.error("error occurred during the muscle deletion");
            AdminMuscleController.log.error("error message is " + exception.getMessage());
            return new ResponseEntity<ErrorResponseDto>(MuscleUtils.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
