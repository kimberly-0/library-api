package dev.kimberly.library.models.book;

import dev.kimberly.library.models.user.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired // Spring Boot will instantiate class for us
    private BookRepository bookRepository;

    public List<Book> allBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> singleBook(ObjectId id) {
        return bookRepository.findBookById(id);
    }

    public Book createBook(String title, String authorFirstName, String authorSurname) {
        return bookRepository.insert(new Book(title, authorFirstName, authorSurname));
    }
}
