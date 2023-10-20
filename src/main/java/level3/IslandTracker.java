package level3;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class IslandTracker {
    public static void main(String[] args) {
        String inputFile = "D:\\OutsourcedIdeaProject\\ccc-classic-20-10-2023\\src\\resources\\level3_5.in";
        String outputFile = "D:\\OutsourcedIdeaProject\\ccc-classic-20-10-2023\\src\\resources\\level3\\3_5.txt";

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
                String routeLine = scanner.nextLine();
                if (doesSeaRouteIntersect(routeLine)) {
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

    private static boolean doesSeaRouteIntersect(String routeLine) {
        String[] coordinates = routeLine.split(" ");
        Set<String> visited = new HashSet<>();
        int x = 0;
        int y = 0;

        for (String coordinate : coordinates) {
            int newX = Integer.parseInt(coordinate.split(",")[0]);
            int newY = Integer.parseInt(coordinate.split(",")[1]);

            if (visited.contains(newX + "," + newY) || (x != newX && y != newY && visited.contains(x + "," + newY) && visited.contains(newX + "," + y))) {
                return true; // Intersection detected
            }

            visited.add(newX + "," + newY);
            x = newX;
            y = newY;
        }

        return false; // No intersection found
    }
}
