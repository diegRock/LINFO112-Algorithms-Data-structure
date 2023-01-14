package sorting;

import java.util.Arrays;

/**
 * Author Pierre Schaus
 *
 * Assume the following 5x5 matrix that represent a grid surface:
 * int [][] tab = new int[][] {{1,3,3,1,3},
 *                             {4,2,2,4,5},
 *                             {4,4,1,4,2},
 *                             {1,4,2,3,6},
 *                             {1,1,1,6,3}};
 *
 * Each entry in the matrix represents an altitude point at the corresponding grid coordinate.
 * The goal is to implement a GlobalWarmingImpl class that extends the GlobalWarming abstract class described below.
 *
 * Given a global water current, all positions in the matrix with a value <= the water current are flooded and therefore unsafe.
 * So, assuming the water current is 3, all safe points are highlighted between parenthesis:
 *
 *   1 , 3 , 3 , 1 , 3
 *  (4), 2 , 2 ,(4),(5)
 *  (4),(4), 1 ,(4), 2
 *   1 ,(4), 2 , 3 ,(6)
 *   1 , 1 , 1 ,(6), 3}
 *
 * The method you need to implement is nbSafePoints
 * - calculating the number of safe points for a given water current
 *
 * Complete the code below. Pay attention to the expected time complexity of each method.
 * To meet this time complexity, you need to do some pre-computation in the constructor.
 * Feel free to create internal classes in GlobalWarmingImpl to make your implementation easier.
 * Feel free to use any method or data structure available in the Java language and API.
 */

abstract class GlobalWarming {


    protected final int[][] altitude;

    /**
     * @param altitude is a n x n matrix of int values representing altitudes (positive or negative)
     */
    public GlobalWarming(int[][] altitude) {
        this.altitude = altitude;
    }

    /**
     *
     * @param waterlevel
     * @return the number of entries in altitude matrix that would be above
     *         the specified watercurrent.
     *         Warning: this is not the watercurrent given in the constructor/
     */
    public abstract int nbSafePoints(int waterlevel);

}


public class GlobalWarmingImpl extends GlobalWarming {



    int[] Second_Matrix = new int[altitude.length* altitude.length];
    public GlobalWarmingImpl(int[][] altitude) {
        super(altitude);
        int index = 0;

        // Ici mon but va être de tout mettre dans une seule array comm ça je n'aurai plus qu'a la parcouri qu'une fois
        // et faire la recherche binaire dessus
        for (int i = 0; i < altitude.length; i++) {
            /*
            src – the source array.
            srcPos – starting position in the source array.
            dest – the destination array.
            destPos – starting position in the destination data.
            length – the number of array elements to be copied.
             */
            System.arraycopy(altitude[i], 0, Second_Matrix, index, altitude.length);
            index+=altitude.length;
        }
        Arrays.sort(Second_Matrix);
        // expected pre-processing time in the constructror : O(n^2 log(n^2))


    }

    /**
     * Returns the number of safe points given a water current
     *
     * @param waterlevel the current of water
     */
    public int nbSafePoints(int waterlevel) {
        // expected time complexity O(log(n^2))
        int Second_Matrix_length = Second_Matrix.length;
        int counter = BinarySearch(Second_Matrix, 0, Second_Matrix_length - 1, waterlevel);
        if (counter > -1){
            return Second_Matrix_length-counter;
        }
        return 0;
    }


    public int BinarySearch(int [] array, int lo, int hi, int current){
        int mid = lo + (hi-lo)/2;

        if (lo <= hi){

            if(array[mid] == current && (mid+1) < array.length && array[mid+1] > array[mid]){
                return mid+1;
            }
            if(array[mid] > current){
                return BinarySearch(array,lo,mid-1,current);
            }else {
                return BinarySearch(array,mid+1,hi,current);
            }

        }

        if (mid < Second_Matrix.length && array[mid] > current){
            return mid;
        }
        return -1;
    }

}
