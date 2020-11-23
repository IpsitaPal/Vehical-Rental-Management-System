package com.cg.ovms.service.implementation;

import com.cg.ovms.dto.User;
import com.cg.ovms.entities.UserEntity;

public class Convert {

    public UserEntity convertDtoToEntity(User userDto) {

    	UserEntity user = new UserEntity();

        user.setUserId(userDto.getUserId());
        user.setPassword(userDto.getPassword());
        user.setRole(userDto.getRole());

        return user;
    }

    public User convertEntityToDto(UserEntity user) {

    	User userDto = new User();
    	
        userDto.setUserId(user.getUserId());
        userDto.setPassword(user.getPassword());
        userDto.setRole(user.getRole());

        return userDto;
    }
}
