package com.cg.ovms.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.ovms.dto.User;
import com.cg.ovms.exception.ApplicationException;
import com.cg.ovms.exception.DuplicateRecordException;
import com.cg.ovms.exception.RecordNotFoundException;
import com.cg.ovms.service.UserService;

@RestController
@RequestMapping("/ovms")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(value = {"/login"})
    public ResponseEntity<String> signIn(@Valid @RequestBody User userDto) throws DuplicateRecordException, RecordNotFoundException {

        userService.SignIn(userDto);
        return new ResponseEntity<String>("Signed in successfully !", HttpStatus.ACCEPTED);
    }

    @PostMapping(value = {"/adduser"})
    public ResponseEntity<String> addUser(@Valid @RequestBody User userDto) throws RecordNotFoundException, DuplicateRecordException {

        userDto = userService.addUser(userDto);
        return new ResponseEntity<String>("User added successfully",HttpStatus.ACCEPTED);
    }

    @DeleteMapping(value = {"/deleteuser/{userId}"})
    public ResponseEntity<String> removeUser(@Valid @PathVariable("userId") String userId) throws RecordNotFoundException {

    	User userDTO = new User();
    	userDTO.setUserId(userId);
        userService.removeUser(userDTO);
        return new ResponseEntity<String>("User removed successfully !", HttpStatus.ACCEPTED);
    }

    @GetMapping(value = {"/allusers"})
    public ResponseEntity<List<User>> getAllUsersInfo() {

        List<User> allUsers = userService.getAllUsersInfo();
        return new ResponseEntity<List<User>>(allUsers, HttpStatus.ACCEPTED);
    }

    @PostMapping(value = {"/signout"})
    public ResponseEntity<String> signOut(@Valid @RequestBody User userdto) {

        userService.signOut(userdto);
        return new ResponseEntity<String>("Successfully Signed Out !", HttpStatus.ACCEPTED);
    }

    @PostMapping(value={"/updatePassword"})
    public ResponseEntity<User> updatePassword(@RequestBody User userDto) throws ApplicationException {
      
       //userDto= userService.updatePassword(userDto);
       return  ResponseEntity.ok(userService.updatePassword(userDto));
    }


}
