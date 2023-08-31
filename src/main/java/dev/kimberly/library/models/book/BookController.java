package dev.kimberly.library.models.book;

import dev.kimberly.library.models.user.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return new ResponseEntity<List<Book>>(bookService.allBooks(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Book>> getSingleBookById(@PathVariable ObjectId id) {
        return new ResponseEntity<Optional<Book>>(bookService.singleBook(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Map<String, String> payload) {
        return new ResponseEntity<Book>(bookService.createBook(payload.get("title"), payload.get("authorFirstName"), payload.get("authorSurname")), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Optional<Book>> createBook(@PathVariable ObjectId id) {
        return new ResponseEntity<Optional<Book>>(bookService.removeBook(id), HttpStatus.ACCEPTED);
    }

}
