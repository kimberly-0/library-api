package dev.kimberly.library.models.user;

import dev.kimberly.library.models.book.Book;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<List<User>>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        return new ResponseEntity<User>(userService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> addUser(@Valid @RequestBody Map<String, String> payload) {
        User user = new User(payload.get("firstName"), payload.get("surname"));
        return new ResponseEntity<User>(userService.save(user), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> editUser(@Valid @PathVariable String id, @Valid @RequestBody Map<String, String> payload) {
        User user = userService.findById(id);
        if (payload.get("firstName") != null) {
            user.setFirstName(payload.get("firstName"));
        }
        if (payload.get("surname") != null) {
            user.setSurname(payload.get("surname"));
        }
        return new ResponseEntity<User>(userService.save(user), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        if (userService.findById(id) != null) {
            userService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
