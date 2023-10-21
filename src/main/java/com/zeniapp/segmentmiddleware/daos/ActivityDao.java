package com.zeniapp.segmentmiddleware.daos;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import com.zeniapp.segmentmiddleware.entities.Activity;

public interface ActivityDao extends MongoRepository<Activity, String> {
    @Query(value = "{ 'name': ?0 }")
    Optional<Activity> findByName(String name);
    
    @Query(value = "{ '_id': { $ne: ?0 }, 'name': ?1 }")
    Optional<Activity> findByIdNotAndName(String id, String name);
}