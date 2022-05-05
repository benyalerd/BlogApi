package com.example.gradleinitial.repository;

import com.example.gradleinitial.model.Member;
import com.example.gradleinitial.model.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UserRepository  extends JpaRepository<Member,Long> {
    Member findByEmail(String email);

}
