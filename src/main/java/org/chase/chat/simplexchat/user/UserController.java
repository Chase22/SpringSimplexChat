package org.chase.chat.simplexchat.user;

import org.apache.commons.collections4.IteratorUtils;
import org.chase.chat.simplexchat.misc.RestApiController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

@RestApiController("user")
public class UserController {
    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = requireNonNull(userService,"chatService");
    }

    //For Connection checks
    @GetMapping("test")
    @ResponseStatus(HttpStatus.OK)
    public void replyOK() {}

    @GetMapping("/")
    public ResponseEntity<List<UserEntity>> getAllUsers(@PathVariable("id") final String id) {
        Iterable<UserEntity> users = userService.getAllUser();

        return new ResponseEntity<>(IteratorUtils.toList(users.iterator()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getUser(@PathVariable("id") final String id) {
        Optional<UserEntity> user = userService.getUserById(id);

        return user.map(userEntity1 -> new ResponseEntity<>(userEntity1, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @PostMapping("")
    public ResponseEntity<UserEntity> createUser(@RequestBody final UserEntity userEntity) {
        userService.insertOrUpdate(userEntity);
        return new ResponseEntity<>(userEntity, HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<UserEntity> upadteChat(@RequestBody final UserEntity userEntity) {
        userService.insertOrUpdate(userEntity);
        return new ResponseEntity<>(userEntity, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") final String id) {
        userService.getUserById(id).ifPresent(chat ->  {
            userService.deleteChatById(id);
        });
    }

}
