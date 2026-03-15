package org.example.vidyavriti.Services;

import org.example.vidyavriti.Models.UserPrincipal;
import org.example.vidyavriti.Models.Users;
import org.example.vidyavriti.Repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    UsersRepository usersRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> user = usersRepository.findByUserName(username);
        if(user.isPresent()){
            return new UserPrincipal(user.get());
        }
        throw new UsernameNotFoundException("User not found!");
    }
}
