package dev.kimberly.library.models.book;

import dev.kimberly.library.models.user.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired // Spring Boot will instantiate class for us
    private BookRepository bookRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Book> allBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> singleBook(ObjectId id) {
        return bookRepository.findBookById(id);
    }

    public Book createBook(String title, String authorFirstName, String authorSurname) {
        return bookRepository.insert(new Book(title, authorFirstName, authorSurname));
    }

    public Optional<Book> removeBook(ObjectId id) {
        Optional<Book> book = bookRepository.findBookById(id);
        mongoTemplate.remove(new Query(Criteria.where("id").is(id)), Book.class);
        return book;
    }
}
