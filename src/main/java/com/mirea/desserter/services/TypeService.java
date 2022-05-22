package com.mirea.desserter.services;

import com.mirea.desserter.models.Type;
import com.mirea.desserter.repos.ITypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeService {

    private ITypeRepo typeRepo;

    @Autowired
    public TypeService( ITypeRepo typeRepo){
        this.typeRepo = typeRepo;
    }

    public Type getTypeById(int id){
        return typeRepo.findById(id);
    }

    public List<Type> getAllTypes() {
        return typeRepo.findAll();
    }

    public void saveType(Type type) {
        typeRepo.save(type);
    }

    public void deleteTypeById(int id) {
        typeRepo.deleteById(id);
    }
}
