package dev.kimberly.library;

import java.io.PrintWriter;
import java.util.Scanner;

import dev.kimberly.library.models.book.Book;
import dev.kimberly.library.models.Library;
import dev.kimberly.library.models.user.User;

/**
 * <h2>Library management system</h2>
 * This project is an interactive library management system with a command line
 * interface. The program enables the importing of books and users from a file,
 * and enables the system user to issue and return books.
 *
 * The IO class manages the input and output of the program, i.e. the command
 * line interface menus, the user's response, and the printed results.
 */
public class IO {

    Library library = new Library();

    public void runMenu(PrintWriter outFile) {
        Scanner scanner = new Scanner(System.in);
        boolean quit = false;
        while (!quit) {

            printMenu();

            char choice = scanner.next().charAt(0);
            scanner.nextLine(); // Retrieving enter to make sure the next reading is from the next line

            switch (choice) {
                case 'f' -> { // Finish running the program
                    System.out.printf("%n%s%n", "Closing the program ...");
                    quit = true;
                    scanner.close();
                }
                case 'b' -> // List information about all books in library
                        listItems(library.getBooks());
                case 'u' -> // List information about al users
                        listItems(library.getUsers());
                case 'i' -> // Issue a book
                        library.issueBook(scanner, outFile);
                case 'r' -> // Return a book
                        library.returnBook(scanner);
                default -> System.out.println("Invalid entry, try again");
            }
        }
    }

    private static void printMenu() {
        System.out.printf("%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n",
                "--------- MENU ---------",
                "f - finish",
                "b - list all books",
                "u - list all users",
                "i - issue a book",
                "r - return a book",
                "------------------------",
                "Type a letter and press Enter");
    }

    private <E extends Comparable<E>> void listItems(SortedArrayList<E> e) {
        System.out.print(System.lineSeparator());
        if (!e.isEmpty()) {
            for (E elem : e)
                System.out.println(elem);
        } else {
            System.out.println("No items found");
        }
    }

    /**
     * This method is the main method to run to start the program
     * 
     * @param args The command line arguments
     */
    public static void main(String[] args) {
        IO io = new IO();

        /*
         * Add books and users to library
         */
        io.library.addBook(new Book("Concurrent Programming", "C. R.", "Snow", false, null));
        io.library.addBook(new Book("Concurrent Programming", "Stephen J.", "Hartley", false, null));
        io.library.addBook(new Book("Java Gently", "Judith", "Bishop", false, null));
        io.library.addBook(new Book("Petri Nets", "Wolfgang", "Reisig", false, null));
        io.library.addBook(new Book("Finite Transition Systems", "Andre", "Arnold", false, null));

        io.library.addUser(new User("Anna", "Smith", 0));
        io.library.addUser(new User("Zoe", "Brown", 0));
        io.library.addUser(new User("John", "Williams", 0));
        io.library.addUser(new User("John", "Smith", 0));

        /*
         * Create new output file to write return requests to
         */
        PrintWriter outFile = null;
        try {
            outFile = new PrintWriter("src/main/java/dev/kimberly/library/resources/return-requests.txt");
        } catch (Exception e) {
            System.out.println("Unable to create output file for return requests.");
        }

        /*
         * Run the command line interface menu
         */
        io.runMenu(outFile);

        /*
         * Close and save output file with return requests
         */
        if (outFile != null) {
            outFile.close();
        }
    }

}
