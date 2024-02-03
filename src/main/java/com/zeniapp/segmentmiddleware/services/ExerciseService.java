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
import com.zeniapp.segmentmiddleware.daos.ExerciseDao;
import com.zeniapp.segmentmiddleware.dtos.ExerciseQueryParamsDto;
import com.zeniapp.segmentmiddleware.entities.Exercise;
import com.zeniapp.segmentmiddleware.utils.ExerciseUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ExerciseService {
    @Autowired
    private ExerciseDao exerciseDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    public Long count(ExerciseQueryParamsDto exerciseQueryParamsDto) throws Exception {
        try {
            Query query = ExerciseUtils.getQueryByExerciseQyeryParamsDto(exerciseQueryParamsDto);

            Long total = this.mongoTemplate.count(query, Exercise.class);

            return total;
        }
        catch (Exception exception) {
            ExerciseService.log.error("error occurred counting exercises");
            ExerciseService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public Exercise save(@NonNull Exercise exercise) throws Exception {
        try {
            return this.exerciseDao.save(exercise);
        }
        catch (Exception exception) {
            ExerciseService.log.error("error occurred saving the exercise");
            ExerciseService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public Optional<Exercise> findOne(String id) throws Exception {
        try {
            return this.exerciseDao.findByIdAndIsDeletedFalse(id);
        }
        catch (Exception exception) {
            ExerciseService.log.error("error occurred retrieving the exercise");
            ExerciseService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public Optional<Exercise> findOnePublished(String id) throws Exception {
        try {
            return this.exerciseDao.findOnePublished(id);
        }
        catch (Exception exception) {
            ExerciseService.log.error("error occurred retrieving the exercise");
            ExerciseService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public List<Exercise> findMany(ExerciseQueryParamsDto exerciseQueryParamsDto) throws Exception {
        try {
            Query query = ExerciseUtils.getQueryByExerciseQyeryParamsDto(exerciseQueryParamsDto);

            query.skip(exerciseQueryParamsDto.getOffset());
            query.limit(exerciseQueryParamsDto.getLimit());

            String sortField = exerciseQueryParamsDto.getSortField();
            String sortDirection = exerciseQueryParamsDto.getSortDirection();

            if (sortField != null && sortField.length() > 0 && sortDirection != null && sortDirection.length() > 0) {
                query.with(
                    Sort.by(
                        exerciseQueryParamsDto.getSortDirection().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC,
                        exerciseQueryParamsDto.getSortField()
                    )
                );
            }

            List<Exercise> exercises = this.mongoTemplate.find(query, Exercise.class);

            return exercises;
        }
        catch (Exception exception) {
            ExerciseService.log.error("error occurred retrieving the exercises");
            ExerciseService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public Optional<Exercise> findByName(String name) throws Exception {
        try {
            return this.exerciseDao.findByName(name);
        }
        catch (Exception exception) {
            ExerciseService.log.error("error occurred retrieving the exercise by name");
            ExerciseService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public Optional<Exercise> findByIdNotAndName(String id, String name) throws Exception {
        try {
            return this.exerciseDao.findByIdNotAndName(id, name);
        }
        catch (Exception exception) {
            ExerciseService.log.error("error occurred retrieving the exercise by name");
            ExerciseService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public List<Exercise> findAllByIds(Set<String> ids) throws Exception {
        try {
            return this.exerciseDao.findAllByIdAndIsDeletedFalse(ids);
        }
        catch (Exception exception) {
            ExerciseService.log.error("error occurred retrieving the exercises by IDs");
            ExerciseService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }
}