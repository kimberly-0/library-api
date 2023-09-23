package dev.kimberly.library.models;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "books")
@Data // creates getters and setters
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book implements Comparable<Book> {

    @Id
    private String id;
    @NotBlank(message = "Book title cannot be empty")
    private String title;
    @NotBlank(message = "Author's first name cannot be empty")
    private String authorFirstName;
    @NotBlank(message = "Author's surname cannot be empty")
    private String authorSurname;
    private boolean onLoan;
    private User borrower;
    private String coverImageURL;

    public Book(String title, String authorFirstName, String authorSurname) {
        this.title = title;
        this.authorFirstName = authorFirstName;
        this.authorSurname = authorSurname;
        this.onLoan = false;
        this.borrower = null;
        this.coverImageURL = null;
    }

    public Book(String title, String authorFirstName, String authorSurname, String coverImageURL) {
        this.title = title;
        this.authorFirstName = authorFirstName;
        this.authorSurname = authorSurname;
        this.onLoan = false;
        this.borrower = null;
        this.coverImageURL = coverImageURL;
    }

    public Book(String title, String authorFirstName, String authorSurname, boolean onLoan, User borrower, String coverImageURL) {
        this.title = title;
        this.authorFirstName = authorFirstName;
        this.authorSurname = authorSurname;
        this.onLoan = onLoan;
        this.borrower = borrower;
        this.coverImageURL = coverImageURL;
    }

    /**
     * This method compares the author's surname to sort in ascending order
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(Book b) {
        return authorSurname.compareTo(b.authorSurname);
    }

    /**
     * This method overrides the default toString method provided by the Object
     * class to ensure the values are showcased when the object is printed. A
     * conditional statement is used to only show the borrower if the book is
     * currently on loan.
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("%s%s%s%s%s %s%s%s%s",
                "Book -> ",
                "title: ", title,
                " | author: ", authorFirstName, authorSurname,
                " | on loan: ", (onLoan ? "yes" : "no"),
                (borrower != null ? " | borrower: " + borrower.getFirstName() + " " + borrower.getSurname() : ""));
    }

    /**
     * This method overrides the default equals method provided by the Object class
     * so that the program perceives books with the same name to be the same Book
     * and not allow multiple books with the same name. This keeps the program
     * simple and allows for the main focus to be on the generic SortedArrayList
     * class and the Comparable interface.
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (!(obj instanceof Book user))
            return false;

        return title.equals(user.title)
                && authorFirstName.equals(user.authorFirstName)
                && authorSurname.equals(user.authorSurname)
                && onLoan == user.onLoan
                && (borrower == null ? borrower == user.borrower : borrower.equals(user.borrower));
    }
}
