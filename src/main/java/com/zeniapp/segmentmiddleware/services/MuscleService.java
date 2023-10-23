package com.zeniapp.segmentmiddleware.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import com.zeniapp.segmentmiddleware.daos.MuscleDao;
import com.zeniapp.segmentmiddleware.dtos.MuscleQueryParamsDto;
import com.zeniapp.segmentmiddleware.entities.Muscle;
import com.zeniapp.segmentmiddleware.utils.MuscleUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MuscleService {
    @Autowired
    private MuscleDao muscleDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    public Long count(MuscleQueryParamsDto muscleQueryParamsDto) throws Exception {
        try {
            Query query = MuscleUtils.getQueryByMuscleQyeryParamsDto(muscleQueryParamsDto);

            Long total = this.mongoTemplate.count(query, Muscle.class);

            return total;
        }
        catch (Exception exception) {
            MuscleService.log.error("error occurred counting muscles");
            MuscleService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public Muscle save(Muscle muscle) throws Exception {
        try {
            return this.muscleDao.save(muscle);
        }
        catch (Exception exception) {
            MuscleService.log.error("error occurred saving the muscle");
            MuscleService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public Optional<Muscle> findOne(String id) throws Exception {
        try {
            return this.muscleDao.findByIdAndIsDeletedFalse(id);
        }
        catch (Exception exception) {
            MuscleService.log.error("error occurred retrieving the muscle");
            MuscleService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public List<Muscle> findMany(MuscleQueryParamsDto muscleQueryParamsDto) throws Exception {
        try {
            Query query = MuscleUtils.getQueryByMuscleQyeryParamsDto(muscleQueryParamsDto);

            query.skip(muscleQueryParamsDto.getOffset());
            query.limit(muscleQueryParamsDto.getLimit());

            String sortField = muscleQueryParamsDto.getSortField();
            String sortDirection = muscleQueryParamsDto.getSortDirection();

            if (sortField != null && sortField.length() > 0 && sortDirection != null && sortDirection.length() > 0) {
                query.with(
                    Sort.by(
                        muscleQueryParamsDto.getSortDirection().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC,
                        muscleQueryParamsDto.getSortField()
                    )
                );
            }

            List<Muscle> muscles = this.mongoTemplate.find(query, Muscle.class);

            return muscles;
        }
        catch (Exception exception) {
            MuscleService.log.error("error occurred retrieving the muscles");
            MuscleService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public Optional<Muscle> findByName(String name) throws Exception {
        try {
            return this.muscleDao.findByName(name);
        }
        catch (Exception exception) {
            MuscleService.log.error("error occurred retrieving the muscle by name");
            MuscleService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public Optional<Muscle> findByIdNotAndName(String id, String name) throws Exception {
        try {
            return this.muscleDao.findByIdNotAndName(id, name);
        }
        catch (Exception exception) {
            MuscleService.log.error("error occurred retrieving the muscle by name");
            MuscleService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public List<Muscle> findAllByIds(Set<String> ids) throws Exception {
        try {
            return this.muscleDao.findAllByIdAndIsDeletedFalse(ids);
        }
        catch (Exception exception) {
            MuscleService.log.error("error occurred retrieving the muscles by IDs");
            MuscleService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }
}