package com.zeniapp.segmentmiddleware.daos;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import com.zeniapp.segmentmiddleware.entities.Exercise;

public interface ExerciseDao extends MongoRepository<Exercise, String> {
    @Query(value = "{ '_id': ?0, 'isDeleted': false }")
    public Optional<Exercise> findByIdAndIsDeletedFalse(String id);

    @Query(value = "{ 'name': ?0 }")
    public Optional<Exercise> findByName(String name);
    
    @Query(value = "{ '_id': { $ne: ?0 }, 'name': ?1 }")
    public Optional<Exercise> findByIdNotAndName(String id, String name);

    @Query(value = "{ '_id': { $in: ?0 }, 'isDeleted': false }")
    public List<Exercise> findAllByIdAndIsDeletedFalse(Set<String> ids);
    
    @Query(value = "{ '_id': ?0, 'isPublished': true }")
    public Optional<Exercise> findOnePublished(String id);
}