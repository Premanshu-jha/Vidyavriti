package org.example.vidyavriti.Repositories;

import org.example.vidyavriti.Models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users,Long> {

   Optional<Users> findByUserName(String userName);
   Optional<Users> findByEmail(String email);
}
