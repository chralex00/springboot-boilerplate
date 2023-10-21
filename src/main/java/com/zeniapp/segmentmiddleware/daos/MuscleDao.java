package com.zeniapp.segmentmiddleware.daos;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.zeniapp.segmentmiddleware.entities.Muscle;

public interface MuscleDao extends MongoRepository<Muscle, String> {}