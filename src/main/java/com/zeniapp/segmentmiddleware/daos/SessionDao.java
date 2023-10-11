package com.zeniapp.segmentmiddleware.daos;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.zeniapp.segmentmiddleware.entities.Session;

public interface SessionDao extends JpaRepository<Session, String> {
    @Query(value = "SELECT * FROM sessions WHERE sessions.account_id = :accountId LIMIT 1", nativeQuery = true)
    public Optional<Session> findByAccountId(String accountId);
}
