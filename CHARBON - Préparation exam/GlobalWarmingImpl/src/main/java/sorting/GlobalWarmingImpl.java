package sorting;

import java.util.ArrayList;
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
 * Given a global water level, all positions in the matrix with a value <= the water level are flooded and therefore unsafe.
 * So, assuming the water level is 3, all safe points are highlighted between parenthesis:
 *
 *   1 , 3 , 3 , 1 , 3
 *  (4), 2 , 2 ,(4),(5)
 *  (4),(4), 1 ,(4), 2
 *   1 ,(4), 2 , 3 ,(6)
 *   1 , 1 , 1 ,(6), 3}
 *
 * The method you need to implement is nbSafePoints
 * - calculating the number of safe points for a given water level
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
     * @param waterLevel
     * @return the number of entries in altitude matrix that would be above
     *         the specified waterLevel.
     *         Warning: this is not the waterLevel given in the constructor/
     */
    public abstract int nbSafePoints(int waterLevel);

}


public class GlobalWarmingImpl extends GlobalWarming {

    int [] altitude_one;

    public GlobalWarmingImpl(int[][] altitude) {
        super(altitude);

        altitude_one = new int[altitude.length * altitude[0].length];
        for (int i = 0; i < altitude.length; i++) {
            for (int j = 0; j < altitude[0].length; j++) {
                altitude_one[i * altitude[0].length + j] = altitude[i][j];
            }
        }
        Arrays.sort(altitude_one);
        // expected pre-processing time in the constructror : O(n^2 log(n^2))
    }

    // Le binary search t edonne l'index de ton premier élément > waterlevel
    public int BinarySearch(int[] array, int low, int high, int waterLevel) {

        while(low <= high){
            int mid = (low + high) / 2;
            if(array[mid] == waterLevel) return mid;
            else if(array[mid] < waterLevel){
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return low;

        /*
        if(high <= low) return low;
        int mid = (low + high) / 2;
        if(array[mid] == waterLevel) return mid;
        if(array[mid] < waterLevel) return BinarySearch(array, mid + 1, high, waterLevel);
        return BinarySearch(array, low, mid, waterLevel);
         */
    }

    /**
     * Returns the number of safe points given a water level
     *
     * @param waterLevel the level of water
     */
    public int nbSafePoints(int waterLevel) {

        int index = BinarySearch(altitude_one, 0, altitude_one.length-1, waterLevel);
        while(index < altitude_one.length && altitude_one[index] == waterLevel){
            index++;
        }
        // expected time complexity O(log(n^2))
        return altitude_one.length - index;
    }



}
