package com.mirea.desserter.services;

import com.mirea.desserter.models.User;
import com.mirea.desserter.repos.IUserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private IUserRepo userRepo;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserService(IUserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepo.findByUsername(s);
    }

    public int getUserId(Authentication authentication) {
        if (authentication == null)
            return 0;
        else
            return ((User)loadUserByUsername(authentication.getName())).getId();
    }

    public String getUserRole(Authentication authentication) {
        if (authentication == null)
            return "GUEST";
        else
            return ((User)loadUserByUsername(authentication.getName())).getRole();
    }

    public void create(String username, String password) {
        User u = new User();
        u.setUsername(username);
        u.setPassword(bCryptPasswordEncoder.encode(password));
        u.setRole("USER");
        userRepo.save(u);
    }

    public List<User> getAll(){
        return userRepo.findAll();
    }

    public  void saveUser(User user){
        userRepo.save(user);
    }

    public void deleteUser(int id){
        userRepo.deleteById(id);
    }
}