package dev.kimberly.library.models;

import java.util.Objects;

public class User implements Comparable<User> {

    private final String firstName;
    private final String surname;
    private Integer numOfBooks;

    public User(String firstName, String surname) {
        this.firstName = firstName;
        this.surname = surname;
        this.numOfBooks = 0;
    }

    public User(String firstName, String surname, Integer numOfBooks) {
        this.firstName = firstName;
        this.surname = surname;
        this.numOfBooks = numOfBooks;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public int getNumOfBooks() {
        return numOfBooks;
    }

    public void setNumOfBooks(int i) {
        this.numOfBooks = i;
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