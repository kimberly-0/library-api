package kd;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;

import kd.models.*;

/**
 * Enable simulation of user input via command line interface and record output
 */
public class TestBase {

    protected IO io;

    // Store command line interface output
    protected final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    // Simulate command line interface user input (scanner)
    InputStream stdin;

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outputStream));
        stdin = System.in;
        io = new IO();
    }

    @After
    public void tearDown() {
        System.setIn(stdin);
    }

    // Set the user input for scanner to read from System.in
    void setInput(String str) {
        System.setIn(new ByteArrayInputStream((str + "\r\n").getBytes()));
    }

    // Get the command line interface output
    String[] getOutputLines() {
        return outputStream.toString().split("\\r?\\n");
    }

    public void importBooksAndUsers() {
        io.addBook(new Book("Concurrent Programming", "C. R.", "Snow"));
        io.addBook(new Book("Concurrent Programming", "Stephen J.", "Hartley"));
        io.addBook(new Book("Java Gently", "Judith", "Bishop"));
        io.addBook(new Book("Petri Nets", "Wolfgang", "Reisig"));
        io.addBook(new Book("Finite Transition Systems", "Andre", "Arnold"));
        io.addUser(new User("Anna", "Smith"));
        io.addUser(new User("Zoe", "Brown"));
        io.addUser(new User("John", "Williams"));
        io.addUser(new User("John", "Smith"));
    }

}
