package com.zeniapp.segmentmiddleware.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zeniapp.segmentmiddleware.daos.MuscleDao;
import com.zeniapp.segmentmiddleware.dtos.MuscleQueryParamsDto;
import com.zeniapp.segmentmiddleware.entities.Muscle;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MuscleService {
    @Autowired
    private MuscleDao muscleDao;

    public Long count(MuscleQueryParamsDto muscleQueryParamsDto) throws Exception {
        try {
            // to do
            return null;
        }
        catch (Exception exception) {
            MuscleService.log.error("error occurred counting muscles");
            MuscleService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public Muscle save(Muscle muscle) throws Exception {
        try {
            // to do
            return null;
        }
        catch (Exception exception) {
            MuscleService.log.error("error occurred saving the muscle");
            MuscleService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public Optional<Muscle> findOne(String id) throws Exception {
        try {
            // to do
            return null;
        }
        catch (Exception exception) {
            MuscleService.log.error("error occurred retrieving the muscle");
            MuscleService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public List<Muscle> findMany(MuscleQueryParamsDto muscleQueryParamsDto) throws Exception {
        try {
            // to do
            return null;
        }
        catch (Exception exception) {
            MuscleService.log.error("error occurred retrieving the muscles");
            MuscleService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public List<Muscle> findByName(String name) throws Exception {
        try {
            // to do
            return null;
        }
        catch (Exception exception) {
            MuscleService.log.error("error occurred retrieving the muscle by name");
            MuscleService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public List<Muscle> findByIdNotAndName(String id, String name) throws Exception {
        try {
            // to do
            return null;
        }
        catch (Exception exception) {
            MuscleService.log.error("error occurred retrieving the muscle by name");
            MuscleService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }
}