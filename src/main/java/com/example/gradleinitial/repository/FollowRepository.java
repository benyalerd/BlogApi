package com.example.gradleinitial.repository;

import com.example.gradleinitial.model.Follow;
import com.example.gradleinitial.model.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface FollowRepository extends JpaRepository<Follow,Long> {

    public Page<Follow> findByFollowerId(Long followerId, Pageable pageable);
    public Page<Follow> findByFollowingId(Long followingId, Pageable pageable);
}
