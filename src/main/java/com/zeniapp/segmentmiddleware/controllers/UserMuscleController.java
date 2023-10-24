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
import com.zeniapp.segmentmiddleware.dtos.MuscleDto;
import com.zeniapp.segmentmiddleware.dtos.MuscleQueryParamsDto;
import com.zeniapp.segmentmiddleware.dtos.CountResponseDto;
import com.zeniapp.segmentmiddleware.dtos.ErrorResponseDto;
import com.zeniapp.segmentmiddleware.dtos.FindManyResponseDto;
import com.zeniapp.segmentmiddleware.entities.Muscle;
import com.zeniapp.segmentmiddleware.exceptions.ResourceNotFoundException;
import com.zeniapp.segmentmiddleware.exceptions.WrongPayloadException;
import com.zeniapp.segmentmiddleware.services.MuscleService;
import com.zeniapp.segmentmiddleware.utils.MuscleUtils;

@Slf4j
@RestController
@RequestMapping("/api/v1/user/muscles")
public class UserMuscleController {
    @Autowired
    private MuscleService muscleService;
    
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/count")
    public ResponseEntity<?> count(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String origin,
        @RequestParam(required = false) String insertion,
        @RequestParam(required = false) String functions,
        @RequestParam(required = false) String form,
        @RequestParam(required = false) String dimension
    ) {
        try {
            MuscleQueryParamsDto muscleQueryParamsDto = new MuscleQueryParamsDto(
                null, null, null, null,
                name, origin, insertion,
                functions, form, dimension
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
            UserMuscleController.log.error("error occurred counting the muscles");
            UserMuscleController.log.error("error message is " + exception.getMessage());
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
            UserMuscleController.log.error("error occurred retrieving the muscle");
            UserMuscleController.log.error("error message is " + exception.getMessage());
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
        @RequestParam(required = false) String dimension
    ) {
        try {
            MuscleQueryParamsDto muscleQueryParamsDto = new MuscleQueryParamsDto(
                offset, limit, sortField, sortDirection,
                name, origin, insertion,
                functions, form, dimension
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
            UserMuscleController.log.error("error occurred retrieving the muscles");
            UserMuscleController.log.error("error message is " + exception.getMessage());
            return new ResponseEntity<ErrorResponseDto>(MuscleUtils.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}