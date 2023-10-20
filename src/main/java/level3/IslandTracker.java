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

            int numRoutes = scanner.nextInt();
            scanner.nextLine();

            for (int i = 0; i < numRoutes; i++) {
                List<String> route = Arrays.asList(scanner.nextLine().split(" "));
                if (doesSeaRouteIntersect(route, mapSize)) {
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

    private static boolean doesSeaRouteIntersect(List<String> route, int mapSize) {
        Set<String> visited = new HashSet<>();
        int x = 0;
        int y = 0;

        for (String coordinate : route) {
            int newX = Integer.parseInt(coordinate.split(",")[0]);
            int newY = Integer.parseInt(coordinate.split(",")[1]);

            if (newX < 0 || newX >= mapSize || newY < 0 || newY >= mapSize || visited.contains(newX + "," + newY)) {
                return true; // Invalid or intersection detected
            }

            if (Math.abs(newX - x) == 1 && Math.abs(newY - y) == 1 &&
                    (visited.contains((newX - 1) + "," + (newY)) ||
                            visited.contains((newX) + "," + (newY - 1)))) {
                return true; // Diagonal intersection detected
            }

            visited.add(newX + "," + newY);
            x = newX;
            y = newY;
        }

        return false; // No invalid or intersection found
    }
}
