package com.ey.nwdashboard.service.impl;

import com.ey.nwdashboard.entity.UserRegisteredEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service(value = "userService")
public class UserRegisteredServiceImpl implements UserDetailsService {

    @Autowired
    private UserRegisteredDBServiceImpl userRegisteredDBService;

    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        UserRegisteredEntity userRegistered = userRegisteredDBService.getUserRegistered(userId);
        if(null == userRegistered){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(userRegistered.getUserName(), new BCryptPasswordEncoder().encode(userRegistered.getUserPassword()), getAuthority());
    }

    private List getAuthority() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }
}
