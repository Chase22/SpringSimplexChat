package io.github.Chase22.simplexchat.security;

import io.github.Chase22.simplexchat.user.UserEntity;
import io.github.Chase22.simplexchat.user.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ChatUserDetailService implements UserDetailsService {
    private UserService userService;

    public ChatUserDetailService(UserService userService) {
        this.userService = Objects.requireNonNull(userService, "userService");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userService.getUserById(username).orElseThrow(NullPointerException::new);
        return new ChatUserPrincipal(userEntity);
    }
}
