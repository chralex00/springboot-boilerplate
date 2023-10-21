package com.zeniapp.segmentmiddleware.daos;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.zeniapp.segmentmiddleware.entities.Exercise;

public interface ExerciseDao extends MongoRepository<Exercise, String> {}