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
        return new ResponseEntity<List<Book>>(bookService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable ObjectId id) {
        return new ResponseEntity<Book>(bookService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Map<String, String> payload) {
        Book book = new Book (payload.get("title"), payload.get("authorFirstName"), payload.get("authorSurname"));
        return new ResponseEntity<Book>(bookService.save(book), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable ObjectId id) {
        if (bookService.findById(id) != null) {
            bookService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("/{bookId}/issue/{userId}")
    public ResponseEntity<Book> issueBook(@PathVariable ObjectId bookId, @PathVariable ObjectId userId) {
        Book issuedBook = bookService.issueBook(bookId, userId);
        if (issuedBook != null) {
            return new ResponseEntity<Book>(issuedBook, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{bookId}/return/{userId}")
    public ResponseEntity<Book> returnBook(@PathVariable ObjectId bookId, @PathVariable ObjectId userId) {
        Book returnedBook = bookService.returnBook(bookId, userId);
        if (returnedBook != null) {
            return new ResponseEntity<Book>(returnedBook, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
