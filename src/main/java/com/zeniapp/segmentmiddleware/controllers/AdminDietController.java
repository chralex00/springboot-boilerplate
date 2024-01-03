package com.zeniapp.segmentmiddleware.controllers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
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
import com.zeniapp.segmentmiddleware.dtos.DietDto;
import com.zeniapp.segmentmiddleware.dtos.DietQueryParamsDto;
import com.zeniapp.segmentmiddleware.dtos.UpdateDietDto;
import com.zeniapp.segmentmiddleware.dtos.CountResponseDto;
import com.zeniapp.segmentmiddleware.dtos.CreateDietDto;
import com.zeniapp.segmentmiddleware.dtos.ErrorResponseDto;
import com.zeniapp.segmentmiddleware.dtos.FindManyResponseDto;
import com.zeniapp.segmentmiddleware.entities.Diet;
import com.zeniapp.segmentmiddleware.exceptions.DuplicateFieldsException;
import com.zeniapp.segmentmiddleware.exceptions.ResourceNotFoundException;
import com.zeniapp.segmentmiddleware.exceptions.WrongPayloadException;
import com.zeniapp.segmentmiddleware.services.AccountService;
import com.zeniapp.segmentmiddleware.services.DietService;
import com.zeniapp.segmentmiddleware.utils.ActivityUtils;
import com.zeniapp.segmentmiddleware.utils.DietUtils;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/admin/diets")
public class AdminDietController {
    @Autowired
    private DietService dietService;
    
