package io.github.Chase22.simplexchat.user;

import io.github.Chase22.simplexchat.misc.RestApiController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

@RestApiController("api/user")
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
        return ResponseEntity.ok(userService.getAllUser());
    }

    @GetMapping("/{id}")
    public ResponseEntity getUser(@PathVariable("id") final String id) {
        Optional<UserEntity> user = userService.getUserById(id);

        return user.<ResponseEntity>map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("")
    public ResponseEntity<UserEntity> createUser(@RequestBody final UserEntity userEntity) {
        userService.save(userEntity);
        return ResponseEntity.ok(userEntity);
    }

    @PutMapping("")
    public ResponseEntity<UserEntity> updateUser(@RequestBody final UserEntity userEntity) {
        userService.save(userEntity);
        return ResponseEntity.ok(userEntity);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") final String id) {
        userService.deleteUserById(id);
    }

}
