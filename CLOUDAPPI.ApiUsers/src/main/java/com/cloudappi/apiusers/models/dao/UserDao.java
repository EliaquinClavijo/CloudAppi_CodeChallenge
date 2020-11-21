package com.cloudappi.apiusers.models.dao;

import com.cloudappi.apiusers.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository< User, Long>{

}
