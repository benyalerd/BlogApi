package com.example.gradleinitial.repository;

import com.example.gradleinitial.dto.response.userDetailResponse;
import com.example.gradleinitial.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import javax.transaction.Transactional;

@Repository
@Transactional
public interface UserRepository  extends JpaRepository<Member,Long> {
   
    String authorDetailQuerry = "select new com.example.gradleinitial.dto.response.userDetailResponse(m.id as userId,m.email as email,m.username as username,m.createdDate as createdDate,m.imageprofile as imageProfile,m.isActive as isActive,m.role as role,(select count(f) from Follow f where f.following.id = ?1 group by f.following.id) as followCount) from Member m where m.id = ?1";

    String readerDetailQuerry = "select new com.example.gradleinitial.dto.response.userDetailResponse(m.id as userId, m.email as email,m.username as username ,m.createdDate as createdDate,m.imageprofile as imageProfile,m.isActive as isActive,m.role as role,(select count(f) from Follow f where f.follower.id = ?1 group by f.follower.id) as followCount) from Member m where m.id = ?1";
    public Member findByEmail(String email);
    @Query(authorDetailQuerry)
    public userDetailResponse findAuthorDetailByUserId(Long id);
    @Query(readerDetailQuerry)
    public userDetailResponse findReaderDetailByUserId(Long id);
}
