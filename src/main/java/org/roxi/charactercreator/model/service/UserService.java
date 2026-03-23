package org.roxi.charactercreator.model.service;
import org.roxi.charactercreator.model.Role;
import org.roxi.charactercreator.model.User;
import org.roxi.charactercreator.model.repository.UserRepository;

import java.util.List;
import java.util.regex.Pattern;

public class UserService {
    private final UserRepository userRepository;

    private static final String EMAIL_REGEX ="^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) return false;
        return Pattern.compile(EMAIL_REGEX).matcher(email).matches();
    }

    public boolean registerNewUser(User user) {
        if (!isValidEmail(user.getEmail())) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        if (user.getPassword().length() < 4) {
            throw new IllegalArgumentException("Password must be at least 4 characters.");
        }
        return userRepository.register(user);
    }

    public User login(String email, String password) {
        return userRepository.login(email, password);
    }
    public List<User> getAllUsers() {
        return userRepository.readAllUsers();
    }
    public void deleteUserAccount(User actor, String emailToDelete) {
        if (actor.getRole() != Role.ADMIN) {
            throw new SecurityException("You do not have permission to delete users.");
        }
        userRepository.deleteUserByEmail(emailToDelete);
        System.out.println("Admin " + actor.getUsername() + " successfully deleted " + emailToDelete);}
}
