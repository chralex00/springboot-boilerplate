package com.zeniapp.segmentmiddleware.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import com.zeniapp.segmentmiddleware.daos.ActivityDao;
import com.zeniapp.segmentmiddleware.dtos.ActivityQueryParamsDto;
import com.zeniapp.segmentmiddleware.entities.Activity;
import com.zeniapp.segmentmiddleware.utils.ActivityUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ActivityService {
    @Autowired
    private ActivityDao activityDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    public Long count(ActivityQueryParamsDto activityQueryParamsDto) throws Exception {
        try {
            Query query = ActivityUtils.getQueryByActivityQyeryParamsDto(activityQueryParamsDto);

            Long total = this.mongoTemplate.count(query, Activity.class);

            return total;
        }
        catch (Exception exception) {
            ActivityService.log.error("error occurred counting activitys");
            ActivityService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public Activity save(Activity activity) throws Exception {
        try {
            return this.activityDao.save(activity);
        }
        catch (Exception exception) {
            ActivityService.log.error("error occurred saving the activity");
            ActivityService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public Optional<Activity> findOne(String id) throws Exception {
        try {
            return this.activityDao.findByIdAndIsDeletedFalse(id);
        }
        catch (Exception exception) {
            ActivityService.log.error("error occurred retrieving the activity");
            ActivityService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public Optional<Activity> findOnePublished(String id) throws Exception {
        try {
            return this.activityDao.findOnePublished(id);
        }
        catch (Exception exception) {
            ActivityService.log.error("error occurred retrieving the activity");
            ActivityService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public List<Activity> findMany(ActivityQueryParamsDto activityQueryParamsDto) throws Exception {
        try {
            Query query = ActivityUtils.getQueryByActivityQyeryParamsDto(activityQueryParamsDto);

            query.skip(activityQueryParamsDto.getOffset());
            query.limit(activityQueryParamsDto.getLimit());

            String sortField = activityQueryParamsDto.getSortField();
            String sortDirection = activityQueryParamsDto.getSortDirection();

            if (sortField != null && sortField.length() > 0 && sortDirection != null && sortDirection.length() > 0) {
                query.with(
                    Sort.by(
                        activityQueryParamsDto.getSortDirection().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC,
                        activityQueryParamsDto.getSortField()
                    )
                );
            }

            List<Activity> activities = this.mongoTemplate.find(query, Activity.class);

            return activities;
        }
        catch (Exception exception) {
            ActivityService.log.error("error occurred retrieving the activitys");
            ActivityService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public Optional<Activity> findByName(String name) throws Exception {
        try {
            return this.activityDao.findByName(name);
        }
        catch (Exception exception) {
            ActivityService.log.error("error occurred retrieving the activity by name");
            ActivityService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public Optional<Activity> findByIdNotAndName(String id, String name) throws Exception {
        try {
            return this.activityDao.findByIdNotAndName(id, name);
        }
        catch (Exception exception) {
            ActivityService.log.error("error occurred retrieving the activity by name");
            ActivityService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }
}