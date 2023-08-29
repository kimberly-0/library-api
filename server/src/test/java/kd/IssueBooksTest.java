package kd;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

import java.io.PrintWriter;

import org.junit.Test;

/**
 * This class tests the issuing of books via command line interface input and
 * output
 */
public class IssueBooksTest extends TestBase {

        /*
         * TO-DO: For these tests, first the ListBooksAndUsersTest need to have passed !
         */

        /**
         * Tests the issue of a book that is available (not currently out on loan) to a
         * user that does not yet have 3 books on loan (the max allowed)
         */
        @Test
        public void testIssueBook() {
                importBooksAndUsers();

                // Issue book to Zoe Brown
                String issueDetails1 = String.format(
                                "Zoe%sBrown%sJava Gently%sBishop%s", System.lineSeparator(), System.lineSeparator(),
                                System.lineSeparator(), System.lineSeparator());
                // Issue first book to John Smith
                String issueDetails2 = String.format(
                                "John%sSmith%sPetri Nets%sReisig%s", System.lineSeparator(), System.lineSeparator(),
                                System.lineSeparator(), System.lineSeparator());
                // Issue second book to John Smith
                String issueDetails3 = String.format(
                                "John%sSmith%sConcurrent Programming%sSnow%s", System.lineSeparator(),
                                System.lineSeparator(),
                                System.lineSeparator(), System.lineSeparator());

                String issueBook = "i";
                String listBooks = "b";
                String listUsers = "u";
                String endProgram = "f";
                setInput(issueBook + System.lineSeparator() + issueDetails1 + System.lineSeparator() + issueBook
                                + System.lineSeparator() + issueDetails2 + System.lineSeparator() + issueBook
                                + System.lineSeparator()
                                + issueDetails3 + System.lineSeparator() + listBooks + System.lineSeparator()
                                + listUsers
                                + System.lineSeparator() + endProgram);

                PrintWriter outFile = null;
                io.runMenu(outFile);

                String[] output = getOutputLines();
                int numberOfOutputLines = output.length;

                // Check successful issue message of last issue
                assertThat(output[numberOfOutputLines - 41],
                                containsString(
                                                "The book has been issued."));

                // Check listed books
                assertThat(output[numberOfOutputLines - 29],
                                containsString(
                                                "Book -> title: Java Gently | author: Judith Bishop | on loan: yes | borrower: Zoe Brown"));
                assertThat(output[numberOfOutputLines - 27],
                                containsString(
                                                "Book -> title: Petri Nets | author: Wolfgang Reisig | on loan: yes | borrower: John Smith"));
                assertThat(output[numberOfOutputLines - 26],
                                containsString(
                                                "Book -> title: Concurrent Programming | author: C. R. Snow | on loan: yes | borrower: John Smith"));

                // Check listed users
                assertThat(output[numberOfOutputLines - 15],
                                containsString("User -> name: Zoe Brown | number of books: 1"));
                assertThat(output[numberOfOutputLines - 13],
                                containsString("User -> name: John Smith | number of books: 2"));
        }

