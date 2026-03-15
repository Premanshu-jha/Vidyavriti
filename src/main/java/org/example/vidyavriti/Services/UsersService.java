package org.example.vidyavriti.Services;

import org.example.vidyavriti.Models.Users;
import org.example.vidyavriti.Repositories.UsersRepository;
import org.example.vidyavriti.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    public void studentSignup(Users user){
        if(usersRepository.findByUserName(user.getUserName()).isPresent()){
            throw  new RuntimeException("Username allready exist!");
        }
        if(usersRepository.findByEmail(user.getEmail()).isPresent()){
            throw new RuntimeException("Email allready exist!");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.STUDENT);
        usersRepository.save(user);

    }

    public String login(Users user){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword()));
        if(authentication.isAuthenticated()){
            return jwtService.generateToken(user);
        }
        return "Failed!";
    }



}
