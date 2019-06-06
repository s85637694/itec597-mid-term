import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.util.Arrays;
import java.util.HashMap;

public class Monastery {

    public int[] resolve(int[][] rooms) {
        int[] result = new int[6];
        int rows = rooms.length;
        int cols = rooms[0].length;
        int numberOfRooms = rows * cols;
        WeightedQuickUnionUF unionUF = constructUnionUF(rooms);

        result[0] = unionUF.count();
        result[1] = numberOfRoomsInMaxRegion(unionUF, numberOfRooms);

        int maxCount = 0;
        int row = 0;
        int col = 0;
        int direction = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int currentRoom = xyTo1D(i, j, cols);
                if (j + 1 < cols && Direction.EAST.hasWall(currentRoom)) {
                    int nextRoom = xyTo1D(i, j + 1, cols);
                    unionUF.union(currentRoom, nextRoom);
                    int tmp = numberOfRoomsInMaxRegion(unionUF, numberOfRooms);
                    if (tmp > maxCount) {
                        maxCount = tmp;
                        row = i + 1;
                        col = j + 1;
                        direction = 4;
                    }
                    unionUF = constructUnionUF(rooms); 
                }
            }
        }
        for (int j = 0; j < cols; j++) {
            for (int i = rows - 1; i >= 0; i--) {
                int currentRoom = xyTo1D(i, j, cols);
                if (i - 1 >= 0 && Direction.NORTH.hasWall(currentRoom)) {
                    int nextRoom = xyTo1D(i-1, j, cols);
                    unionUF.union(currentRoom, nextRoom);
                    int tmp = numberOfRoomsInMaxRegion(unionUF, numberOfRooms);
                    if (tmp > maxCount) {
                        maxCount = tmp;
                        row = i + 1;
                        col = j + 1;
                        direction = 2;
                    }
                    unionUF = constructUnionUF(rooms);
                }
            }
        }
        result[2] = maxCount;
        result[3] = row;
        result[4] = col;
        result[5]= direction;
        return result;
    }

    private WeightedQuickUnionUF constructUnionUF(int rooms[][]) {
        int rows = rooms.length;
        int cols = rooms[0].length;
        int numberOfRooms = rows * cols;
        WeightedQuickUnionUF unionUF = new WeightedQuickUnionUF(numberOfRooms);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int room = rooms[i][j];
                int currentRoomOrder = xyTo1D(i, j, cols);
                if (!Direction.WEST.hasWall(room) && j - 1 >= 0) {
                    int order = xyTo1D(i, j - 1, cols);
                    unionUF.union(currentRoomOrder, order);
                }
                if (!Direction.NORTH.hasWall(room) && i - 1 >= 0) {
                    int order = xyTo1D(i-1, j, cols);
                    unionUF.union(currentRoomOrder, order);
                }
                if (!Direction.EAST.hasWall(room) && j + 1 < cols) {
                    int order = xyTo1D(i, j + 1, cols);
                    unionUF.union(currentRoomOrder, order);
                }
                if (!Direction.SOUTH.hasWall(room) && i + 1 < rows) {
                    int order = xyTo1D(i + 1, j, cols);
                    unionUF.union(currentRoomOrder, order);
                }
            }
        }
        return unionUF;
    }

    private int numberOfRoomsInMaxRegion(WeightedQuickUnionUF unionUF, int numberOfRooms) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < numberOfRooms; i++) {
            Integer p = unionUF.find(i);
            Integer count = map.get(p);
            map.put(p, count == null ? 1 : count + 1);
        }
        int maxNumberOfRooms = 0;
        for (Integer count : map.values()) {
            if (count > maxNumberOfRooms) {
                maxNumberOfRooms = count;
            }
        }
        return maxNumberOfRooms;
    }


    private int xyTo1D(int x, int y, int cols) {
        return x * cols + y;
    }

    private enum Direction {
        WEST, NORTH, EAST, SOUTH;

        public boolean hasWall(int room) {
            Integer[] west = {1, 3, 5, 7, 9, 11, 13, 15};
            Integer[] north = {2, 3, 6, 7, 10, 11, 14, 15};
            Integer[] east = {4, 5, 6, 7, 12, 13, 14, 15};
            Integer[] south = {8, 9, 10, 11, 12, 13, 14, 15};
            switch (this) {
                case WEST: return room % 2 == 1;
                case NORTH: return Arrays.asList(north).contains(room);
                case EAST: return Arrays.asList(east).contains(room);
                case SOUTH: return Arrays.asList(south).contains(room);
                default: return true;
            }
        }
    }

    public static void main(String[] args) {
        Monastery monastery = new Monastery();
        int[][] rooms = {{3, 2, 6, 3, 6},
                {1, 8, 4, 1, 4},
                {13, 7, 13, 9, 4},
                {3, 0, 2, 6, 5},
                {9, 8, 8, 12, 13}};
        System.out.println(Arrays.toString(monastery.resolve(rooms)));
    }

}