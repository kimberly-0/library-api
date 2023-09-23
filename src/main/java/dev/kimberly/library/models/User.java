package dev.kimberly.library.models;

import java.util.Objects;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Data // creates getters and setters
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements Comparable<User> {

    @Id
    private String id;
    @NotBlank(message = "First name cannot be empty")
    private String firstName;
    @NotBlank(message = "Surname cannot be empty")
    private String surname;
    @Max(value=3, message="A member can borrow a maximum of 3 books at one time")
    private Integer numOfBooks;

    public User(String firstName, String surname) {
        this.firstName = firstName;
        this.surname = surname;
        this.numOfBooks = 0;
    }

    /**
     * This method compares the user's surname after which the user's first name to
     * sort in ascending order
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(User u) {
        int snCmp = surname.compareTo(u.surname);
        if (snCmp != 0)
            return snCmp;
        return firstName.compareTo(u.firstName);
    }

    /**
     * This method overrides the default toString method provided by the Object
     * class to ensure the values are showcased when the object is printed.
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("%s%s%s %s%s%d",
                "User -> ",
                "name: ", firstName, surname,
                " | number of books: ", numOfBooks);
    }

    /**
     * This method overrides the default equals method provided by the Object class
     * so that the program perceives users with the same name to be the same User
     * and not allow multiple users with the same name. This keeps the program
     * simple and allows for the main focus to be on the generic SortedArrayList
     * class and the Comparable interface.
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (!(obj instanceof User user))
            return false;

        return firstName.equals(user.firstName)
                && surname.equals(user.surname)
                && Objects.equals(numOfBooks, user.numOfBooks);
    }

}
