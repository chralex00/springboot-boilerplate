package com.zeniapp.segmentmiddleware.daos;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import com.zeniapp.segmentmiddleware.entities.Food;

public interface FoodDao extends MongoRepository<Food, String> {
    @Query(value = "{ '_id': ?0, 'isDeleted': false }")
    public Optional<Food> findByIdAndIsDeletedFalse(String id);

    @Query(value = "{ 'name': ?0 }")
    public Optional<Food> findByName(String name);
    
    @Query(value = "{ '_id': { $ne: ?0 }, 'name': ?1 }")
    public Optional<Food> findByIdNotAndName(String id, String name);

    @Query(value = "{ '_id': { $in: ?0 }, 'isDeleted': false }")
    public List<Food> findAllByIdAndIsDeletedFalse(Set<String> ids);
    
    @Query(value = "{ '_id': ?0, 'isPublished': true }")
    public Optional<Food> findOnePublished(String id);
}