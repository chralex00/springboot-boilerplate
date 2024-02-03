package com.zeniapp.segmentmiddleware.services;

import java.util.Set;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import com.zeniapp.segmentmiddleware.daos.TdeeDao;
import com.zeniapp.segmentmiddleware.dtos.TdeeQueryParamsDto;
import com.zeniapp.segmentmiddleware.entities.Tdee;
import com.zeniapp.segmentmiddleware.utils.TdeeUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TdeeService {
    @Autowired
    private TdeeDao tdeeDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    public Long count(TdeeQueryParamsDto tdeeQueryParamsDto) throws Exception {
        try {
            Query query = TdeeUtils.getQueryByTdeeQyeryParamsDto(tdeeQueryParamsDto);

            Long total = this.mongoTemplate.count(query, Tdee.class);

            return total;
        }
        catch (Exception exception) {
            TdeeService.log.error("error occurred counting tdees");
            TdeeService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public Tdee save(@NonNull Tdee tdee) throws Exception {
        try {
            return this.tdeeDao.save(tdee);
        }
        catch (Exception exception) {
            TdeeService.log.error("error occurred saving the tdee");
            TdeeService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public Optional<Tdee> findOne(@NonNull String id) throws Exception {
        try {
            return this.tdeeDao.findById(id);
        }
        catch (Exception exception) {
            TdeeService.log.error("error occurred retrieving the tdee");
            TdeeService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public Optional<Tdee> findOneByIdAndAccountId(String id, String accountId) throws Exception {
        try {
            return this.tdeeDao.findByIdAndAccountId(id, accountId);
        }
        catch (Exception exception) {
            TdeeService.log.error("error occurred retrieving the tdee");
            TdeeService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public List<Tdee> findMany(TdeeQueryParamsDto tdeeQueryParamsDto) throws Exception {
        try {
            Query query = TdeeUtils.getQueryByTdeeQyeryParamsDto(tdeeQueryParamsDto);

            query.skip(tdeeQueryParamsDto.getOffset());
            query.limit(tdeeQueryParamsDto.getLimit());

            String sortField = tdeeQueryParamsDto.getSortField();
            String sortDirection = tdeeQueryParamsDto.getSortDirection();

            if (sortField != null && sortField.length() > 0 && sortDirection != null && sortDirection.length() > 0) {
                query.with(
                    Sort.by(
                        tdeeQueryParamsDto.getSortDirection().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC,
                        tdeeQueryParamsDto.getSortField()
                    )
                );
            }

            List<Tdee> tdees = this.mongoTemplate.find(query, Tdee.class);

            return tdees;
        }
        catch (Exception exception) {
            TdeeService.log.error("error occurred retrieving the tdees");
            TdeeService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public void deleteOne(@NonNull String id) throws Exception {
        try {
            this.tdeeDao.deleteById(id);
        }
        catch (Exception exception) {
            TdeeService.log.error("error occurred deleting the tdee");
            TdeeService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public void deleteMany(@NonNull Set<String> ids) throws Exception {
        try {
            this.tdeeDao.deleteAllById(ids);
        }
        catch (Exception exception) {
            TdeeService.log.error("error occurred deleting many tdees");
            TdeeService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }
}