package com.zeniapp.segmentmiddleware.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zeniapp.segmentmiddleware.daos.FoodDao;
import com.zeniapp.segmentmiddleware.dtos.FoodQueryParamsDto;
import com.zeniapp.segmentmiddleware.entities.Food;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FoodService {
    @Autowired
    private FoodDao foodDao;

    public Long count(FoodQueryParamsDto foodQueryParamsDto) throws Exception {
        try {
            // to do
            return null;
        }
        catch (Exception exception) {
            FoodService.log.error("error occurred counting foods");
            FoodService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public Food save(Food food) throws Exception {
        try {
            // to do
            return null;
        }
        catch (Exception exception) {
            FoodService.log.error("error occurred saving the food");
            FoodService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public Optional<Food> findOne(String id) throws Exception {
        try {
            // to do
            return null;
        }
        catch (Exception exception) {
            FoodService.log.error("error occurred retrieving the food");
            FoodService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public List<Food> findMany(FoodQueryParamsDto foodQueryParamsDto) throws Exception {
        try {
            // to do
            return null;
        }
        catch (Exception exception) {
            FoodService.log.error("error occurred retrieving the foods");
            FoodService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public List<Food> findByName(String name) throws Exception {
        try {
            // to do
            return null;
        }
        catch (Exception exception) {
            FoodService.log.error("error occurred retrieving the food by name");
            FoodService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public List<Food> findByIdNotAndName(String id, String name) throws Exception {
        try {
            // to do
            return null;
        }
        catch (Exception exception) {
            FoodService.log.error("error occurred retrieving the food by name");
            FoodService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }
}