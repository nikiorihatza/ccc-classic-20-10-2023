package level3;

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
                map[i] = scanner.nextLine().toCharArray();
            }

            int numTasks = scanner.nextInt();
            System.out.println(numTasks);
            scanner.nextLine();

            System.out.println(scanner.hasNext());

            for (int i = 0; i < numTasks; i++) {
                String taskType = scanner.nextLine();

                List<String> route = Arrays.asList(scanner.nextLine().split(" "));
                if (doesSeaRouteIntersect(route)) {
                    writer.write("INVALID\n");
                } else {
                    writer.write("VALID\n");
                }
            }

            scanner.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean areCoordinatesOnSameIsland(char[][] map, int[] coordinates1, int[] coordinates2) {
        int x1 = coordinates1[0];
        int y1 = coordinates1[1];
        int x2 = coordinates2[0];
        int y2 = coordinates2[1];

        if (x1 < 0 || x1 >= map.length || x2 < 0 || x2 >= map.length || y1 < 0 || y1 >= map.length || y2 < 0 || y2 >= map.length) {
            return false; // Coordinates out of bounds
        }

        char tile1 = map[y1][x1];
        char tile2 = map[y2][x2];

        return tile1 == 'L' && tile2 == 'L';
    }

    private static boolean doesSeaRouteIntersect(List<String> route) {
        Set<String> visited = new HashSet<>();
        int x = 0;
        int y = 0;

        for (String coordinate : route) {
            int newX = Integer.parseInt(coordinate.split(",")[0]);
            int newY = Integer.parseInt(coordinate.split(",")[1]);

            if (x == newX && y == newY || visited.contains(newX + "," + newY)) {
                return true; // Intersection detected
            }

            visited.add(newX + "," + newY);
            x = newX;
            y = newY;
        }

        return false; // No intersection found
    }
}
