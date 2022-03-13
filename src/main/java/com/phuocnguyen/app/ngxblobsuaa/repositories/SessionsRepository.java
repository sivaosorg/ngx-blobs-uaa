package com.phuocnguyen.app.ngxblobsuaa.repositories;

import com.phuocnguyen.app.ngxblobsuaa.entities.SessionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionsRepository extends JpaRepository<SessionsEntity, Long> {

}
