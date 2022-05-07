package com.mirea.desserter.repos;

import com.mirea.desserter.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepo extends JpaRepository<User, Integer> {

    User findByUsername(String username);

    Long deleteById(int id);
}
