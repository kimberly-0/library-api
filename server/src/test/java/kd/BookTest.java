package kd;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import kd.models.*;

/**
 * This class tests a constructor, and the compareTo and equals methods in the
 * Book class
 */
public class BookTest {

    /**
     * This method tests that a book created without parameters for the loan status
     * and borrower will by default not be on loan and have no borrower
     */
    @Test
    public void createBookWithoutLoanStatusOrBorrower_ShouldNotBeOnLoan() {
        Book book = new Book("Java Gently", "Judith", "Bishop");
        boolean bookLoanStatus = book.isOnLoan();
        User bookBorrower = book.getBorrower();

        assertFalse(bookLoanStatus);
        assertNull(bookBorrower);
    }

    /**
     * This method tests that when two books are compared, and the surname of
     * the first author lexicographically comes before the surname of the author
     * it's being compared to, it should return a negative integer
     */
    @Test
    public void compareToShouldReturnNegativeInt_IfSurnameAuthorComesBeforeAuthorItsBeingComparedTo() {
        Book book1 = new Book("Finite Transition Systems", "Andre", "Arnold");
        Book book2 = new Book("Java Gently", "Judith", "Bishop");

        int compareResult = book1.compareTo(book2);
        assertTrue(compareResult < 0);
    }

    /**
     * This method tests that when two books are compared, and the surname of
     * the first author is the same as the surname of the author it's being compared
     * to, it should return 0
     */
    @Test
    public void compareToShouldReturnZero_IfSurnameAuthorIsEqualToAuthorItsBeingComparedTo() {
        Book book1 = new Book("Finite Transition Systems", "Andre", "Doe");
        Book book2 = new Book("Java Gently", "Judith", "Doe");

        int compareResult = book1.compareTo(book2);
        assertTrue(compareResult == 0);
    }

    /**
     * This method tests that when two books are compared, and the surname of
     * the first author lexicographically comes after the surname of the author
     * it's being compared to, it should return a positive integer
     */
    @Test
    public void compareToShouldReturnPositiveInt_IfSurnameAuthorComesAfterAuthorItsBeingComparedTo() {
        Book book1 = new Book("Concurrent Programming", "C. R.", "Snow");
        Book book2 = new Book("Java Gently", "Judith", "Bishop");

        int compareResult = book1.compareTo(book2);
        assertTrue(compareResult > 0);
    }

    /**
     * This method tests that two books with the same title and author are perceived
     * as equal.
     * The system does not allow multiple books with the same name to keep the
     * program simple and remain focussed on the generic SortedArrayList class and
     * the Comparable interface.
     */
    @Test
    public void twoBooksWithSameTitleAndAuthor_AreEqual() {
        Book book1 = new Book("Java Gently", "Judith", "Bishop");
        Book book2 = new Book("Java Gently", "Judith", "Bishop");

        assertTrue(book1.equals(book1));
        assertTrue(book1.equals(book2));
    }

}
