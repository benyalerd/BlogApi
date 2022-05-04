package com.example.gradleinitial.repository;

import javax.transaction.Transactional;
import com.example.gradleinitial.model.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface UserTokenRepository extends JpaRepository<UserToken,Long>{
    public UserToken findByToken(String token);
}
