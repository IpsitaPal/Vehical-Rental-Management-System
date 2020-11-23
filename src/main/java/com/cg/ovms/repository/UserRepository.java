package com.cg.ovms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.ovms.entities.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,String>  {

}
