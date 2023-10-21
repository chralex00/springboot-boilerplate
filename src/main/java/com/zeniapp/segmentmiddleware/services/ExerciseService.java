package com.zeniapp.segmentmiddleware.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zeniapp.segmentmiddleware.daos.ExerciseDao;
import com.zeniapp.segmentmiddleware.dtos.ExerciseQueryParamsDto;
import com.zeniapp.segmentmiddleware.entities.Exercise;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ExerciseService {
    @Autowired
    private ExerciseDao exerciseDao;

    public Long count(ExerciseQueryParamsDto exerciseQueryParamsDto) throws Exception {
        try {
            // to do
            return null;
        }
        catch (Exception exception) {
            ExerciseService.log.error("error occurred counting exercises");
            ExerciseService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public Exercise save(Exercise exercise) throws Exception {
        try {
            // to do
            return null;
        }
        catch (Exception exception) {
            ExerciseService.log.error("error occurred saving the exercise");
            ExerciseService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public Optional<Exercise> findOne(String id) throws Exception {
        try {
            // to do
            return null;
        }
        catch (Exception exception) {
            ExerciseService.log.error("error occurred retrieving the exercise");
            ExerciseService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public List<Exercise> findMany(ExerciseQueryParamsDto exerciseQueryParamsDto) throws Exception {
        try {
            // to do
            return null;
        }
        catch (Exception exception) {
            ExerciseService.log.error("error occurred retrieving the exercises");
            ExerciseService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public List<Exercise> findByName(String name) throws Exception {
        try {
            // to do
            return null;
        }
        catch (Exception exception) {
            ExerciseService.log.error("error occurred retrieving the exercise by name");
            ExerciseService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public List<Exercise> findByIdNotAndName(String id, String name) throws Exception {
        try {
            // to do
            return null;
        }
        catch (Exception exception) {
            ExerciseService.log.error("error occurred retrieving the exercise by name");
            ExerciseService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }
}