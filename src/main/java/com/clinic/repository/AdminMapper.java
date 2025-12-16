package com.clinic.repository;

import com.clinic.domain.Admin;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminMapper {
    Admin findByAdminId(String adminId);
}