        /**
         * Tests the issue of a book to a user that already has the maximum of 3 books
         * on loan
         */
        @Test
        public void testIssueBookToUserThatHasMaxBooksOnLoan() {
                importBooksAndUsers();

                // Issue 3 books to Zoe Brown
                String issueDetailsFirstBook = String.format(
                                "Zoe%sBrown%sFinite Transition Systems%sArnold%s", System.lineSeparator(),
                                System.lineSeparator(), System.lineSeparator(), System.lineSeparator());
                String issueDetailsSecondBook = String.format(
                                "Zoe%sBrown%sJava Gently%sBishop%s", System.lineSeparator(), System.lineSeparator(),
                                System.lineSeparator(), System.lineSeparator());
                String issueDetailsThirdBook = String.format(
                                "Zoe%sBrown%sConcurrent Programming%sSnow%s", System.lineSeparator(),
                                System.lineSeparator(), System.lineSeparator(), System.lineSeparator());

                // Issue 4th book that should give error message (user can have 3 max on loan)
                String issueDetailsFourthBook = String.format(
                                "Zoe%sBrown%sPetri Nets%sReisig%s", System.lineSeparator(),
                                System.lineSeparator(), System.lineSeparator(), System.lineSeparator());

                String issueBook = "i";
                String listBooks = "b";
                String listUsers = "u";
                String endProgram = "f";
                setInput(issueBook + System.lineSeparator() + issueDetailsFirstBook + System.lineSeparator() + issueBook
                                + System.lineSeparator() + issueDetailsSecondBook + System.lineSeparator() + issueBook
                                + System.lineSeparator() + issueDetailsThirdBook + System.lineSeparator() + issueBook
                                + System.lineSeparator() + issueDetailsFourthBook + System.lineSeparator() + listBooks
                                + System.lineSeparator() + listUsers + System.lineSeparator() + endProgram);

                PrintWriter outFile = null;
                io.runMenu(outFile);

                String[] output = getOutputLines();
                int numberOfOutputLines = output.length;

                // Check successful issue message of last issue
                assertThat(output[numberOfOutputLines - 41],
                                containsString("Zoe Brown already has 3 books on loan. The maximum allowed is 3. Please return a book first."));

                // Check listed books that should currently be issued to Zoe Brown
                assertThat(output[numberOfOutputLines - 30], containsString(
                                "Book -> title: Finite Transition Systems | author: Andre Arnold | on loan: yes | borrower: Zoe Brown"));
                assertThat(output[numberOfOutputLines - 29], containsString(
                                "Book -> title: Java Gently | author: Judith Bishop | on loan: yes | borrower: Zoe Brown"));
                assertThat(output[numberOfOutputLines - 26], containsString(
                                "Book -> title: Concurrent Programming | author: C. R. Snow | on loan: yes | borrower: Zoe Brown"));

                // Check listed books for book that was attempted to be issued but did not
                // because of max reached
                assertThat(output[numberOfOutputLines - 27],
                                containsString("Book -> title: Petri Nets | author: Wolfgang Reisig | on loan: no"));

                // Check listed users
                assertThat(output[numberOfOutputLines - 15],
                                containsString("User -> name: Zoe Brown | number of books: 3"));

        }

        /**
         * Tests the issue of an invalid book (does not exist, or not owned by the
         * library)
         */
        @Test
        public void testIssueOfInvalidBook() {

                importBooksAndUsers();

                String issueBook = "i";
                String issueDetailsUser = String.format(
                                "Zoe%sBrown%s", System.lineSeparator(), System.lineSeparator());
                String issueDetailsInvalidBook = String.format(
                                "Clean Code%sMartin%s", System.lineSeparator(), System.lineSeparator());
                String endProgram = "f";

                setInput(issueBook + System.lineSeparator() + issueDetailsUser + issueDetailsInvalidBook
                                + issueDetailsInvalidBook + endProgram);

                PrintWriter outFile = null;
                io.runMenu(outFile);

                String[] output = getOutputLines();

                // Check error message for issuing invalid book
                assertThat(output[16],
                                containsString("Invalid book, please enter the book's title again:"));
                assertThat(output[18],
                                containsString("Book was not found"));
        }

        /**
         * Tests the issue of a book to an invalid user (not registered by the library)
         */
        @Test
        public void testIssueOfBookToInvalidUser() {

                importBooksAndUsers();

                String issueBook = "i";
                String issueDetailsInvalidUser = String.format(
                                "Jane%sDoe%s", System.lineSeparator(), System.lineSeparator());
                String endProgram = "f";

                setInput(issueBook + System.lineSeparator() + issueDetailsInvalidUser + issueDetailsInvalidUser
                                + endProgram);

                PrintWriter outFile = null;
                io.runMenu(outFile);

                String[] output = getOutputLines();

                // Check error message for issuing book to invalid user
                assertThat(output[14],
                                containsString("Invalid user, please enter the user's first name again:"));
                assertThat(output[16],
                                containsString("User was not found"));
        }

}
