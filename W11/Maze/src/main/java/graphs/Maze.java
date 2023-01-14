package graphs;


import java.util.Collections;
import java.util.LinkedList;

/**
 * We are interested in solving a maze represented
 * by a matrix of integers 0-1 of size nxm.
 * This matrix is a two-dimensional array.
 * An entry equal to '1' means that there
 * is a wall and therefore this position is not accessible,
 * while '0' means that the position is free.
 * We ask you to write a Java code to discover
 * the shortest path between two coordinates
 * on this matrix from (x1, y1) to (x2, y2).
 * The moves can only be vertical (up/down) or horizontal (left/right)
 * (not diagonal), one step at a time.
 * The result of the path is an Iterable of
 * coordinates from the origin to the destination.
 * These coordinates are represented by integers
 * between 0 and n * m-1, where an integer 'a'
 * represents the position x =a/m and y=a%m.
 * If the start or end position is a wall
 * or if there is no path, an empty Iterable must be returned.
 * The same applies if there is no path
 * between the origin and the destination.
 */

// Shortest path + No cost associated to the edges si --> BFS

public class Maze {

    public static class Point{
        int x;
        int y;
        public Point(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    public static Iterable<Integer> shortestPath(int[][] maze, int x1, int y1, int x2, int y2) {

        if(maze[x1][y1] == 1 || maze[x2][y2] == 1) return new LinkedList<>();

        int lenX = maze.length;
        int lenY = maze[0].length;

        boolean [][] marked = new boolean[lenX][lenY];
        Point [][] edgeTo = new Point[lenX][lenY];
        int [][] pos = {
                {1, 0},
                {-1, 0},
                {0, 1},
                {0, -1}
        };

        Point start = new Point(x1, y1);
        Point stop = new Point(x2, y2);

        LinkedList<Point> queue = new LinkedList<>();
        queue.add(start);
        marked[start.x][start.y] = true;

        boolean found = true;
        while(!queue.isEmpty() && found){

            Point current = queue.poll();

            for (int i = 0; i < 4; i++) {
                int new_x = current.x + pos[i][0];
                int new_y = current.y + pos[i][1];

                if(new_x < 0) continue;
                if(new_x >= lenX) continue;
                if(new_y < 0) continue;
                if(new_y >= lenY) continue;
                if(marked[new_x][new_y]) continue;
                if(maze[new_x][new_y] == 1) continue;

                edgeTo[new_x][new_y] = current;
                marked[new_x][new_y] = true;
                queue.add(new Point(new_x, new_y));

                if(new_x == x2 && new_y == y2) found = false;
            }
        }

        if(!marked[x2][y2]) return new LinkedList<>();

        LinkedList<Integer> path = new LinkedList<>();
        Point iter = stop;
        while(iter.x != start.x || iter.y != start.y){
            path.add(iter.x * lenY + iter.y);
            iter = edgeTo[iter.x][iter.y];
        }
        path.add(start.x * lenY + start.y);
        Collections.reverse(path);
        return path;
    }

    public static int ind(int x, int y, int lg) {
        return x * lg + y;
    }

    public static int row(int pos, int mCols) {
        return pos / mCols;
    }

    public static int col(int pos, int mCols) {
        return pos % mCols;
    }

}
