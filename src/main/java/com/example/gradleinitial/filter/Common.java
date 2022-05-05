package com.example.gradleinitial.filter;

import com.example.gradleinitial.model.Role;
import com.example.gradleinitial.model.UserToken;
import com.example.gradleinitial.repository.RoleRepository;
import com.example.gradleinitial.repository.UserTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Optional;

@Component
@Transactional
@Slf4j
public class Common {

    @Autowired
    private RoleRepository roleRepository;

    public Boolean checkAccessService(Long requestRole,String serviceName){
        var role = roleRepository.findById(requestRole);
        if(role != null) {
            String[] roleArr = role.get().getAccessService().split(",").clone();
            for (String name : roleArr) {
                if (name.equals(serviceName)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Optional<Role> checkRole(Long requestRole){
        var role = roleRepository.findById(requestRole);
        return role;
    }


}
