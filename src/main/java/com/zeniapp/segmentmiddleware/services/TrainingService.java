package com.zeniapp.segmentmiddleware.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import com.zeniapp.segmentmiddleware.daos.TrainingDao;
import com.zeniapp.segmentmiddleware.dtos.TrainingQueryParamsDto;
import com.zeniapp.segmentmiddleware.entities.Training;
import com.zeniapp.segmentmiddleware.utils.TrainingUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TrainingService {
    @Autowired
    private TrainingDao trainingDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    public Long count(TrainingQueryParamsDto trainingQueryParamsDto) throws Exception {
        try {
            Query query = TrainingUtils.getQueryByTrainingQyeryParamsDto(trainingQueryParamsDto);

            Long total = this.mongoTemplate.count(query, Training.class);

            return total;
        }
        catch (Exception exception) {
            TrainingService.log.error("error occurred counting trainings");
            TrainingService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public Training save(@NonNull Training training) throws Exception {
        try {
            return this.trainingDao.save(training);
        }
        catch (Exception exception) {
            TrainingService.log.error("error occurred saving the training");
            TrainingService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public Optional<Training> findOne(@NonNull String id) throws Exception {
        try {
            return this.trainingDao.findById(id);
        }
        catch (Exception exception) {
            TrainingService.log.error("error occurred retrieving the training");
            TrainingService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public Optional<Training> findOneByIdAndAccountId(String id, String accountId) throws Exception {
        try {
            return this.trainingDao.findByIdAndAccountId(id, accountId);
        }
        catch (Exception exception) {
            TrainingService.log.error("error occurred retrieving the training");
            TrainingService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public List<Training> findMany(TrainingQueryParamsDto trainingQueryParamsDto) throws Exception {
        try {
            Query query = TrainingUtils.getQueryByTrainingQyeryParamsDto(trainingQueryParamsDto);

            query.skip(trainingQueryParamsDto.getOffset());
            query.limit(trainingQueryParamsDto.getLimit());

            String sortField = trainingQueryParamsDto.getSortField();
            String sortDirection = trainingQueryParamsDto.getSortDirection();

            if (sortField != null && sortField.length() > 0 && sortDirection != null && sortDirection.length() > 0) {
                query.with(
                    Sort.by(
                        trainingQueryParamsDto.getSortDirection().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC,
                        trainingQueryParamsDto.getSortField()
                    )
                );
            }

            List<Training> trainings = this.mongoTemplate.find(query, Training.class);

            return trainings;
        }
        catch (Exception exception) {
            TrainingService.log.error("error occurred retrieving the trainings");
            TrainingService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public void deleteOne(@NonNull String id) throws Exception {
        try {
            this.trainingDao.deleteById(id);
        }
        catch (Exception exception) {
            TrainingService.log.error("error occurred deleting the training");
            TrainingService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public void deleteMany(@NonNull Set<String> ids) throws Exception {
        try {
            this.trainingDao.deleteAllById(ids);
        }
        catch (Exception exception) {
            TrainingService.log.error("error occurred deleting many trainings");
            TrainingService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }
}