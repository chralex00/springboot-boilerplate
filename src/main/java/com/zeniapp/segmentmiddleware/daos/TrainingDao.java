package com.zeniapp.segmentmiddleware.daos;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import com.zeniapp.segmentmiddleware.entities.Training;

public interface TrainingDao extends MongoRepository<Training, String> {
    @Query(value = "{ '_id': ?0, 'accountId': ?1 }")
    public Optional<Training> findByIdAndAccountId(String id, String accountId);
}