package com.zeniapp.segmentmiddleware.daos;

import java.util.Optional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.zeniapp.segmentmiddleware.entities.Account;

public interface AccountDao extends JpaRepository<Account, String> {
    public Long countByIsDeletedFalse();

    @Query(value = "SELECT * FROM accounts WHERE (accounts.email = :email OR accounts.username = :username) LIMIT 2", nativeQuery = true)
    public List<Account> findByEmailOrUsername(String email, String username);

    public Optional<Account> findByIdAndIsDeletedFalse(String id);

    public List<Account> findByIsDeletedFalse();

    @Query(value = "SELECT * FROM accounts WHERE accounts.id <> :id AND (accounts.email = :email OR accounts.username = :username) AND is_deleted = FALSE LIMIT 2", nativeQuery = true)
    public List<Account> findByIdNotAndEmailOrUsernameAndIsDeletedFalse(String id, String email, String username);

    @Query(value = "SELECT * FROM accounts WHERE (accounts.email = :identifier OR accounts.username = :identifier) AND is_deleted = FALSE LIMIT 1", nativeQuery = true)
    public Optional<Account> findByIdentifierAndIsDeletedFalse(String identifier);
}
