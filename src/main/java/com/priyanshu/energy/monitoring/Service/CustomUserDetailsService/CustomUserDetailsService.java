package com.priyanshu.energy.monitoring.Service.CustomUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.priyanshu.energy.monitoring.entity.user.UserEntity;
import com.priyanshu.energy.monitoring.entity.userDetails.CustomUserDetails;
import com.priyanshu.energy.monitoring.repository.user.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepo.findByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("No user exists with the given mail"));

        return new CustomUserDetails(user);
    }
}
