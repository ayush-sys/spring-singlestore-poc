package com.singlestore.singlestore_application.dao;

import com.singlestore.singlestore_application.dao.entity.UserEntity;
import com.singlestore.singlestore_application.dao.repository.UserRepository;
import com.singlestore.singlestore_application.utils.Utils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@Transactional
public class ApplicationDAO {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private Utils utils;

    public List<UserEntity> getAllUsers(){
        return userRepo.findAll();
    }

    public List<UserEntity> getUsersByState(String userState){
        log.info("Getting users by {} state at {}", userState, utils.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
        return userRepo.findByUserState(userState);
    }

    public String getAccessToken(String username) {
        log.info("Getting tokens by {} state at {}", username, utils.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
        return userRepo.getUserAccessTokens(username);
    }

    public void updateUserState(String userState, String username) {
        log.info("Updating user state to {} state for username {} at {}", userState, username, utils.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
        userRepo.updateUserState(userState, username);
    }

}