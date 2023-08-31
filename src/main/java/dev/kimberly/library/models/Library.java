package dev.kimberly.library.models;

import dev.kimberly.library.SortedArrayList;

import java.io.PrintWriter;
import java.util.Scanner;

public class Library {

    private final SortedArrayList<Book> books = new SortedArrayList<>();
    private final SortedArrayList<User> users = new SortedArrayList<>();

    public SortedArrayList<Book> getBooks() {
        return books;
    }

    public SortedArrayList<User> getUsers() {
        return users;
    }

    public void addBook(Book book) {
        for (Book u : books) {
            if (u.equals(book))
                return; // book already exists
        }
        books.insert(book);
    }

    public void addUser(User user) {
        for (User u : users) {
            if (u.equals(user))
                return; // user already exists
        }
        users.insert(user);
    }


    public void issueBook(Scanner scanner, PrintWriter outFile) {
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

    public void returnBook(Scanner scanner) {
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
            for (User u : this.getUsers()) {
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
            for (Book b : this.getBooks()) {
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

}
