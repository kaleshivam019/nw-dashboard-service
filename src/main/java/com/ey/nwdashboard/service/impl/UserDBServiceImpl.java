package com.ey.nwdashboard.service.impl;

import com.ey.nwdashboard.entity.UserEntity;
import com.ey.nwdashboard.repository.UserRepository;
import com.ey.nwdashboard.service.UserDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDBServiceImpl implements UserDBService {

    @Autowired
    UserRepository userRepository;

    /**
     * Service to fetch all the active users
     * @return
     */
    @Override
    public List<UserEntity> getAllUsers() {
        List<UserEntity> userList = userRepository.findAll();
        if(null != userList && !userList.isEmpty()){
            //filter users with active status
            userList = userList.stream().filter(userEntity -> userEntity.isUserActive()).collect(Collectors.toList());
            return userList;
        }
        return null;
    }

    /**
     * Service to fetch an existing user based on GPN
     * @param userGPN
     * @return UserEntity
     */
    @Override
    public boolean isExistingUser(String userGPN) {
        UserEntity existingUser = userRepository.findByuserGPN(userGPN);
        if(null != existingUser){
            return Boolean.TRUE;
        }else{
            return Boolean.FALSE;
        }
    }

    @Override
    public UserEntity addNewUser(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }
}
