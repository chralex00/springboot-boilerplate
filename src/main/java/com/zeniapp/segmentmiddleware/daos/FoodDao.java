package com.zeniapp.segmentmiddleware.daos;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.zeniapp.segmentmiddleware.entities.Food;

public interface FoodDao extends MongoRepository<Food, String> {}