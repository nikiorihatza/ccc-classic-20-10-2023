import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class IslandTracker {
    public static void main(String[] args) {
        String inputFile = "input.txt";
        String outputFile = "output.txt";

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
                String[] coordinates = routeLine.split(" ");
                List<String> validRoute = findValidSeaRoute(map, coordinates, mapSize);
                if (validRoute != null) {
                    writer.write(String.join(" ", validRoute) + "\n");
                } else {
                    writer.write("NO ROUTE\n");
                }
            }

            scanner.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String> findValidSeaRoute(char[][] map, String[] coordinates, int mapSize) {
        String[] startCoordinates = coordinates[0].split(",");
        String[] endCoordinates = coordinates[1].split(",");

        int startX = Integer.parseInt(startCoordinates[0]);
        int startY = Integer.parseInt(startCoordinates[1]);
        int endX = Integer.parseInt(endCoordinates[0]);
        int endY = Integer.parseInt(endCoordinates[1]);

        // Implement Dijkstra's algorithm to find a valid sea route
        List<String> validRoute = dijkstra(map, startX, startY, endX, endY, mapSize);

        return validRoute;
    }

    private static List<String> dijkstra(char[][] map, int startX, int startY, int endX, int endY, int mapSize) {
        // Implement Dijkstra's algorithm for finding the sea route
        // You will need to check for land tiles, gaps, revisiting the same tile,
        // avoiding diagonal step crossings, and ensuring the route is not longer
        // than twice the width of the map.

        // Placeholder return for now
        return null;
    }
}
