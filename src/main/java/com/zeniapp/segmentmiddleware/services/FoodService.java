package com.zeniapp.segmentmiddleware.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import com.zeniapp.segmentmiddleware.daos.FoodDao;
import com.zeniapp.segmentmiddleware.dtos.FoodQueryParamsDto;
import com.zeniapp.segmentmiddleware.entities.Food;
import com.zeniapp.segmentmiddleware.utils.FoodUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FoodService {
    @Autowired
    private FoodDao foodDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    public Long count(FoodQueryParamsDto foodQueryParamsDto) throws Exception {
        try {
            Query query = FoodUtils.getQueryByFoodQyeryParamsDto(foodQueryParamsDto);

            Long total = this.mongoTemplate.count(query, Food.class);

            return total;
        }
        catch (Exception exception) {
            FoodService.log.error("error occurred counting foods");
            FoodService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public Food save(Food food) throws Exception {
        try {
            return this.foodDao.save(food);
        }
        catch (Exception exception) {
            FoodService.log.error("error occurred saving the food");
            FoodService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public Optional<Food> findOne(String id) throws Exception {
        try {
            return this.foodDao.findByIdAndIsDeletedFalse(id);
        }
        catch (Exception exception) {
            FoodService.log.error("error occurred retrieving the food");
            FoodService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public List<Food> findMany(FoodQueryParamsDto foodQueryParamsDto) throws Exception {
        try {
            Query query = FoodUtils.getQueryByFoodQyeryParamsDto(foodQueryParamsDto);

            query.skip(foodQueryParamsDto.getOffset());
            query.limit(foodQueryParamsDto.getLimit());

            String sortField = foodQueryParamsDto.getSortField();
            String sortDirection = foodQueryParamsDto.getSortDirection();

            if (sortField != null && sortField.length() > 0 && sortDirection != null && sortDirection.length() > 0) {
                query.with(
                    Sort.by(
                        foodQueryParamsDto.getSortDirection().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC,
                        foodQueryParamsDto.getSortField()
                    )
                );
            }

            List<Food> foods = this.mongoTemplate.find(query, Food.class);

            return foods;
        }
        catch (Exception exception) {
            FoodService.log.error("error occurred retrieving the foods");
            FoodService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public Optional<Food> findByName(String name) throws Exception {
        try {
            return this.foodDao.findByName(name);
        }
        catch (Exception exception) {
            FoodService.log.error("error occurred retrieving the food by name");
            FoodService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public Optional<Food> findByIdNotAndName(String id, String name) throws Exception {
        try {
            return this.foodDao.findByIdNotAndName(id, name);
        }
        catch (Exception exception) {
            FoodService.log.error("error occurred retrieving the food by name");
            FoodService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public List<Food> findAllByIds(Set<String> ids) throws Exception {
        try {
            return this.foodDao.findAllByIdAndIsDeletedFalse(ids);
        }
        catch (Exception exception) {
            FoodService.log.error("error occurred retrieving the foods by IDs");
            FoodService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }
}