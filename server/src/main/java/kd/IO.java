package kd;

import java.io.PrintWriter;
import java.util.Scanner;

import kd.models.*;

/**
 * <h2>Library management system</h2>
 * This project is an interactive library management system with a command line
 * interface. The program enables the importing of books and users from a file,
 * and enables the system user to issue and return books.
 *
 * The IO class manages the input and output of the program, i.e. the command
 * line interface menus, the user's response, and the printed results.
 * 
 * @author Kimberly Dijkmans
 */
public class IO {

    private SortedArrayList<Book> books = new SortedArrayList<>();
    private SortedArrayList<User> users = new SortedArrayList<>();

    boolean addBook(Book book) {
        for (Book u : books) {
            if (u.equals(book))
                return false; // book already exists
        }
        books.insert(book);
        return true;
    }

    boolean addUser(User user) {
        for (User u : users) {
            if (u.equals(user))
                return false; // user already exists
        }
        users.insert(user);
        return true;
    }

    public void runMenu(PrintWriter outFile) {
        Scanner scanner = new Scanner(System.in);
        boolean quit = false;
        while (!quit) {

            printMenu();

            char choice = scanner.next().charAt(0);
            scanner.nextLine(); // Retrieving enter to make sure the next reading is from the next line

            switch (choice) {
                case 'f': // Finish running the program
                    System.out.printf("%n%s%n", "Closing the program ...");
                    quit = true;
                    scanner.close();
                    break;
                case 'b': // List information about all books in library
                    listItems(books);
                    break;
                case 'u': // List information about al users
                    listItems(users);
                    break;
                case 'i': // Issue a book
                    issueBook(scanner, outFile);
                    break;
                case 'r': // Return a book
                    returnBook(scanner);
                    break;
                default:
                    System.out.println("Invalid entry, try again");
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
        if (e.size() > 0) {
            for (E elem : e)
                System.out.println(elem);
        } else {
            System.out.println("No items found");
        }
    }

    void issueBook(Scanner scanner, PrintWriter outFile) {
        System.out.printf("%n%s%n%s%n", "ISSUE A BOOK", "--------------");

        User selectedUser;
        Book selectedBook;

        try {
            selectedUser = validateUser(scanner);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        try {
            selectedBook = validateBook(scanner);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        if (selectedUser.getNumOfBooks() < 3) { // Check if user has less than 3 books on loan (can borrow 3 max)

            if (selectedBook.isOnLoan()) { // If selected book is already out on loan, send a return request
                sendReturnRequest(outFile, selectedUser, selectedBook);
                System.out.println(
                        "The book could not be issued as it is currently held by another user. A return request is sent.");

            } else { // If book is available, issue book to user
                selectedBook.setBorrower(selectedUser);
                selectedBook.setOnLoan(true);
                selectedUser.setNumOfBooks(selectedUser.getNumOfBooks() + 1);
                System.out.println(
                        "The book has been issued.");
            }

        } else { // User already has 3 or more books on loan
            System.out.println(selectedUser.getFirstName() + " " + selectedUser.getSurname() + " already has " +
                    selectedUser.getNumOfBooks()
                    + " books on loan. The maximum allowed is 3. Please return a book first.");
        }
    }

    private void returnBook(Scanner scanner) {
        System.out.printf("%n%s%n%s%n", "RETURN A BOOK", "--------------");

        User selectedUser;
        Book selectedBook;

        try {
            selectedUser = validateUser(scanner);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        try {
            selectedBook = validateBook(scanner);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        if (selectedUser.equals(selectedBook.getBorrower())) { // If book is borrowed by selected user, return book
            selectedBook.setBorrower(null);
            selectedBook.setOnLoan(false);
            selectedUser.setNumOfBooks(selectedUser.getNumOfBooks() - 1);
            System.out.println("The book has been returned.");

        } else { // If book is not borrowed by selected user, print message
            System.out.println(selectedUser.getFirstName() + " " +
                    selectedUser.getSurname() + " is not in possession of the book " +
                    selectedBook.getTitle() + " by " + selectedBook.getAuthorFirstName() + " " +
                    selectedBook.getAuthorSurname() + ".");
        }
    }

    private User validateUser(Scanner scanner) throws Exception {
        System.out.println("Enter the user's first name:");
        String userFirstName = scanner.nextLine().trim();
        System.out.println("Enter the user's surname:");
        String userSurname = scanner.nextLine().trim();

        User selectedUser = null;
        boolean validUser = false;

        int timesToRetry = 1;
        while (!validUser) {
            for (User u : users) {
                if (u.getFirstName().equalsIgnoreCase(userFirstName)
                        && u.getSurname().equalsIgnoreCase(userSurname)) {
                    selectedUser = u;
                    validUser = true;
                }
            }

            if (timesToRetry > 0) {
                if (!validUser) {
                    timesToRetry--;
                    System.out.println("Invalid user, please enter the user's first name again:");
                    userFirstName = scanner.nextLine().trim();
                    System.out.println("Enter the user's surname:");
                    userSurname = scanner.nextLine().trim();
                }
            } else {
                throw new Exception("User was not found");
            }

        }
        return selectedUser;
    }

    private Book validateBook(Scanner scanner) throws Exception {
        System.out.println("Enter the book's title:");
        String bookTitle = scanner.nextLine().trim();
        System.out.println("Enter the surname of the book's author:");
        String bookAuthorSurname = scanner.nextLine().trim();

        Book selectedBook = null;
        boolean validBook = false;

        int timesToRetry = 1;
        while (!validBook) {
            for (Book b : books) {
                if (b.getTitle().equalsIgnoreCase(bookTitle)
                        && b.getAuthorSurname().equalsIgnoreCase(bookAuthorSurname)) {
                    selectedBook = b;
                    validBook = true;
                }
            }

            if (timesToRetry > 0) {
                if (!validBook) {
                    timesToRetry--;
                    System.out.println("Invalid book, please enter the book's title again:");
                    bookTitle = scanner.nextLine().trim();
                    System.out.println("Enter the surname of the book's author:");
                    bookAuthorSurname = scanner.nextLine().trim();
                }
            } else {
                throw new Exception("Book was not found");
            }
        }
        return selectedBook;
    }

    /*
     * This method sends out a return request by writing the request to a file
     */
    private void sendReturnRequest(PrintWriter file, User user, Book book) {
        if (file != null) {
            file.println("Dear " + user.getFirstName() + " " + user.getSurname() + ", ");
            file.println("The book " + book.getTitle() + " by " +
                    book.getAuthorFirstName() + " " + book.getAuthorSurname() +
                    " is requested and would have to be returned ASAP. Thank you.");
            file.println("Yours sincerely, library management.");
            file.println(" ");
        } else {
            System.out.println("Unable to write return request, file not found");
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
         * Import books and users from file
         */
        new ImportFile().importBooksAndUsersFromFile("resources/input-books-users.txt", io);

        /*
         * Create new output file to write return requests to
         */
        PrintWriter outFile = null;
        try {
            outFile = new PrintWriter("src/main/java/kd/resources/return-requests.txt");
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
