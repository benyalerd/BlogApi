package com.example.gradleinitial.repository;

import com.example.gradleinitial.model.Client;
import com.example.gradleinitial.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ClientRepository  extends JpaRepository<Client,Long> {

    Client findByClientName (String clientName);
}
