package graphs;


import java.util.Arrays;
import java.util.Stack;

/**
 * In this exercise, we revisit the GlobalWarming class from the sorting package.
 * You are still given a matrix of altitude in parameter of the constructor, with a water level.
 * All the entries whose altitude is under, or equal to, the water level are submerged while the other constitute small islands.
 *
 * For example let us assume that the water level is 3 and the altitude matrix is the following
 *
 *      | 1 | 3 | 3 | 1 | 3 |
 *      | 4 | 2 | 2 | 4 | 5 |
 *      | 4 | 4 | 1 | 4 | 2 |
 *      | 1 | 4 | 2 | 3 | 6 |
 *      | 1 | 1 | 1 | 6 | 3 |
 *
 * If we replace the submerged entries by _, it gives the following matrix
 *
 *      | _ | _ | _ | _ | _ |
 *      | 4 | _ | _ | 4 | 5 |
 *      | 4 | 4 | _ | 4 | _ |
 *      | _ | 4 | _ | _ | 6 |
 *      | _ | _ | _ | 6 | _ |
 *
 * The goal is to implmets two methods that can answer the following questions:
 *      1) Are two entries on the same island? //
 *      2) How many island is there // DFS
 *
 * Two entries above the water level are connected if they are next to each other on
 * the same row or the same column. They are **not** connected **in diagonal**.
 * Beware that the methods must run in O(1) time complexity, at the cost of a pre-processing in the constructor.
 * To help you, you'll find a `Point` class in the utils package which identified an entry of the grid.
 * Carefully read the expected time complexity of the different methods.
 */
public class GlobalWarming {


    static int[][]altitude;
    static int waterlevel;
    static int [][] id;
    static int counter;
    static int lenX;
    static int lenY;
    static boolean [][] marked;

    static int [][] pos = {
            {1, 0},{0, 1},{-1, 0},{0, -1}
    };

    /**
     * Constructor. The run time of this method is expected to be in
     * O(n x log(n)) with n the number of entry in the altitude matrix.
     *
     * @param altitude   the matrix of altitude
     * @param waterLevel the water level under which the entries are submerged
     */
    public GlobalWarming(int[][] altitude, int waterLevel) {
        this.altitude = altitude;
        this.waterlevel = waterLevel;

        lenX = altitude.length;
        lenY = altitude[0].length;

        marked = new boolean[lenX][lenY];
        id = new int[lenX][lenY];
        for(int[] i : id) Arrays.fill(i, 0);

        counter = 1;

        for (int i = 0; i < lenX; i++) {
            for (int j = 0; j < lenY; j++) {
                if(!marked[i][j] && altitude[i][j] > waterlevel){
                    marked[i][j] = true;
                    id[i][j] = counter;
                    dfs(new Point(i, j));
                    counter++;
                }
            }
        }

    }

    public static void dfs(Point entry){

        Stack<Point> stack = new Stack<>();
        stack.push(entry);

        while(!stack.isEmpty()){
            Point current = stack.pop();
            for (int i = 0; i < 4; i++) {
                int new_x = current.x + pos[i][0];
                int new_y = current.y + pos[i][1];

                if(new_x >= lenX) continue;
                if(new_x < 0) continue;
                if(new_y >= lenY) continue;
                if(new_y < 0) continue;
                if(marked[new_x][new_y]) continue;
                if(altitude[new_x][new_y] <= waterlevel) continue;

                marked[new_x][new_y] = true;
                id[new_x][new_y] = counter;
                stack.push(new Point(new_x, new_y));
            }
        }

    }


    /**
     * Returns the number of island
     * <p>
     * Expected time complexity O(1)
     */
    public int nbIslands() {
        return counter - 1;
    }

    /**
     * Return true if p1 is on the same island than p2, false otherwise
     * <p>
     * Expected time complexity: O(1)
     *
     * @param p1 the first point to compare
     * @param p2 the second point to compare
     */
    public boolean onSameIsland(Point p1, Point p2) {
        return id[p1.x][p1.y] == id[p2.x][p2.y];
    }


    /**
     * This class represent a point in a 2-dimension discrete plane. This is used, for instance, to
     * identified cells of a grid
     */
    static class Point {

        private final int x;
        private final int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof Point) {
                Point p = (Point) o;
                return p.x == this.x && p.y == this.y;
            }
            return false;
        }
    }


    public static void main(String[] args) {
        int[][] perso = new int[][]{
                {2, 0, 2, 0},
                {0, 2, 0, 2},
                {2, 4, 2, 5},
                {0, 2, 0, 2}
        };

        int WLperso = 1;

        GlobalWarming object = new GlobalWarming(perso, WLperso);
        //System.out.println(object.nbIslands());
        //System.out.println(object.onSameIsland(new Point(2, 1), new Point(1, 1)));


        int[][] altitude = new int[][]{
                {1, 3, 3, 1, 3},
                {4, 2, 2, 4, 5},
                {4, 4, 1, 4, 2},
                {1, 4, 2, 3, 6},
                {1, 1, 1, 6, 3}
        };

        int waterLevel = 3;

        GlobalWarming globalWarming = new GlobalWarming(altitude, waterLevel);
        for (int[] i : globalWarming.id) System.out.println(Arrays.toString(i));
        System.out.println("Expected 4:");
        System.out.println(globalWarming.nbIslands());
        System.out.println();

        Point p1 = new GlobalWarming.Point(1, 0);
        Point p2 = new GlobalWarming.Point(3, 1);
        Point p3 = new GlobalWarming.Point(1, 3);
        Point p4 = new GlobalWarming.Point(0, 0);

        System.out.println("Expected true:");
        System.out.println(globalWarming.onSameIsland(p1, p2));
        System.out.println();

        System.out.println("Expected false:");
        System.out.println(globalWarming.onSameIsland(p1, p3));
        System.out.println();

        System.out.println("Expected false:");
        System.out.println(globalWarming.onSameIsland(p1, p4));
        System.out.println();

        //int[][] res = object.components;
        //for (int[] i : res) System.out.println(Arrays.toString(i));
    }
}
