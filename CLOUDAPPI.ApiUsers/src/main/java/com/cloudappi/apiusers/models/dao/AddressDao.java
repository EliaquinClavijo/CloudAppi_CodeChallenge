package com.cloudappi.apiusers.models.dao;

import com.cloudappi.apiusers.models.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressDao extends JpaRepository< Address, Long>{

}
