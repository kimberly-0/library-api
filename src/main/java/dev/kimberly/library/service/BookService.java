package dev.kimberly.library.service;

import dev.kimberly.library.models.Book;
import dev.kimberly.library.models.User;
import dev.kimberly.library.repository.BookRepository;
import dev.kimberly.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired // Spring Boot will instantiate class for us
    private BookRepository bookRepository;

    @Autowired // Spring Boot will instantiate class for us
    private UserRepository userRepository;

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findById(String id) {
        return bookRepository.findById(id).orElse(null);
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public void deleteById(String id) {
        bookRepository.deleteById(id);
    }

    public Book issueBook(String bookId, String userId) {
        Book book = findById(bookId);
        User user = userRepository.findById(userId).orElse(null);

        if (book != null && !book.isOnLoan() && user != null) {

            // User is allowed to borrow a maximum of 3 books at one time
//            if (user.getNumOfBooks() < 3) {
                book.setBorrower(user);
                book.setOnLoan(true);
                user.setNumOfBooks(user.getNumOfBooks() + 1);
                userRepository.save(user);
                return save(book);
//            } else {
//                throw new Exception("Maximum number of books borrowed");
//            }


        }

        // Handle errors (e.g., book not found, book already borrowed, user not found)
        return null;
    }

    public Book returnBook(String bookId) {
        Book book = findById(bookId);

        if (book != null && book.isOnLoan() && book.getBorrower() != null) {

            String userId = book.getBorrower().getId();
            User user = userRepository.findById(userId).orElse(null);

            if (user.getNumOfBooks() > 0) {

                user.setNumOfBooks(user.getNumOfBooks() - 1);
                userRepository.save(user);

                book.setBorrower(null);
                book.setOnLoan(false);

                return save(book);
            }
        }

        // Handle errors (e.g., book not found, book already borrowed, user not found)
        return null;
    }

}
