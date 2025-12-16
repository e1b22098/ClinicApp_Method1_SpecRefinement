package com.clinic.service;

import com.clinic.domain.Admin;
import com.clinic.domain.User;
import com.clinic.repository.AdminMapper;
import com.clinic.repository.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AdminMapper adminMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("=== loadUserByUsername called ===");
        logger.info("Username: {}", username);
        
        // 管理者として検索
        Admin admin = adminMapper.findByAdminId(username);
        if (admin != null) {
            logger.info("Admin found: {}", admin.getAdminId());
            logger.debug("Admin password hash: {}", admin.getPassword() != null ? admin.getPassword().substring(0, Math.min(30, admin.getPassword().length())) : "null");
            return new CustomUserDetails(
                admin.getAdminId(),
                admin.getPassword(),
                getAdminAuthorities()
            );
        }

        // 患者として検索
        User user = userMapper.findByCardNumber(username);
        if (user != null) {
            logger.info("User found: cardNumber={}, userId={}, name={}", user.getCardNumber(), user.getUserId(), user.getName());
            logger.debug("User password hash: {}", user.getPassword() != null ? user.getPassword().substring(0, Math.min(30, user.getPassword().length())) : "null");
            return new CustomUserDetails(
                user.getCardNumber(),
                user.getPassword(),
                getUserAuthorities()
            );
        }

        logger.warn("User not found: {}", username);
        throw new UsernameNotFoundException("ユーザーが見つかりません: " + username);
    }

    private Collection<? extends GrantedAuthority> getAdminAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return authorities;
    }

    private Collection<? extends GrantedAuthority> getUserAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return authorities;
    }
}

