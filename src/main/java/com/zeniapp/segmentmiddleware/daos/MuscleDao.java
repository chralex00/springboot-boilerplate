package com.zeniapp.segmentmiddleware.daos;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import com.zeniapp.segmentmiddleware.entities.Muscle;

public interface MuscleDao extends MongoRepository<Muscle, String> {
    @Query(value = "{ '_id': ?0, 'isDeleted': false }")
    public Optional<Muscle> findByIdAndIsDeletedFalse(String id);

    @Query(value = "{ 'name': ?0 }")
    public Optional<Muscle> findByName(String name);
    
    @Query(value = "{ '_id': { $ne: ?0 }, 'name': ?1 }")
    public Optional<Muscle> findByIdNotAndName(String id, String name);

    @Query(value = "{ '_id': { $in: ?0 }, 'isDeleted': false }")
    public List<Muscle> findAllByIdAndIsDeletedFalse(Set<String> ids);
}