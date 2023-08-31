package dev.kimberly.library.models.user;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public Optional<User> singleUser(ObjectId id) {
        return userRepository.findUserById(id);
    }

    public User createUser(String firstName, String surname) {
        return userRepository.insert(new User(firstName, surname));
    }

    public Optional<User> removeUser(ObjectId id) {
        Optional<User> user = userRepository.findUserById(id);
        mongoTemplate.remove(new Query(Criteria.where("id").is(id)), User.class);
        return user;
    }
}
