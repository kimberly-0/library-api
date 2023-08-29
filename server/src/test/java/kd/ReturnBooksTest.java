package kd;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

import java.io.PrintWriter;

import org.junit.Test;

/**
 * This class tests the returning of books via command line interface input and
 * output
 */
public class ReturnBooksTest extends TestBase {

        /*
         * TO-DO: For these tests, first the ListBooksAndUsersTest need to have passed !
         */

        /**
         * Tests the return a book that is currently out on loan
         */
        @Test
        public void testReturnBook() {
                importBooksAndUsers();

                String issueBook = "i";
                String issueDetails = String.format(
                                "Zoe%sBrown%sJava Gently%sBishop%s", System.lineSeparator(), System.lineSeparator(),
                                System.lineSeparator(), System.lineSeparator());
                String returnBook = "r";
                String returnDetails = String.format("Zoe%sBrown%sJava Gently%sBishop%s", System.lineSeparator(),
                                System.lineSeparator(),
                                System.lineSeparator(), System.lineSeparator());
                String listBooks = "b";
                String listUsers = "u";
                String endProgram = "f";
                setInput(issueBook + System.lineSeparator() + issueDetails + System.lineSeparator() + returnBook
                                + System.lineSeparator() + returnDetails + System.lineSeparator() + listBooks
                                + System.lineSeparator()
                                + listUsers + System.lineSeparator() + endProgram);

                PrintWriter outFile = null;
                io.runMenu(outFile);

                String[] output = getOutputLines();
                int numberOfOutputLines = output.length;

                // Check successful return message
                assertThat(output[numberOfOutputLines - 41],
                                containsString(
                                                "The book has been returned."));

                // Check listed books
                assertThat(output[numberOfOutputLines - 29],
                                containsString(
                                                "Book -> title: Java Gently | author: Judith Bishop | on loan: no"));

                // Check listed users
                assertThat(output[numberOfOutputLines - 15],
                                containsString("User -> name: Zoe Brown | number of books: 0"));
        }

        /**
         * Tests the attempt to return a book that is not currently out on loan
         */
        @Test
        public void testReturnBookNotOnLoan() {
                importBooksAndUsers();

                String returnBook = "r";
                String returnDetails = String.format("Zoe%sBrown%sJava Gently%sBishop%s", System.lineSeparator(),
                                System.lineSeparator(),
                                System.lineSeparator(), System.lineSeparator());
                String listBooks = "b";
                String listUsers = "u";
                String endProgram = "f";
                setInput(returnBook + System.lineSeparator() + returnDetails + System.lineSeparator() + listBooks
                                + System.lineSeparator() + listUsers + System.lineSeparator() + endProgram);

                PrintWriter outFile = null;
                io.runMenu(outFile);

                String[] output = getOutputLines();
                int numberOfOutputLines = output.length;

                // Check error return message
                assertThat(output[numberOfOutputLines - 41], containsString(
                                "Zoe Brown is not in possession of the book Java Gently by Judith Bishop."));
        }

        /**
         * Tests the return of an invalid book (does not exist, or not owned by the
         * library)
         */
        @Test
        public void testReturnOfInvalidBook() {
                // -
        }

        /**
         * Tests the return of a book from an invalid user (not registered by the
         * library)
         */
        @Test
        public void testReturnOfBookToInvalidUser() {
                // TO-DO
        }

}