    @Autowired
    private AccountService accountService;
    
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/count")
    public ResponseEntity<?> count(
        @RequestParam(required = false) String title,
        @RequestParam(required = false) String goal,
        @RequestParam(required = false) String type,
        @RequestParam(required = false) String startsOnMin,
        @RequestParam(required = false) String startsOnMax,
        @RequestParam(required = false) Integer durationMin,
        @RequestParam(required = false) Integer durationMax,
        @RequestParam(required = false) String createdOnMin,
        @RequestParam(required = false) String createdOnMax,
        @RequestParam(required = false) String updatedOnMin,
        @RequestParam(required = false) String updatedOnMax,
        @RequestParam(required = false) Boolean isArchived,
        @RequestParam(required = false) List<String> tags,
        @RequestParam(required = false) String accountId
    ) {
        try {
            DietQueryParamsDto dietQueryParamsDto = new DietQueryParamsDto(
                null, null, null, null,
                title, goal, type, startsOnMin, startsOnMax,
                durationMin, durationMax, isArchived,
                createdOnMin, createdOnMax,
                updatedOnMin, updatedOnMax,
                accountId, tags
            );
            
            DietUtils.validateDietQueryParamsDto(dietQueryParamsDto);

            CountResponseDto countDto = new CountResponseDto();
            countDto.setTotal(this.dietService.count(dietQueryParamsDto));

            return new ResponseEntity<CountResponseDto>(countDto, HttpStatus.OK);
        }
        catch (WrongPayloadException wrongPayloadException) {
            return new ResponseEntity<ErrorResponseDto>(wrongPayloadException.getErrorResponseDto(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception exception) {
            AdminDietController.log.error("error occurred counting the diets");
            AdminDietController.log.error("error message is " + exception.getMessage());
            return new ResponseEntity<ErrorResponseDto>(DietUtils.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateDietDto createDietDto, BindingResult bindingResult) {
        try {
            DietUtils.validateCreateOrUpdateDietDto(bindingResult);

            this.accountService
                .findOne(createDietDto.getAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("account not found"));

            Diet dietToCreate = modelMapper.map(createDietDto, Diet.class);
            dietToCreate.setId(null);
            SimpleDateFormat datetimeFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            dietToCreate.setCreatedOn(datetimeFormatter.format(Calendar.getInstance().getTime()));
            dietToCreate.setUpdatedOn(datetimeFormatter.format(Calendar.getInstance().getTime()));

            Diet createdDiet = this.dietService.save(dietToCreate);

            return new ResponseEntity<DietDto>(this.modelMapper.map(createdDiet, DietDto.class), HttpStatus.CREATED);
        }
        catch (WrongPayloadException wrongPayloadException) {
            return new ResponseEntity<ErrorResponseDto>(wrongPayloadException.getErrorResponseDto(), HttpStatus.BAD_REQUEST);
        }
        catch (ResourceNotFoundException resourceNotFoundException) {
            return new ResponseEntity<ErrorResponseDto>(resourceNotFoundException.getErrorResponseDto(), HttpStatus.NOT_FOUND);
        }
        catch (DuplicateFieldsException duplicateFieldsException) {
            return new ResponseEntity<ErrorResponseDto>(duplicateFieldsException.getErrorResponseDto(), HttpStatus.CONFLICT);
        }
        catch (Exception exception) {
            AdminDietController.log.error("error occurred creating the diet");
            AdminDietController.log.error("error message is " + exception.getMessage());
            return new ResponseEntity<ErrorResponseDto>(DietUtils.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findOne(@PathVariable String id) {
        try {
            Diet dietFound = this.dietService.findOne(id).orElseThrow(ResourceNotFoundException::new);

            DietDto dietDto = this.modelMapper.map(dietFound, DietDto.class);

            return new ResponseEntity<DietDto>(dietDto, HttpStatus.OK);
        }
        catch (ResourceNotFoundException resourceNotFoundException) {
            return new ResponseEntity<ErrorResponseDto>(resourceNotFoundException.getErrorResponseDto(), HttpStatus.NOT_FOUND);
        }
        catch (Exception exception) {
            AdminDietController.log.error("error occurred retrieving the diet");
            AdminDietController.log.error("error message is " + exception.getMessage());
            return new ResponseEntity<ErrorResponseDto>(DietUtils.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping()
    public ResponseEntity<?> findMany(
        @RequestParam(required = false, defaultValue = "0") Integer offset,
        @RequestParam(required = false, defaultValue = "10") Integer limit,
        @RequestParam(required = false, defaultValue = "title") String sortField,
        @RequestParam(required = false, defaultValue = "asc") String sortDirection,
        @RequestParam(required = false) String title,
        @RequestParam(required = false) String goal,
        @RequestParam(required = false) String type,
        @RequestParam(required = false) String startsOnMin,
        @RequestParam(required = false) String startsOnMax,
        @RequestParam(required = false) Integer durationMin,
        @RequestParam(required = false) Integer durationMax,
        @RequestParam(required = false) String createdOnMin,
        @RequestParam(required = false) String createdOnMax,
        @RequestParam(required = false) String updatedOnMin,
        @RequestParam(required = false) String updatedOnMax,
        @RequestParam(required = false) Boolean isArchived,
        @RequestParam(required = false) List<String> tags,
        @RequestParam(required = false) String accountId
    ) {
        try {
            DietQueryParamsDto dietQueryParamsDto = new DietQueryParamsDto(
                offset, limit, sortField, sortDirection,
                title, goal, type, startsOnMin, startsOnMax,
                durationMin, durationMax, isArchived,
                createdOnMin, createdOnMax,
                updatedOnMin, updatedOnMax,
                accountId, tags
            );
            
            DietUtils.validateDietQueryParamsDto(dietQueryParamsDto);

            List<Diet> dietsFound = this.dietService.findMany(dietQueryParamsDto);
            List<DietDto> dietDtos = dietsFound.stream().map(e -> this.modelMapper.map(e, DietDto.class)).toList();
            Long total = this.dietService.count(dietQueryParamsDto);

            FindManyResponseDto<DietDto> findAllResponseDto = new FindManyResponseDto<DietDto>(total, dietDtos);

            return new ResponseEntity<FindManyResponseDto<DietDto>>(findAllResponseDto, HttpStatus.OK);
        }
        catch (WrongPayloadException wrongPayloadException) {
            return new ResponseEntity<ErrorResponseDto>(wrongPayloadException.getErrorResponseDto(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception exception) {
            AdminDietController.log.error("error occurred retrieving the diets");
            AdminDietController.log.error("error message is " + exception.getMessage());
            return new ResponseEntity<ErrorResponseDto>(DietUtils.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOne(@PathVariable String id, @Valid @RequestBody UpdateDietDto updateDietDto, BindingResult bindingResult) {
        try {
            DietUtils.validateCreateOrUpdateDietDto(bindingResult);
            
            this.accountService
                .findOne(updateDietDto.getAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("account not found"));

            Diet dietFound = this.dietService.findOne(id).orElseThrow(ResourceNotFoundException::new);

            Diet dietToUpdate = modelMapper.map(updateDietDto, Diet.class);
            dietToUpdate.setId(dietFound.getId());
            dietToUpdate.setCreatedOn(dietFound.getCreatedOn());
            dietToUpdate.setUpdatedOn(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(Calendar.getInstance().getTime()));

            Diet dietUpdated = this.dietService.save(dietToUpdate);

            return new ResponseEntity<DietDto>(this.modelMapper.map(dietUpdated, DietDto.class), HttpStatus.OK);
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
            AdminDietController.log.error("error occurred updating the diet");
            AdminDietController.log.error("error message is " + exception.getMessage());
            return new ResponseEntity<ErrorResponseDto>(ActivityUtils.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable String id) {
        try {
            Diet dietToDelete = this.dietService.findOne(id).orElseThrow(ResourceNotFoundException::new);

            this.dietService.deleteOne(dietToDelete.getId());

            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        catch (ResourceNotFoundException resourceNotFoundException) {
            return new ResponseEntity<ErrorResponseDto>(resourceNotFoundException.getErrorResponseDto(), HttpStatus.NOT_FOUND);
        }
        catch (Exception exception) {
            AdminDietController.log.error("error occurred during the diet deletion");
            AdminDietController.log.error("error message is " + exception.getMessage());
            return new ResponseEntity<ErrorResponseDto>(DietUtils.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}