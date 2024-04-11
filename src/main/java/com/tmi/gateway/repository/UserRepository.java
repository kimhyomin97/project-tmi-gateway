package com.tmi.gateway.repository;

import com.tmi.gateway.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserSeq(Long userSeq);
}
