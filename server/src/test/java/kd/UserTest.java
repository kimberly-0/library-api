package kd;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import kd.models.*;

/**
 * * This class tests a constructor, and the compareTo and equals methods in the
 * User class
 */
public class UserTest {

    /**
     * This method tests that a user created without parameters for the number of
     * books will by default have 0 number of books on loan
     */
    @Test
    public void createUserWithoutNumOfBooks_ShouldHaveZeroNumOfBooks() {
        User user = new User("Jane", "Doe");
        int numOfBooks = user.getNumOfBooks();

        assertEquals(numOfBooks, 0);
    }

    /**
     * This method tests that when two users are compared, and the surname of
     * the first user lexicographically comes before the surname of the user
     * it's being compared to, it should return a negative integer
     */
    @Test
    public void compareToShouldReturnNegativeInt_IfSurnameUserComesBeforeSurnameUserItsBeingComparedTo() {
        User user1 = new User("Adam", "Apple");
        User user2 = new User("Jane", "Doe");

        int compareResult = user1.compareTo(user2);
        assertTrue(compareResult < 0);
    }

    /**
     * This method tests that when two users with the same surname are compared, and
     * the first name of the first user lexicographically comes before the first
     * name of the user it's being compared to, it should return a negative integer
     */
    @Test
    public void compareToShouldReturnNegativeInt_IfSurnameIsEqualAndFirstNameAuthorComesBeforeAuthorItsBeingComparedTo() {
        User user1 = new User("Adam", "Doe");
        User user2 = new User("Jane", "Doe");

        int compareResult = user1.compareTo(user2);
        assertTrue(compareResult < 0);
    }

    /**
     * This method tests that when two users are compared, and the surnames and
     * first names are equal, it should return 0
     */
    @Test
    public void compareToShouldReturnZero_IfSurnameAuthorIsEqualToAuthorItsBeingComparedTo() {
        User user1 = new User("Jane", "Doe");
        User user2 = new User("Jane", "Doe");

        int compareResult = user1.compareTo(user2);
        assertTrue(compareResult == 0);
    }

    /**
     * This method tests that when two users are compared, and the surname of
     * the first user lexicographically comes after the surname of the user
     * it's being compared to, it should return a positive integer
     */
    @Test
    public void compareToShouldReturnPositiveInt_IfSurnameUserComesAfterSurnameUserItsBeingComparedTo() {
        User user1 = new User("Lucy", "Montes");
        User user2 = new User("Jane", "Doe");

        int compareResult = user1.compareTo(user2);
        assertTrue(compareResult > 0);
    }

    /**
     * This method tests that when two users with the same surname are compared, and
     * the first name of the first user lexicographically comes after the first
     * name of the user it's being compared to, it should return a positive integer
     */
    @Test
    public void compareToShouldReturnPositiveInt_IfSurnameIsEqualAndFirstNameAuthorComesAfterAuthorItsBeingComparedTo() {
        User user1 = new User("Lucy", "Doe");
        User user2 = new User("Jane", "Doe");

        int compareResult = user1.compareTo(user2);
        assertTrue(compareResult > 0);
    }

    /**
     * This method tests that two users with the same name and number of books on
     * loan are perceived as equal.
     * The system does not allow multiple users with the same name to keep the
     * program simple and remain focussed on the generic SortedArrayList class and
     * the Comparable interface.
     */
    @Test
    public void twoUsersWithSameName_AreEqual() {
        User user1 = new User("Jane", "Doe");
        User user2 = new User("Jane", "Doe");

        assertTrue(user1.equals(user1));
        assertTrue(user1.equals(user2));
    }

}
