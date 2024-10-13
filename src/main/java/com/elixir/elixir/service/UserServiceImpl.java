package com.elixir.elixir.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.stream.Collectors;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.elixir.elixir.entity.dto.UserAdminDTO;

import com.elixir.elixir.entity.User;
import com.elixir.elixir.entity.dto.UserDTO;
import com.elixir.elixir.repository.UserRepository;
import com.elixir.elixir.entity.Role;
import com.elixir.elixir.service.Interface.UserService;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal(); 
        return user.getUser_id(); // Devuelve el ID del usuario
    }
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    public UserDTO convertToDTO(User user) {
        if (user == null) {
            return null;
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getRealUserName());
        userDTO.setEmail(user.getEmail());
        userDTO.setName(user.getName());
        userDTO.setLast_name(user.getLast_name());
        return userDTO;
    }

    public UserAdminDTO convertToDTOtoAdmin(User user) {
        if (user == null) {
            return null;
        }
        UserAdminDTO userDTO = new UserAdminDTO();
        userDTO.setUsername(user.getRealUserName());
        userDTO.setEmail(user.getEmail());
        userDTO.setName(user.getName());
        userDTO.setLast_name(user.getLast_name());
        userDTO.setState(user.getState());

        return userDTO;
    }

    public UserDTO userInfoUpdate(String username, String email, String name, String last_name) {
        Long userId = getCurrentUserId();
        User user = getUserById(userId);
        if (user.getRealUserName()!=username) {
            user.setUsername(username);
        }
        if (user.getEmail()!=email) {
            user.setEmail(email);
        }
        if (user.getName()!=name) {
            user.setName(name);
        }
        if (user.getLast_name()!=last_name) {
            user.setLast_name(last_name);
        }
        userRepository.save(user);
        
        return convertToDTO(user);
    }

    public UserDTO deactivateUser(Long userId) {
    User user = getUserById(userId);
    if (user.getRole() == Role.ADMIN) {
        throw new AccessDeniedException("Cannot deactivate an ADMIN user.");
    }
    user.setState(false);
    userRepository.save(user);
    return convertToDTO(user);
    }

    public UserDTO activateUser(Long userId) {
        User user = getUserById(userId);
        user.setState(true);
        userRepository.save(user);
        return convertToDTO(user);
    }

    public List<UserAdminDTO> getAllUsers() {
    List<User> users = userRepository.findAll();
    return users.stream()
                .map(this::convertToDTOtoAdmin)
                .collect(Collectors.toList());
    }
}
