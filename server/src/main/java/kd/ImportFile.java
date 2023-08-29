package kd;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import kd.models.*;

/**
 * The ImportFile class is responsible for importing books and users from a
 * file.
 * 
 * @author Kimberly Dijkmans
 */
public class ImportFile {

    /**
     * This method imports books and users from a file
     * 
     * @param fileURI The URI of the file containing books and users to import
     * @param io      The io (input/output) instance that stores the lists of books
     *                and users
     */
    public void importBooksAndUsersFromFile(String fileURI, IO io) {
        if (fileURI != null) {
            try {
                // Create a file object from the given URI
                File file = new File(ImportFile.class.getResource(fileURI).getFile());

                // Read file, parse lines, create Book and User objects and add it to the
                // records
                parser(readFile(file), io);

            } catch (Exception e) {
                System.out.println("Unable to import from file");
            }
        } else {
            System.out.println("No file to import records from");
        }

    }

    /*
     * This method reads the file and stores each line into an array list
     */
    private ArrayList<String> readFile(File file) {
        try {
            ArrayList<String> records = new ArrayList<>();
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                records.add(scanner.nextLine());
            }
            scanner.close();
            return records;
        } catch (Exception e) {
            System.out.println("Unable to read file");
            return null;
        }
    }

    /*
     * This method takes each line from the array list, parses it, creates Book and
     * User objects from the information and adds it to the records
     */
    private void parser(ArrayList<String> records, IO io) {

        int numOfBooks = Integer.parseInt(records.get(0));
        int numOfUsers = Integer.parseInt(records.get((numOfBooks * 2) + 1));

        /*
         * Loop through the books and add to books collection
         */
        for (int i = 1; i < (numOfBooks * 2); i += 2) {
            String title = records.get(i);
            String[] authorName = records.get(i + 1).split(" ");
            String authorSurname = authorName[authorName.length - 1].trim();
            String authorFirstName = records.get(i + 1).replace(authorSurname, "").trim();

            io.addBook(new Book(title, authorFirstName, authorSurname, false, null));
        }

        int currentLine = (numOfBooks * 2) + 2; // set current line

        /*
         * Loop through the users and add to users list
         */
        for (int i = 0; i < numOfUsers; i++) {
            String[] name = records.get(currentLine).split(" ");
            String firstName = name[0];
            String surname = name[1];

            currentLine++;

            io.addUser(new User(firstName, surname, 0));
        }
    }

}
