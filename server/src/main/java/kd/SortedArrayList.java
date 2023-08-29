package kd;

import java.util.ArrayList;

/**
 * The SortedArrayList class is responsible for creating a sorted list of Book
 * objects and sorted list of User objects.
 * 
 * The generic class SortedArrayList restricts the type parameter to
 * ArrayList<E> classes and the elements in the SortedArrayList (in this case
 * Users or Books) must implement the Comparable<E> interface.
 * 
 * @author Kimberly Dijkmans
 */
public class SortedArrayList<E extends Comparable<E>> extends ArrayList<E> {

    /**
     * This method inserts an element into a sorted array list at a certain position
     * to keep the list in the correct order
     * 
     * @param newElement The new element to insert into the list
     */
    public void insert(E newElement) {

        /*
         * If array is empty, insert element
         */
        if (this.size() == 0) {
            this.add(newElement);
            return;
        }

        /*
         * Loop through existing elements in array list and if new element is meant to
         * come before the element in the array, insert it at that index (subsequent
         * elements will be shifted one place)
         */
        for (int i = 0; i < this.size(); i++) {

            /*
             * compareTo returns a negative value if new element is meant to come before the
             * element it is being compared to, or returns 0 if they are equal
             */
            if (newElement.compareTo(this.get(i)) <= 0) {
                this.add(i, newElement);
                return;
            }
        }

        /*
         * If loop has gone through all existing elements in array, and new element is
         * not meant to be positioned before any of them, insert it at the end of the
         * array
         */
        this.add(newElement);

    }
}
