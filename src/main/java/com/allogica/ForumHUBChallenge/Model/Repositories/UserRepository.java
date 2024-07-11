package com.allogica.ForumHUBChallenge.Model.Repositories;

import com.allogica.ForumHUBChallenge.Model.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
    User findByUserName(String userName);
}
