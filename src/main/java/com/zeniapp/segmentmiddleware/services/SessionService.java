package com.zeniapp.segmentmiddleware.services;

import java.util.Optional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import com.zeniapp.segmentmiddleware.daos.SessionDao;
import com.zeniapp.segmentmiddleware.dtos.SessionQueryParamsDto;
import com.zeniapp.segmentmiddleware.entities.Session;
import com.zeniapp.segmentmiddleware.utils.SessionUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SessionService {
    @Autowired
    private SessionDao sessionDao;

    @Autowired
    private EntityManager entityManager;

    public Long count(SessionQueryParamsDto sessionQueryParamsDto) throws Exception {
        try {
            CriteriaBuilder countManyCriteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Long> countManyCriteriaQuery = countManyCriteriaBuilder.createQuery(Long.class);
            Root<Session> countManySessionRoot = countManyCriteriaQuery.from(Session.class);
            
            List<Predicate> predicates = SessionUtils.getPredicatesBySessionQyeryParamsDto(countManyCriteriaBuilder, countManySessionRoot, sessionQueryParamsDto);
            
            Predicate andPredicate = countManyCriteriaBuilder.and(predicates.toArray(new Predicate[0]));
            countManyCriteriaQuery.where(andPredicate).select(countManyCriteriaBuilder.count(countManySessionRoot));

            TypedQuery<Long> countManyQuery = entityManager.createQuery(countManyCriteriaQuery);
            return countManyQuery.getSingleResult();
        }
        catch (Exception exception) {
            SessionService.log.error("error occurred counting sessions");
            SessionService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public Session save(@NonNull Session session) throws Exception {
        try {
            return this.sessionDao.save(session);
        }
        catch (Exception exception) {
            SessionService.log.error("error occurred saving the session");
            SessionService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public Optional<Session> findOne(@NonNull String id) throws Exception {
        try {
            return this.sessionDao.findById(id);
        }
        catch (Exception exception) {
            SessionService.log.error("error occurred retrieving the session");
            SessionService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public List<Session> findMany(SessionQueryParamsDto sessionQueryParamsDto) throws Exception {
        try {
            CriteriaBuilder findManyCriteriaBuilder = this.entityManager.getCriteriaBuilder();
            CriteriaQuery<Session> findManyCriteriaQuery = findManyCriteriaBuilder.createQuery(Session.class);
            Root<Session> findManySessionRoot = findManyCriteriaQuery.from(Session.class);

            String sortField = sessionQueryParamsDto.getSortField();
            String sortDirection = sessionQueryParamsDto.getSortDirection();

            if (sortField != null && sortField.length() > 0 && sortDirection != null && sortDirection.length() > 0) {
                Order order = sortDirection.equalsIgnoreCase("asc")
                    ? findManyCriteriaBuilder.asc(findManySessionRoot.get(sortField))
                    : findManyCriteriaBuilder.desc(findManySessionRoot.get(sortField));
                findManyCriteriaQuery.orderBy(order);
            }

            List<Predicate> predicates = SessionUtils.getPredicatesBySessionQyeryParamsDto(findManyCriteriaBuilder, findManySessionRoot, sessionQueryParamsDto);

            Predicate andPredicate = findManyCriteriaBuilder.and(predicates.toArray(new Predicate[0]));
            findManyCriteriaQuery.where(andPredicate);

            TypedQuery<Session> findManyQuery = entityManager.createQuery(findManyCriteriaQuery);

            return findManyQuery.setFirstResult(sessionQueryParamsDto.getOffset()).setMaxResults(sessionQueryParamsDto.getLimit()).getResultList();
        }
        catch (Exception exception) {
            SessionService.log.error("error occurred retrieving the sessions");
            SessionService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public void deleteOne(@NonNull String id) throws Exception {
        try {
            this.sessionDao.deleteById(id);
        }
        catch (Exception exception) {
            SessionService.log.error("error occurred deleting the session");
            SessionService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public Optional<Session> findByAccountId(String accountId) throws Exception {
        try {
            return this.sessionDao.findByAccountId(accountId);
        }
        catch (Exception exception) {
            SessionService.log.error("error occurred retrieving the session by account id");
            SessionService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }
}
