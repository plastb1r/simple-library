package com.example.library.services;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;

import com.example.library.entity.SecurityUser;
import com.example.library.entity.User;
import com.example.library.repos.BookRepository;
import com.example.library.repos.UserRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    private String loggedInUser;

    @PostConstruct
    public void init() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            userRepository.update(user);
        }
    }

    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        return new SecurityUser(userRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("user " + username + " was not found!")));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findUser(String name) {
        return userRepository.findByName(name);
    }

    public String addUser(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        if (userRepository.save(user) != 0) {
            return "Success";
        } else {
            return "Error";
        }
    }

    public String addUser(Map<String, String> userAttr) {
        if (userAttr.get("name") == null || userAttr.get("password") == null || userAttr.get("name").isEmpty()
                || userAttr.get("password").isEmpty() || this.contains(userAttr.get("name"))) {
            return "Error";
        } else {
            User user = new User(userAttr.get("name"), new BCryptPasswordEncoder().encode(userAttr.get("password")));
            userRepository.save(user);
            return "Success";
        }
    }

    public String deleteUser(String name) {
        if (bookRepository.updateWhenOwnerDelete(name) >= 0 && userRepository.deleteByName(name) != 0) {
            return "Success";
        } else {
            return "Error";
        }
    }

    public boolean contains(String name) {
        if (userRepository.findByName(name).isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    public String getLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            String userName = ((UserDetails) auth.getPrincipal()).getUsername();
            if (userName != null) {
                loggedInUser = userName;
            }
        }
        return loggedInUser;
    }

}
