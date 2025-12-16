package com.clinic.service;

import com.clinic.domain.Admin;
import com.clinic.domain.User;
import com.clinic.repository.AdminMapper;
import com.clinic.repository.UserMapper;
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

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AdminMapper adminMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 管理者として検索
        Admin admin = adminMapper.findByAdminId(username);
        if (admin != null) {
            return new CustomUserDetails(
                admin.getAdminId(),
                admin.getPassword(),
                getAdminAuthorities()
            );
        }

        // 患者として検索
        User user = userMapper.findByCardNumber(username);
        if (user != null) {
            return new CustomUserDetails(
                user.getCardNumber(),
                user.getPassword(),
                getUserAuthorities()
            );
        }

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

