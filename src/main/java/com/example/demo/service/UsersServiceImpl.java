package com.example.demo.service;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo.data.UserEntity;
import com.example.demo.data.UsersRepository;
import com.example.demo.shared.UserDto;

public class UsersServiceImpl implements UsersService{
	UsersRepository usersRepository;
	Environment environment;
	BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	public UsersServiceImpl(UsersRepository usersRepository, 
			Environment environment,BCryptPasswordEncoder bCryptPasswordEncoder)
	{
		this.usersRepository = usersRepository;
		this.environment = environment;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	@Override
	public UserDto createUser(UserDto userDetails) {
		// TODO Auto-generated method stub
		userDetails.setUserId(UUID.randomUUID().toString());
		userDetails.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
		
		ModelMapper modelMapper = new ModelMapper(); 
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);

		usersRepository.save(userEntity);
		
		UserDto returnValue = modelMapper.map(userEntity, UserDto.class);
 
		return returnValue;
	}

}
