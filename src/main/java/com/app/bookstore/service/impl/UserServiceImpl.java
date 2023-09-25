package com.app.bookstore.service.impl;

import com.app.bookstore.dto.UserRegistrationRequestDto;
import com.app.bookstore.dto.UserRegistrationResponseDto;
import com.app.bookstore.exceptions.EntityNotFoundException;
import com.app.bookstore.exceptions.RegistrationException;
import com.app.bookstore.mapper.UserMapper;
import com.app.bookstore.model.Role;
import com.app.bookstore.model.RoleName;
import com.app.bookstore.model.ShoppingCart;
import com.app.bookstore.model.User;
import com.app.bookstore.repository.cart.ShoppingCartRepository;
import com.app.bookstore.repository.role.RoleRepository;
import com.app.bookstore.repository.user.UserRepository;
import com.app.bookstore.service.UserService;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final ShoppingCartRepository shoppingCartRepository;

    @Override
    public UserRegistrationResponseDto register(UserRegistrationRequestDto request)
            throws RegistrationException {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RegistrationException("The user is already registered");
        }
        User user = userMapper.toModel(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        Role role = roleRepository.getByName(RoleName.ROLE_USER);
        user.setRoles(Set.of(role));
        User savedUser = userRepository.save(user);
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(savedUser);
        shoppingCartRepository.save(shoppingCart);
        return userMapper.toUserResponse(savedUser);
    }

    @Override
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find user with email " + authentication.getName()));
    }
}
