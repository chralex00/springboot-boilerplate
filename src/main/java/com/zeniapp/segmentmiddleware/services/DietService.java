package com.zeniapp.segmentmiddleware.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import com.zeniapp.segmentmiddleware.daos.DietDao;
import com.zeniapp.segmentmiddleware.dtos.DietQueryParamsDto;
import com.zeniapp.segmentmiddleware.entities.Diet;
import com.zeniapp.segmentmiddleware.utils.DietUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DietService {
    @Autowired
    private DietDao dietDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    public Long count(DietQueryParamsDto dietQueryParamsDto) throws Exception {
        try {
            Query query = DietUtils.getQueryByDietQyeryParamsDto(dietQueryParamsDto);

            Long total = this.mongoTemplate.count(query, Diet.class);

            return total;
        }
        catch (Exception exception) {
            DietService.log.error("error occurred counting diets");
            DietService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public Diet save(Diet diet) throws Exception {
        try {
            return this.dietDao.save(diet);
        }
        catch (Exception exception) {
            DietService.log.error("error occurred saving the diet");
            DietService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public Optional<Diet> findOne(String id) throws Exception {
        try {
            return this.dietDao.findById(id);
        }
        catch (Exception exception) {
            DietService.log.error("error occurred retrieving the diet");
            DietService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public Optional<Diet> findOneByIdAndAccountId(String id, String accountId) throws Exception {
        try {
            return this.dietDao.findByIdAndAccountId(id, accountId);
        }
        catch (Exception exception) {
            DietService.log.error("error occurred retrieving the diet");
            DietService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public List<Diet> findMany(DietQueryParamsDto dietQueryParamsDto) throws Exception {
        try {
            Query query = DietUtils.getQueryByDietQyeryParamsDto(dietQueryParamsDto);

            query.skip(dietQueryParamsDto.getOffset());
            query.limit(dietQueryParamsDto.getLimit());

            String sortField = dietQueryParamsDto.getSortField();
            String sortDirection = dietQueryParamsDto.getSortDirection();

            if (sortField != null && sortField.length() > 0 && sortDirection != null && sortDirection.length() > 0) {
                query.with(
                    Sort.by(
                        dietQueryParamsDto.getSortDirection().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC,
                        dietQueryParamsDto.getSortField()
                    )
                );
            }

            List<Diet> diets = this.mongoTemplate.find(query, Diet.class);

            return diets;
        }
        catch (Exception exception) {
            DietService.log.error("error occurred retrieving the diets");
            DietService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public void deleteOne(String id) throws Exception {
        try {
            this.dietDao.deleteById(id);
        }
        catch (Exception exception) {
            DietService.log.error("error occurred deleting the diet");
            DietService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }
}