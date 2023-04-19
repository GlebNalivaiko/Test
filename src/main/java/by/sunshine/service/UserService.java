package by.sunshine.service;

import by.sunshine.converter.UserConverter;
import by.sunshine.dto.AuthorizationRequestUser;
import by.sunshine.dto.RegistrationRequestUser;
import by.sunshine.dto.UserDto;
import by.sunshine.entity.User;
import by.sunshine.repository.RoleRepository;
import by.sunshine.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;


    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User save(RegistrationRequestUser requestUser, UserDto userDto) {
        User user = userConverter.convert(requestUser, userDto);
        user.setRole(roleRepository.findById(1).orElseThrow());
        user.setPassword(passwordEncoder.encode(requestUser.getPassword()));
        User updateUser = userRepository.save(user);
        userConverter.convert(user, userDto);
        return updateUser;
    }

    public Optional<User> findByEmailAndPassword(AuthorizationRequestUser requestUser) {
        User user = userRepository.findByEmail(requestUser.getEmail()).orElse(null);
        if (user != null) {
            if (passwordEncoder.matches(requestUser.getPassword(), user.getPassword())) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
