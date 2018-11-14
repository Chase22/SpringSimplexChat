package org.chase.chat.simplexchat.security;

import org.chase.chat.simplexchat.user.UserEntity;
import org.chase.chat.simplexchat.user.UserService;
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
