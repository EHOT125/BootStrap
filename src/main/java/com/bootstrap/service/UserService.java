package com.bootstrap.service;

import com.bootstrap.model.User;
import com.bootstrap.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository repository;

    public UserService(UserRepository userRepository) {
        this.repository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<User> getUsers() {
        return repository.findAll();
    }

    @Transactional
    public void createOrUpdateUser(User user) {
        repository.save(user);
    }

    @Transactional(readOnly = true)
    public User findUserById(Long id) {
        return repository.getById(id);
    }


    @Transactional
    public void delete(Long id) {
        repository.delete(findUserById(id));
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findUserByEmail(username);
    }
}
