package com.mirea.desserter.repos;

import com.mirea.desserter.models.Type;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITypeRepo extends JpaRepository<Type, Integer> {
    Type findById(int id);
    Long deleteById(int id);
}
