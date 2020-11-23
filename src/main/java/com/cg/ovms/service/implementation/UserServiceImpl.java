package com.cg.ovms.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.ovms.MyLogger;
import com.cg.ovms.dto.User;
import com.cg.ovms.entities.UserEntity;
import com.cg.ovms.exception.ApplicationException;
import com.cg.ovms.exception.DuplicateRecordException;
import com.cg.ovms.exception.RecordNotFoundException;
import com.cg.ovms.exception.UserNotFoundException;
import com.cg.ovms.repository.UserRepository;
import com.cg.ovms.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

	@Override
	public User SignIn(User userDto) throws RecordNotFoundException, DuplicateRecordException{
		
		MyLogger.info("SignIn method called.");
		
		UserEntity userEntity = convertDtoToEntity(userDto);
		Optional<UserEntity> user1 = userRepo.findById(userEntity.getUserId());

		if(!(user1.isPresent())) {
			MyLogger.info("SignIn method teriminated unsuccessfully");
			throw new RecordNotFoundException("Invalid user");
		}
		
		if(userDto.getPassword().equals(user1.get().getPassword())) {
			MyLogger.info("SignIn method executed.");
			return convertEntityToDto((user1.get()));
		}
		else {
			MyLogger.info("SignIn method teriminated unsuccessfully");
			throw new DuplicateRecordException("Password Wrong");
		}
	}

	@Override
	public User addUser(User userDto) throws DuplicateRecordException, RecordNotFoundException {
		MyLogger.info("Adduser method called ");
		
		if(userDto==null)
			return null;
		
		UserEntity userEntity = convertDtoToEntity(userDto);
	    
		Optional<UserEntity> user1 = userRepo.findById(userEntity.getUserId());
		if(user1.isPresent()) {
			MyLogger.info(("Add user method teriminated unsuccessfully"));
			throw new DuplicateRecordException("userId already found");
		}
		
		userEntity = userRepo.save(userEntity);
		userDto = convertEntityToDto(userEntity);
		if (userDto == null) {
			throw new RecordNotFoundException("User Not Added");
		}
		
		MyLogger.info("Adduser method executed");
		return userDto;
	}

	public List<User> getAllUsersInfo(){
		MyLogger.info("GetAllUsers method called ");
		
		List<User> ul= new ArrayList<User>();
		List<UserEntity> userall= userRepo.findAll();
		User ud = null;
		
		for(UserEntity u:userall) {
			ud = convertEntityToDto(u);
			ul.add(ud);
		}
		
		MyLogger.info("GetAllUsers method executed");
		return ul;
	}

	public User updatePassword(User userDto) throws ApplicationException, UserNotFoundException{
		if(userDto==null)
			throw new UserNotFoundException("Invalid User");
		
		UserEntity userEntity = convertDtoToEntity(userDto);
		Optional<UserEntity> user1 = userRepo.findById(userEntity.getUserId());
		
		if(!user1.isPresent()) {
			MyLogger.info(("Update user method teriminated unsuccessfully"));
			throw new RecordNotFoundException("User Not Found");
		}
			
		if(!(user1.get().getPassword().equals(userDto.getPassword()))) {
			user1.get().setPassword(userDto.getPassword());
			userEntity = user1.get();
			userEntity = userRepo.save(userEntity);
		}
		
		userDto = convertEntityToDto(userEntity);

		if (userDto == null) {
			throw new ApplicationException("Password Not Updated");
		}

		return userDto;
	}

	public User removeUser(User userDTO) throws RecordNotFoundException {
		MyLogger.info("RemoveUser method called ");
		
		UserEntity userEntity = convertDtoToEntity(userDTO);
		
		Optional<UserEntity> user1 = userRepo.findById(userEntity.getUserId());
		if(!(user1.isPresent())){
			MyLogger.info("RemoveUser method teriminated unsuccessfully");
			throw new RecordNotFoundException("user is not found");
		}
		
		userRepo.delete(userEntity);
		
		MyLogger.info("RemoveUser method executed ");
		userDTO = convertEntityToDto(userEntity);
		return userDTO;
	}

	public User signOut(User userDto) {
//		User u=convertDtoToEntity((userDto));
//		Optional<User> user1=iUserRepo.findById(u.getUserId());
//		if(userDto.getPassword().equals(user1.get().getPassword()))
	//		return convertEntityToDto(user1.get());
		return null;
	}

	public UserEntity convertDtoToEntity(User userDto) {

		UserEntity user = new UserEntity();
		
    	user.setUserId(userDto.getUserId());
    	user.setPassword(userDto.getPassword());
    	user.setRole(userDto.getRole());
    	/*
    	CustomerEntity customerEntity = new CustomerEntity();
		
		customerEntity.setCustomerId(userDto.getCustomer().getCustomerId());
		customerEntity.setFirstName(userDto.getCustomer().getFirstName());
		customerEntity.setLastName(userDto.getCustomer().getLastName());
		customerEntity.setMobileNumber(userDto.getCustomer().getMobileNumber());
		customerEntity.setEmailId(userDto.getCustomer().getEmailId());
		customerEntity.setAddress(userDto.getCustomer().getAddress());
		
    	user.setCustomer(customerEntity);
*/
		return user;

	}

	public User convertEntityToDto(UserEntity user) {

		User userDto = new User();
		
		userDto.setUserId(user.getUserId());
		userDto.setPassword(user.getPassword());
		userDto.setRole(user.getRole());
		/*
		Customer customerDTO = new Customer();
		
		customerDTO.setCustomerId(user.getCustomer().getCustomerId());
		customerDTO.setFirstName(user.getCustomer().getFirstName());
		customerDTO.setLastName(user.getCustomer().getLastName());
		customerDTO.setMobileNumber(user.getCustomer().getMobileNumber());
		customerDTO.setEmailId(user.getCustomer().getEmailId());
		customerDTO.setAddress(user.getCustomer().getAddress());
		
		userDto.setCustomer(customerDTO);
		*/
		return userDto;
	}

}
