package org.example.vidyavriti.Services;

import org.example.vidyavriti.Exception.CustomException;
import org.example.vidyavriti.Models.Users;
import org.example.vidyavriti.Repositories.UsersRepository;
import org.example.vidyavriti.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    @Autowired
    UsersRepository usersRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtService jwtService;

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";

    public void studentSignup(Users user){
        if(usersRepository.findByUserName(user.getUserName()).isPresent()){
            throw  new CustomException("Username allready exist!");
        }
        if(usersRepository.findByEmail(user.getEmail()).isPresent()){
            throw new CustomException("Email allready exist!");
        }
        if(!user.getEmail().matches(EMAIL_REGEX)){
            throw new CustomException("PLease enter email in valid format!");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.STUDENT);
        usersRepository.save(user);

    }

    public String login(Users user){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword()));
        if(authentication.isAuthenticated()){
            return jwtService.generateToken(usersRepository.findByUserName(user.getUserName()).get());
        }
        throw new CustomException("Login Failed!");
    }



}
