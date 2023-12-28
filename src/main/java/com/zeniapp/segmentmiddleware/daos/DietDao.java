package com.zeniapp.segmentmiddleware.daos;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import com.zeniapp.segmentmiddleware.entities.Diet;

public interface DietDao extends MongoRepository<Diet, String> {
    @Query(value = "{ '_id': ?0, 'accountId': ?1 }")
    public Optional<Diet> findByIdAndAccountId(String id, String accountId);
}