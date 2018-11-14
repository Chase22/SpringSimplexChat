package org.chase.chat.simplexchat.user;

import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(final UserRepository userRepository) {
        this.userRepository = requireNonNull(userRepository, "chatRepository");
    }

    public Optional<UserEntity> getUserById(final String id) {
        return userRepository.findById(id);
    }

    public Iterable<UserEntity> getAllUser() {
        return userRepository.findAll();
    }

    public void save(final UserEntity userEntity) {
        userRepository.save(userEntity);
    }

    public void deleteChatById(String id) {
        userRepository.deleteById(id);
    }
}
