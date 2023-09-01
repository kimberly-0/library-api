package dev.kimberly.library;

import java.io.PrintWriter;

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
         * Create new output file to write return requests to
         */
        PrintWriter outFile = null;
        try {
            outFile = new PrintWriter("src/main/java/dev/kimberly/library/resources/return-requests.txt");
        } catch (Exception e) {
            System.out.println("Unable to create output file for return requests.");
        }

        /*
         * Close and save output file with return requests
         */
        if (outFile != null) {
            outFile.close();
        }
    }

}
