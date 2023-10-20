package level4;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class IslandTracker {
    static class Point {
        int x, y;
        Point prev;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) {
        String inputFile = "input.txt"; // Replace with your input file path
        String outputFile = "output.txt"; // Replace with your output file path

        try {
            Scanner scanner = new Scanner(new File(inputFile));
            FileWriter writer = new FileWriter(new File(outputFile));

            int mapSize = scanner.nextInt();
            scanner.nextLine();

            char[][] map = new char[mapSize][mapSize];
            for (int i = 0; i < mapSize; i++) {
                map[i] = scanner.nextLine().toCharArray();
            }

            int numCoordinates = scanner.nextInt();
            scanner.nextLine();

            for (int i = 0; i < numCoordinates; i++) {
                String coordinateLine = scanner.nextLine();
                String[] coordinateParts = coordinateLine.split(",");
                int x = Integer.parseInt(coordinateParts[0]);
                int y = Integer.parseInt(coordinateParts[1]);
                List<String> validRoute = findValidSeaRoute(map, x, y);
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

    private static List<String> findValidSeaRoute(char[][] map, int startX, int startY) {
        List<String> validRoute = new ArrayList<>();
        int mapSize = map.length;
        int[] dx = { 0, 1, 1, 1, 0, -1, -1, -1 };
        int[] dy = { -1, -1, 0, 1, 1, 1, 0, -1 };
        Set<String> visited = new HashSet<>();
        Point startPoint = new Point(startX, startY);

        for (int i = 0; i < 8; i++) {
            validRoute.clear();
            int currentX = startX;
            int currentY = startY;
            boolean isCircular = false;

            while (true) {
                int nextX = currentX + dx[i];
                int nextY = currentY + dy[i];

                if (!isValidCoordinate(nextX, nextY, mapSize)) {
                    break;
                }

                if (visited.contains(nextX + "," + nextY)) {
                    isCircular = false;
                    break;
                }

                char currentTile = map[currentY][currentX];
                char nextTile = map[nextY][nextX];

                if (currentTile == 'L' || nextTile == 'L') {
                    break;
                }

                validRoute.add(currentX + "," + currentY);
                visited.add(currentX + "," + currentY);
                currentX = nextX;
                currentY = nextY;

                if (currentX == startX && currentY == startY && validRoute.size() > 1) {
                    isCircular = true;
                    break;
                }
            }

            if (isCircular) {
                validRoute.add(startX + "," + startY); // Add the starting point again to complete the circular route
                return validRoute;
            }
        }

        return null;
    }

    private static boolean isValidCoordinate(int x, int y, int mapSize) {
        return x >= 0 && x < mapSize && y >= 0 && y < mapSize;
    }
}
