package org.example.vidyavriti.Services;

import org.example.vidyavriti.Models.Users;
import org.example.vidyavriti.Repositories.UsersRepository;
import org.example.vidyavriti.Role;
import org.springframework.beans.factory.annotation.Autowired;

public class UsersService {
    @Autowired
    UsersRepository usersRepository;

    public void addUser(String userType, Users user,boolean studentSignup){
        if(usersRepository.findByUserName(user.getUserName()).isPresent()){
            throw new RuntimeException("UserName allready exist!");
        }
        if(usersRepository.findByEmail(user.getEmail()).isPresent()){
            throw new RuntimeException("Email allready exist!");
        }
        if(studentSignup) userType = "register";
        else {
            if (userType.equals("student")) user.setRole(Role.STUDENT);
            else if (userType.equals("admin")) user.setRole(Role.ADMIN);
            else if (userType.equals("manager")) user.setRole(Role.MANAGER);
            else throw new RuntimeException("Invalid Role!");
        }
        usersRepository.save(user);
    }

    public void studentSignUp(Users user){
        addUser("student",user,true);
    }

}
