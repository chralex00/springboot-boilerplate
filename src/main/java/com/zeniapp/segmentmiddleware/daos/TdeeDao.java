package com.zeniapp.segmentmiddleware.daos;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import com.zeniapp.segmentmiddleware.entities.Tdee;

public interface TdeeDao extends MongoRepository<Tdee, String> {
    @Query(value = "{ '_id': ?0, 'accountId': ?1 }")
    public Optional<Tdee> findByIdAndAccountId(String id, String accountId);
}