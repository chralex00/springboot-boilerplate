package com.zeniapp.segmentmiddleware.controllers;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
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
import com.zeniapp.segmentmiddleware.dtos.FoodDto;
import com.zeniapp.segmentmiddleware.dtos.FoodQueryParamsDto;
import com.zeniapp.segmentmiddleware.dtos.CountResponseDto;
import com.zeniapp.segmentmiddleware.dtos.CreateFoodDto;
import com.zeniapp.segmentmiddleware.dtos.ErrorResponseDto;
import com.zeniapp.segmentmiddleware.dtos.FindManyResponseDto;
import com.zeniapp.segmentmiddleware.dtos.UpdateFoodDto;
import com.zeniapp.segmentmiddleware.entities.Food;
import com.zeniapp.segmentmiddleware.exceptions.DuplicateFieldsException;
import com.zeniapp.segmentmiddleware.exceptions.ResourceNotFoundException;
import com.zeniapp.segmentmiddleware.exceptions.WrongPayloadException;
import com.zeniapp.segmentmiddleware.services.FoodService;
import com.zeniapp.segmentmiddleware.utils.FoodUtils;

@Slf4j
@RestController
@RequestMapping("/api/v1/admin/foods")
public class AdminFoodController {
    @Autowired
    private FoodService foodService;
    
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/count")
    public ResponseEntity<?> count(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String category,
        @RequestParam(required = false) Integer energyMin,
        @RequestParam(required = false) Integer energyMax,
        @RequestParam(required = false) Integer referencePortionQuantityMin,
        @RequestParam(required = false) Integer referencePortionQuantityMax,
        @RequestParam(required = false) Integer proteinsMin,
        @RequestParam(required = false) Integer proteinsMax,
        @RequestParam(required = false) Integer complexCarbsMin,
        @RequestParam(required = false) Integer complexCarbsMax,
        @RequestParam(required = false) Integer simplexCarbsMin,
        @RequestParam(required = false) Integer simplexCarbsMax,
        @RequestParam(required = false) Integer saturatedFatsMin,
        @RequestParam(required = false) Integer saturatedFatsMax,
        @RequestParam(required = false) Integer unsaturatedFatsMin,
        @RequestParam(required = false) Integer unsaturatedFatsMax,
        @RequestParam(required = false) Integer fibersMin,
        @RequestParam(required = false) Integer fibersMax,
        @RequestParam(required = false) Integer cholesterolMin,
        @RequestParam(required = false) Integer cholesterolMax,
        @RequestParam(required = false) List<String> vitamins,
        @RequestParam(required = false) List<String> minerals
    ) {
        try {
            FoodQueryParamsDto foodQueryParamsDto = new FoodQueryParamsDto(
                null, null, null, null,
                name, category, energyMin, energyMax,
                referencePortionQuantityMin, referencePortionQuantityMax,
                proteinsMin, proteinsMax, simplexCarbsMin, simplexCarbsMax,
                complexCarbsMin, complexCarbsMax, saturatedFatsMin, saturatedFatsMax,
                unsaturatedFatsMin, unsaturatedFatsMax, fibersMin, fibersMax,
                cholesterolMin, cholesterolMax, vitamins, minerals
            );
            
            FoodUtils.validateFoodQueryParamsDto(foodQueryParamsDto);

            CountResponseDto countDto = new CountResponseDto();
            countDto.setTotal(this.foodService.count(foodQueryParamsDto));

            return new ResponseEntity<CountResponseDto>(countDto, HttpStatus.OK);
        }
        catch (WrongPayloadException wrongPayloadException) {
            return new ResponseEntity<ErrorResponseDto>(wrongPayloadException.getErrorResponseDto(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception exception) {
            AdminFoodController.log.error("error occurred counting the foods");
            AdminFoodController.log.error("error message is " + exception.getMessage());
            return new ResponseEntity<ErrorResponseDto>(FoodUtils.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateFoodDto createFoodDto, BindingResult bindingResult) {
        try {
            FoodUtils.validateCreateOrUpdateFoodDto(bindingResult);

            Optional<Food> foodByName = this.foodService.findByName(createFoodDto.getName());

            FoodUtils.manageDuplicateFields(foodByName);

            Food foodToCreate = this.modelMapper.map(createFoodDto, Food.class);
            foodToCreate.setIsDeleted(false);

            Food createdFood = this.foodService.save(foodToCreate);

            return new ResponseEntity<FoodDto>(this.modelMapper.map(createdFood, FoodDto.class), HttpStatus.CREATED);
        }
        catch (WrongPayloadException wrongPayloadException) {
            return new ResponseEntity<ErrorResponseDto>(wrongPayloadException.getErrorResponseDto(), HttpStatus.BAD_REQUEST);
        }
        catch (DuplicateFieldsException duplicateFieldsException) {
            return new ResponseEntity<ErrorResponseDto>(duplicateFieldsException.getErrorResponseDto(), HttpStatus.CONFLICT);
        }
        catch (Exception exception) {
            AdminFoodController.log.error("error occurred creating the food");
            AdminFoodController.log.error("error message is " + exception.getMessage());
            return new ResponseEntity<ErrorResponseDto>(FoodUtils.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findOne(@PathVariable String id) {
        try {
            Food foodFound = this.foodService.findOne(id).orElseThrow(ResourceNotFoundException::new);

            FoodDto foodDto = this.modelMapper.map(foodFound, FoodDto.class);

            return new ResponseEntity<FoodDto>(foodDto, HttpStatus.OK);
        }
        catch (ResourceNotFoundException resourceNotFoundException) {
            return new ResponseEntity<ErrorResponseDto>(resourceNotFoundException.getErrorResponseDto(), HttpStatus.NOT_FOUND);
        }
        catch (Exception exception) {
            AdminFoodController.log.error("error occurred retrieving the food");
            AdminFoodController.log.error("error message is " + exception.getMessage());
            return new ResponseEntity<ErrorResponseDto>(FoodUtils.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping()
    public ResponseEntity<?> findMany(
        @RequestParam(required = false, defaultValue = "0") Integer offset,
        @RequestParam(required = false, defaultValue = "10") Integer limit,
        @RequestParam(required = false, defaultValue = "name") String sortField,
        @RequestParam(required = false, defaultValue = "asc") String sortDirection,
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String category,
        @RequestParam(required = false) Integer energyMin,
        @RequestParam(required = false) Integer energyMax,
        @RequestParam(required = false) Integer referencePortionQuantityMin,
        @RequestParam(required = false) Integer referencePortionQuantityMax,
        @RequestParam(required = false) Integer proteinsMin,
        @RequestParam(required = false) Integer proteinsMax,
        @RequestParam(required = false) Integer complexCarbsMin,
        @RequestParam(required = false) Integer complexCarbsMax,
        @RequestParam(required = false) Integer simplexCarbsMin,
        @RequestParam(required = false) Integer simplexCarbsMax,
        @RequestParam(required = false) Integer saturatedFatsMin,
        @RequestParam(required = false) Integer saturatedFatsMax,
        @RequestParam(required = false) Integer unsaturatedFatsMin,
        @RequestParam(required = false) Integer unsaturatedFatsMax,
        @RequestParam(required = false) Integer fibersMin,
        @RequestParam(required = false) Integer fibersMax,
        @RequestParam(required = false) Integer cholesterolMin,
        @RequestParam(required = false) Integer cholesterolMax,
        @RequestParam(required = false) List<String> vitamins,
        @RequestParam(required = false) List<String> minerals
    ) {
        try {
            FoodQueryParamsDto foodQueryParamsDto = new FoodQueryParamsDto(
                offset, limit, sortField, sortDirection,
                name, category, energyMin, energyMax,
                referencePortionQuantityMin, referencePortionQuantityMax,
                proteinsMin, proteinsMax, simplexCarbsMin, simplexCarbsMax,
                complexCarbsMin, complexCarbsMax, saturatedFatsMin, saturatedFatsMax,
                unsaturatedFatsMin, unsaturatedFatsMax, fibersMin, fibersMax,
                cholesterolMin, cholesterolMax, vitamins, minerals
            );
            
            FoodUtils.validateFoodQueryParamsDto(foodQueryParamsDto);

            List<Food> foodsFound = this.foodService.findMany(foodQueryParamsDto);
            List<FoodDto> foodDtos = foodsFound.stream().map(e -> this.modelMapper.map(e, FoodDto.class)).toList();
            Long total = this.foodService.count(foodQueryParamsDto);

            FindManyResponseDto<FoodDto> findAllResponseDto = new FindManyResponseDto<FoodDto>(total, foodDtos);

            return new ResponseEntity<FindManyResponseDto<FoodDto>>(findAllResponseDto, HttpStatus.OK);
        }
        catch (WrongPayloadException wrongPayloadException) {
            return new ResponseEntity<ErrorResponseDto>(wrongPayloadException.getErrorResponseDto(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception exception) {
            AdminFoodController.log.error("error occurred retrieving the foods");
            AdminFoodController.log.error("error message is " + exception.getMessage());
            return new ResponseEntity<ErrorResponseDto>(FoodUtils.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOne(@PathVariable String id, @Valid @RequestBody UpdateFoodDto updateFoodDto, BindingResult bindingResult) {
        try {
            FoodUtils.validateCreateOrUpdateFoodDto(bindingResult);

            Food foodFound = this.foodService.findOne(id).orElseThrow(ResourceNotFoundException::new);

            Optional<Food> foodByName = this.foodService.findByIdNotAndName(id, updateFoodDto.getName());
            
            FoodUtils.manageDuplicateFields(foodByName);

            foodFound.setName(updateFoodDto.getName());
            foodFound.setCategory(updateFoodDto.getCategory());
            foodFound.setEnergy(updateFoodDto.getEnergy());
            foodFound.setReferencePortion(updateFoodDto.getReferencePortion());
            foodFound.setProteins(updateFoodDto.getProteins());
            foodFound.setComplexCarbs(updateFoodDto.getComplexCarbs());
            foodFound.setSimplexCarbs(updateFoodDto.getSimplexCarbs());
            foodFound.setSaturatedFats(updateFoodDto.getSaturatedFats());
            foodFound.setUnsaturatedFats(updateFoodDto.getUnsaturatedFats());
            foodFound.setFibers(updateFoodDto.getFibers());
            foodFound.setCholesterol(updateFoodDto.getCholesterol());
            foodFound.setVitamins(updateFoodDto.getVitamins());
            foodFound.setMinerals(updateFoodDto.getMinerals());
            foodFound.setIsPublished(updateFoodDto.getIsPublished());

            Food foodUpdated = this.foodService.save(foodFound);

            return new ResponseEntity<FoodDto>(this.modelMapper.map(foodUpdated, FoodDto.class), HttpStatus.OK);
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
            AdminFoodController.log.error("error occurred updating the food");
            AdminFoodController.log.error("error message is " + exception.getMessage());
            return new ResponseEntity<ErrorResponseDto>(FoodUtils.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable String id) {
        try {
            Food foodToDelete = this.foodService.findOne(id).orElseThrow(ResourceNotFoundException::new);

            foodToDelete.setIsDeleted(true);

            this.foodService.save(foodToDelete);

            FoodDto foodDto = this.modelMapper.map(foodToDelete, FoodDto.class);

            return new ResponseEntity<FoodDto>(foodDto, HttpStatus.OK);
        }
        catch (ResourceNotFoundException resourceNotFoundException) {
            return new ResponseEntity<ErrorResponseDto>(resourceNotFoundException.getErrorResponseDto(), HttpStatus.NOT_FOUND);
        }
        catch (Exception exception) {
            AdminFoodController.log.error("error occurred during the food deletion");
            AdminFoodController.log.error("error message is " + exception.getMessage());
            return new ResponseEntity<ErrorResponseDto>(FoodUtils.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
