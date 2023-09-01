package dev.kimberly.library.models.book;

import dev.kimberly.library.models.user.User;
import dev.kimberly.library.models.user.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired // Spring Boot will instantiate class for us
    private BookRepository bookRepository;

    @Autowired // Spring Boot will instantiate class for us
    private UserRepository userRepository;

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findById(ObjectId id) {
        return bookRepository.findById(id).orElse(null);
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public void deleteById(ObjectId id) {
        bookRepository.deleteById(id);
    }

    public Book issueBook(ObjectId bookId, ObjectId userId) {
        Book book = findById(bookId);
        User user = userRepository.findById(userId).orElse(null);

        if (book != null && !book.isOnLoan() && user != null) {
            book.setBorrower(user);
            book.setOnLoan(true);
            user.setNumOfBooks(user.getNumOfBooks() + 1);
            userRepository.save(user);
            return save(book);
        }

        // Handle errors (e.g., book not found, book already borrowed, user not found)
        return null;
    }

    public Book returnBook(ObjectId bookId, ObjectId userId) {
        Book book = findById(bookId);
        User user = userRepository.findById(userId).orElse(null);

        if (book != null && book.isOnLoan() && book.getBorrower().equals(user) && user.getNumOfBooks() > 0) {
            book.setBorrower(null);
            book.setOnLoan(false);
            user.setNumOfBooks(user.getNumOfBooks() - 1);
            userRepository.save(user);
            return save(book);
        }

        // Handle errors (e.g., book not found, book already borrowed, user not found)
        return null;
    }

}
