package com.zeniapp.segmentmiddleware.controllers;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.zeniapp.segmentmiddleware.dtos.ExerciseDto;
import com.zeniapp.segmentmiddleware.dtos.ExerciseQueryParamsDto;
import com.zeniapp.segmentmiddleware.dtos.CountResponseDto;
import com.zeniapp.segmentmiddleware.dtos.ErrorResponseDto;
import com.zeniapp.segmentmiddleware.dtos.FindManyResponseDto;
import com.zeniapp.segmentmiddleware.entities.Exercise;
import com.zeniapp.segmentmiddleware.exceptions.ResourceNotFoundException;
import com.zeniapp.segmentmiddleware.exceptions.WrongPayloadException;
import com.zeniapp.segmentmiddleware.services.ExerciseService;
import com.zeniapp.segmentmiddleware.utils.ExerciseUtils;

@Slf4j
@RestController
@RequestMapping("/api/v1/user/exercises")
public class UserExerciseController {
    @Autowired
    private ExerciseService exerciseService;
    
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
        @RequestParam(required = false) List<String> fixatorMuscles
    ) {
        try {
            ExerciseQueryParamsDto exerciseQueryParamsDto = new ExerciseQueryParamsDto(
                null, null, null, null,
                name, difficulty, category,
                type, agonistMuscles, antagonistMuscles,
                synergisticMuscles, fixatorMuscles
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
            UserExerciseController.log.error("error occurred counting the exercises");
            UserExerciseController.log.error("error message is " + exception.getMessage());
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
            UserExerciseController.log.error("error occurred retrieving the exercise");
            UserExerciseController.log.error("error message is " + exception.getMessage());
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
        @RequestParam(required = false) List<String> fixatorMuscles
    ) {
        try {
            ExerciseQueryParamsDto exerciseQueryParamsDto = new ExerciseQueryParamsDto(
                offset, limit, sortField, sortDirection,
                name, difficulty, category,
                type, agonistMuscles, antagonistMuscles,
                synergisticMuscles, fixatorMuscles
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
            UserExerciseController.log.error("error occurred retrieving the exercises");
            UserExerciseController.log.error("error message is " + exception.getMessage());
            return new ResponseEntity<ErrorResponseDto>(ExerciseUtils.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}