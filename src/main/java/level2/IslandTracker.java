package level2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class IslandTracker {
    public static void main(String[] args) {
        String inputFile = "D:\\OutsourcedIdeaProject\\ccc-classic-20-10-2023\\src\\resources\\level3_example.in";
        String outputFile = "D:\\OutsourcedIdeaProject\\ccc-classic-20-10-2023\\src\\resources\\level3\\test3.txt";

        try {
            Scanner scanner = new Scanner(new File(inputFile));
            FileWriter writer = new FileWriter(new File(outputFile));

            int mapSize = scanner.nextInt();
            scanner.nextLine();

            char[][] map = new char[mapSize][mapSize];
            for (int i = 0; i < mapSize; i++) {
                String line = scanner.nextLine();
                map[i] = line.toCharArray();
            }

            int numCoordinates = scanner.nextInt();
            scanner.nextLine();

            for (int i = 0; i < numCoordinates; i++) {
                String[] coordinates = scanner.nextLine().split(" ");
                String[] coordinate1 = coordinates[0].split(",");
                String[] coordinate2 = coordinates[1].split(",");

                int x1 = Integer.parseInt(coordinate1[0]);
                int y1 = Integer.parseInt(coordinate1[1]);
                int x2 = Integer.parseInt(coordinate2[0]);
                int y2 = Integer.parseInt(coordinate2[1]);

                if (areCoordinatesOnSameIsland(map, x1, y1, x2, y2)) {
                    writer.write("SAME\n");
                } else {
                    writer.write("DIFFERENT\n");
                }
            }

            scanner.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean areCoordinatesOnSameIsland(char[][] map, int x1, int y1, int x2, int y2) {
        int mapSize = map.length;
        if (x1 < 0 || x1 >= mapSize || x2 < 0 || x2 >= mapSize || y1 < 0 || y1 >= mapSize || y2 < 0 || y2 >= mapSize) {
            return false; // Coordinates out of bounds
        }

        if (map[y1][x1] != 'L' || map[y2][x2] != 'L') {
            return false; // Both coordinates should be on land
        }

        // Use Dijkstra's algorithm to check if they are on the same island
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{x1, y1});
        boolean[][] visited = new boolean[mapSize][mapSize];
        visited[y1][x1] = true;

        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int cx = current[0];
            int cy = current[1];

            if (cx == x2 && cy == y2) {
                return true; // Found a path to the second coordinate
            }

            for (int[] dir : directions) {
                int nx = cx + dir[0];
                int ny = cy + dir[1];

                if (nx >= 0 && nx < mapSize && ny >= 0 && ny < mapSize
                        && map[ny][nx] == 'L' && !visited[ny][nx]) {
                    queue.add(new int[]{nx, ny});
                    visited[ny][nx] = true;
                }
            }
        }

        return false; // No path from the first coordinate to the second coordinate
    }
}
