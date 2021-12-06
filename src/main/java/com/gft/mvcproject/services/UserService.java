package com.gft.mvcproject.services;

import com.gft.mvcproject.entities.User;
import com.gft.mvcproject.repositories.user.UserRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    public User getUser(Long id) throws NotFoundException {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new NotFoundException("Usuário não encontrado!");
        }

        return user.get();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public boolean usernameExists(String username) {
        return userRepository.getUserByUsername(username) != null;
    }

    public List<User> listAdm() {
        return userRepository.listAdm();
    }

    public List<User> listSm() {
        return userRepository.listSm();
    }

    public List<User> listScrumMaster() {
        return userRepository.listScrumMasters();
    }

}
