package com.zeniapp.segmentmiddleware.services;

import java.util.Optional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zeniapp.segmentmiddleware.daos.AccountDao;
import com.zeniapp.segmentmiddleware.dtos.AccountQueryParamsDto;
import com.zeniapp.segmentmiddleware.entities.Account;
import com.zeniapp.segmentmiddleware.utils.AccountUtils;

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
public class AccountService {
    @Autowired
    private AccountDao accountDao;

    @Autowired
    private EntityManager entityManager;

    public Long count(AccountQueryParamsDto accountQueryParamsDto) throws Exception {
        try {
            CriteriaBuilder countManyCriteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Long> countManyCriteriaQuery = countManyCriteriaBuilder.createQuery(Long.class);
            Root<Account> countManyAccountRoot = countManyCriteriaQuery.from(Account.class);
            
            List<Predicate> predicates = AccountUtils.getPredicatesByAccountQyeryParamsDto(countManyCriteriaBuilder, countManyAccountRoot, accountQueryParamsDto);
            
            Predicate andPredicate = countManyCriteriaBuilder.and(predicates.toArray(new Predicate[0]));
            countManyCriteriaQuery.where(andPredicate).select(countManyCriteriaBuilder.count(countManyAccountRoot));

            TypedQuery<Long> countManyQuery = entityManager.createQuery(countManyCriteriaQuery);
            return countManyQuery.getSingleResult();
        }
        catch (Exception exception) {
            AccountService.log.error("error occurred counting accounts");
            AccountService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public Account save(Account account) throws Exception {
        try {
            return this.accountDao.save(account);
        }
        catch (Exception exception) {
            AccountService.log.error("error occurred saving the account");
            AccountService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public Optional<Account> findOne(String id) throws Exception {
        try {
            return this.accountDao.findByIdAndIsDeletedFalse(id);
        }
        catch (Exception exception) {
            AccountService.log.error("error occurred retrieving the account");
            AccountService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public List<Account> findMany(AccountQueryParamsDto accountQueryParamsDto) throws Exception {
        try {
            CriteriaBuilder findManyCriteriaBuilder = this.entityManager.getCriteriaBuilder();
            CriteriaQuery<Account> findManyCriteriaQuery = findManyCriteriaBuilder.createQuery(Account.class);
            Root<Account> findManyAccountRoot = findManyCriteriaQuery.from(Account.class);

            String sortField = accountQueryParamsDto.getSortField();
            String sortDirection = accountQueryParamsDto.getSortDirection();

            if (sortField != null && sortField.length() > 0 && sortDirection != null && sortDirection.length() > 0) {
                Order order = sortDirection.equalsIgnoreCase("asc")
                    ? findManyCriteriaBuilder.asc(findManyAccountRoot.get(sortField))
                    : findManyCriteriaBuilder.desc(findManyAccountRoot.get(sortField));
                findManyCriteriaQuery.orderBy(order);
            }

            List<Predicate> predicates = AccountUtils.getPredicatesByAccountQyeryParamsDto(findManyCriteriaBuilder, findManyAccountRoot, accountQueryParamsDto);

            Predicate andPredicate = findManyCriteriaBuilder.and(predicates.toArray(new Predicate[0]));
            findManyCriteriaQuery.where(andPredicate);

            TypedQuery<Account> findManyQuery = entityManager.createQuery(findManyCriteriaQuery);

            return findManyQuery.setFirstResult(accountQueryParamsDto.getOffset()).setMaxResults(accountQueryParamsDto.getLimit()).getResultList();
        }
        catch (Exception exception) {
            AccountService.log.error("error occurred retrieving the accounts");
            AccountService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public List<Account> findByEmailOrUsername(String email, String username) throws Exception {
        try {
            return this.accountDao.findByEmailOrUsername(email, username);
        }
        catch (Exception exception) {
            AccountService.log.error("error occurred retrieving the account by email and username");
            AccountService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public List<Account> findByIdNotAndEmailOrUsername(String id, String email, String username) throws Exception {
        try {
            return this.accountDao.findByIdNotAndEmailOrUsernameAndIsDeletedFalse(id, email, username);
        }
        catch (Exception exception) {
            AccountService.log.error("error occurred retrieving the account by email and username");
            AccountService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }

    public Optional<Account> findByIdentifier(String identifier) throws Exception {
        try {
            return this.accountDao.findByIdentifierAndIsDeletedFalse(identifier);
        }
        catch (Exception exception) {
            AccountService.log.error("error occurred retrieving the account by identifier (username, or email)");
            AccountService.log.error("error message is " + exception.getMessage());
            throw exception;
        }
    }
}
