package dev.kimberly.library.models.book;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<Book> getBookById(@PathVariable String id) {
        return new ResponseEntity<Book>(bookService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@Valid @RequestBody Map<String, String> payload) {
        Book book = new Book (payload.get("title"), payload.get("authorFirstName"), payload.get("authorSurname"));
        return new ResponseEntity<Book>(bookService.save(book), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> editBook(@Valid @PathVariable String id, @Valid @RequestBody Map<String, String> payload) {
        Book book = bookService.findById(id);
        if (payload.get("title") != null) {
            book.setTitle(payload.get("title"));
        }
        if (payload.get("authorFirstName") != null) {
            book.setAuthorFirstName(payload.get("authorFirstName"));
        }
        if (payload.get("authorSurname") != null) {
            book.setAuthorSurname(payload.get("authorSurname"));
        }
        return new ResponseEntity<Book>(bookService.save(book), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable String id) {
        if (bookService.findById(id) != null) {
            bookService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("/{bookId}/issue/{userId}")
    public ResponseEntity<Book> issueBook(@Valid @PathVariable String bookId, @PathVariable String userId) {
        Book issuedBook = bookService.issueBook(bookId, userId);
        if (issuedBook != null) {
            return new ResponseEntity<Book>(issuedBook, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{bookId}/return")
    public ResponseEntity<Book> returnBook(@Valid @PathVariable String bookId) {
        Book returnedBook = bookService.returnBook(bookId);
        if (returnedBook != null) {
            return new ResponseEntity<Book>(returnedBook, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
