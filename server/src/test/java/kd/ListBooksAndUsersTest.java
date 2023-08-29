package kd;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

import java.io.PrintWriter;

import org.junit.Test;

/**
 * This class tests the listing of books and users via command line interface
 * input and output
 */
public class ListBooksAndUsersTest extends TestBase {

        @Test
        public void testPrintBooks() {
                importBooksAndUsers();

                String listBooks = "b";
                String endProgram = "f";
                setInput(listBooks + System.lineSeparator() + endProgram);

                PrintWriter outFile = null;
                io.runMenu(outFile);

                String[] output = getOutputLines();
                int numberOfOutputLines = output.length;

                assertThat(output[numberOfOutputLines - 16],
                                containsString("Book -> title: Finite Transition Systems | author: Andre Arnold | on loan: no"));
                assertThat(output[numberOfOutputLines - 15],
                                containsString("Book -> title: Java Gently | author: Judith Bishop | on loan: no"));
                assertThat(output[numberOfOutputLines - 14],
                                containsString("Book -> title: Concurrent Programming | author: Stephen J. Hartley | on loan: no"));
                assertThat(output[numberOfOutputLines - 13],
                                containsString("Book -> title: Petri Nets | author: Wolfgang Reisig | on loan: no"));
                assertThat(output[numberOfOutputLines - 12],
                                containsString("Book -> title: Concurrent Programming | author: C. R. Snow | on loan: no"));
                // Other lines contain the printed menu
        }

        @Test
        public void testPrintBooksWithNoBooks() {
                String listBooks = "b";
                String endProgram = "f";
                setInput(listBooks + System.lineSeparator() + endProgram);

                PrintWriter outFile = null;
                io.runMenu(outFile);

                assertThat(outputStream.toString(), containsString("No items found"));
        }

        @Test
        public void testPrintUsers() {
                importBooksAndUsers();

                String listUsers = "u";
                String endProgram = "f";
                setInput(listUsers + System.lineSeparator() + endProgram);

                PrintWriter outFile = null;
                io.runMenu(outFile);

                String[] output = getOutputLines();
                int numberOfOutputLines = output.length;

                assertThat(output[numberOfOutputLines - 15],
                                containsString("User -> name: Zoe Brown | number of books: 0"));
                assertThat(output[numberOfOutputLines - 14],
                                containsString("User -> name: Anna Smith | number of books: 0"));
                assertThat(output[numberOfOutputLines - 13],
                                containsString("User -> name: John Smith | number of books: 0"));
                assertThat(output[numberOfOutputLines - 12],
                                containsString("User -> name: John Williams | number of books: 0"));
                // Other lines contain the printed menu
        }

        @Test
        public void testPrintUsersWithNoUsers() {
                String listUsers = "u";
                String endProgram = "f";
                setInput(listUsers + System.lineSeparator() + endProgram);

                PrintWriter outFile = null;
                io.runMenu(outFile);

                assertThat(outputStream.toString(), containsString("No items found"));
        }

}
