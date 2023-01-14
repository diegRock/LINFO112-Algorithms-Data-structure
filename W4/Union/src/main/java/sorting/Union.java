package sorting;

//Utiliser equals et pas == en java !
//Voir vidéo sur  RedBlackTree --> C'est le meilleur parce que tout est en O(log(n)).

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Author Pierre Schaus
 *
 * Given an array of (closed) intervals, you are asked to implement the union operation.
 * This operation will return the minimal array of sorted intervals covering exactly the union
 * of the points covered by the input intervals.
 * For example, the union of intervals [7,9],[5,8],[2,4] is [2,4],[5,9].
 * The Interval class allowing to store the intervals is provided
 * to you.
 *
 */
public class Union {

    /**
     * A class representing an interval with two integers. Hence the interval is
     * [min, max].
     */
    public static class Interval implements Comparable<Union.Interval> {

        public final int min;
        public final int max;

        public Interval(int min, int max) {
            assert(min <= max);
            this.min = min;
            this.max = max;
        }

        @Override
        public boolean equals(Object obj) {
            return ((Union.Interval) obj).min == min && ((Union.Interval) obj).max == max;
        }

        @Override
        public String toString() {
            return "["+min+","+max+"]";
        }

        @Override
        public int compareTo(Union.Interval o) {
            if (min < o.min) return -1;
            else if (min == o.min) return max - o.max;
            else return 1;
        }
    }

    /**
     * Returns the union of the intervals given in parameters.
     * This is the minimal array of (sorted) intervals covering
     * exactly the same points than the intervals in parameter.
     * 
     * @param intervals the intervals to unite.
     */
    public static Interval[] union(Interval[] intervals) {
        if(intervals.length == 0) return intervals;
        Arrays.sort(intervals);
        ArrayList<Interval> response = new ArrayList<>();
        int min = intervals[0].min;
        int max = intervals[0].max;
        for (int i = 0; i < intervals.length - 1; i++) {
            if(max < intervals[i+1].min){
                Interval new_interval = new Interval(min, max);
                response.add(new_interval);
                min = intervals[i+1].min;
                max = intervals[i+1].max;
            } else if (intervals[i + 1].max > max) {
                max = intervals[i+1].max;

            }
        }
        Interval new_interval = new Interval(min, max);
        response.add(new_interval);
        return response.toArray(new Interval[]{});
    }

}

// 2.2.2
// Tu fais 3 compteurs qui comptr le nombre de 0, 1 et 2.
// Ensuite t'ajoutes les éléments

//2.2.3
// compter le mode d'une array
/*
Trier les éléments
Trouver l'élément '

On va utiliser le 3-Way Quicksort

private class Quick3way {
    private static void sort(Comparable[] a, int lo, int hi){
        if (hi <= lo) return;
        int lt = lo, i = lo+1, gt= hi;
        Comparable v = a[lo];
        while(i <= gt) {
            int cmp = a[i].compareTo(v);
            if (cmp < 0) exch(a, lt++, i++);
            else if (cmp > 0) exch(a, i, gt--);
            else {
                i++;
            }
        }
        sort(a, lo, lt-1);
        sort(a, gt+1, hi);
    }
}
 */

// 2.2.4
// nlog(n)
//

// jsp quelle suestion
// Donnez un algo pour calculer l'union de deux ensembles A et B.
// Tout mettre dans n grand tableau et trier O(m +n)log(m+n) et ensuite retirer les doublons
// ou
// trier chacun des ensembles séparément et ensuite collecter les éléments en retirant les doublons. O(m log(m) + n log(n)).



