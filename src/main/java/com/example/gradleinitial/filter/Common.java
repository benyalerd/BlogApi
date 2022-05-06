package com.example.gradleinitial.filter;

import com.example.gradleinitial.dto.enumm.ActivityType;
import com.example.gradleinitial.model.*;
import com.example.gradleinitial.repository.*;
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

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

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

    public Optional<Category> checkCategory(Long requestCateId){
        var category = categoryRepository.findById(requestCateId);
        return category;
    }

    public Optional<Member> checkUser(Long userId){
        var user = userRepository.findById(userId);
        return user;
    }

    public Optional<Article> checkArticle(Long articleId){
        var article = articleRepository.findById(articleId);
        return article;
    }

    public Boolean checkActivityType(Integer actType){
        if(actType != ActivityType.LIKE.getValue() && actType != ActivityType.SHARE.getValue() && actType != ActivityType.VIEW.getValue() )
        {
            return false;
        }
        return true;
    }


}
