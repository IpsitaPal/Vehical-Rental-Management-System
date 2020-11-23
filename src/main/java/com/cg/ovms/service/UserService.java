package com.cg.ovms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cg.ovms.dto.User;
import com.cg.ovms.exception.ApplicationException;
import com.cg.ovms.exception.DuplicateRecordException;
import com.cg.ovms.exception.RecordNotFoundException;

@Service
public interface UserService {

	 public User SignIn(User udto) throws RecordNotFoundException,DuplicateRecordException;

	 public User addUser(User udto) throws RecordNotFoundException, DuplicateRecordException;

	 public User removeUser(User udto) throws RecordNotFoundException;

	 public User updatePassword(User udto) throws ApplicationException;

	 public List<User> getAllUsersInfo();

	 public User signOut(User userdto);

   
}
