package level5;

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
        String inputFile = "D:\\OutsourcedIdeaProject\\ccc-classic-20-10-2023\\src\\resources\\level5_example.in"; // Replace with your input file path
        String outputFile = "D:\\OutsourcedIdeaProject\\ccc-classic-20-10-2023\\src\\resources\\level5\\test5.txt"; // Replace with your output file path

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
                List<String> validRoute = findEncirclingSeaRoute(map, x, y);
                if (validRoute != null) {
                    writeRouteToFile(validRoute, writer);
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

    private static class Route {
        List<String> path;
        boolean[][] visited;

        public Route(List<String> path, boolean[][] visited) {
            this.path = path;
            this.visited = visited;
        }
    }

    private static boolean isValidCoordinate(int x, int y, int mapSize) {
        return x >= 0 && x < mapSize && y >= 0 && y < mapSize;
    }

    private static Route DijkstraSearch(char[][] map, int startX, int startY) {
        int mapSize = map.length;
        int[] dx = { 0, 1, 1, 1, 0, -1, -1, -1 };
        int[] dy = { -1, -1, 0, 1, 1, 1, 0, -1 };

        int[][] distance = new int[mapSize][mapSize];
        Point[][] prevPoint = new Point[mapSize][mapSize];

        for (int i = 0; i < mapSize; i++) {
            Arrays.fill(distance[i], Integer.MAX_VALUE);
        }

        PriorityQueue<Point> queue = new PriorityQueue<>(Comparator.comparingInt(p -> distance[p.y][p.x]));

        boolean[][] visited = new boolean[mapSize][mapSize];
        visited[startY][startX] = true;

        queue.offer(new Point(startX, startY));
        distance[startY][startX] = 0;

        while (!queue.isEmpty()) {
            Point current = queue.poll();

            int currentX = current.x;
            int currentY = current.y;

            for (int i = 0; i < 8; i++) {
                int newX = currentX + dx[i];
                int newY = currentY + dy[i];

                if (isValidCoordinate(newX, newY, mapSize) && map[newY][newX] == 'W' && !visited[newY][newX]) {
                    int newDistance = distance[currentY][currentX] + 1;

                    if (newDistance < distance[newY][newX]) {
                        distance[newY][newX] = newDistance;
                        prevPoint[newY][newX] = current;
                        queue.offer(new Point(newX, newY));
                        visited[newY][newX] = true;
                    }
                }
            }
        }

        // The route is not found
        if (distance[startY][startX] == Integer.MAX_VALUE) {
            return null;
        }

        // Reconstruct the path
        List<String> path = new ArrayList<>();
        int x = startX, y = startY;
        visited = new boolean[mapSize][mapSize];

        while (prevPoint[y][x] != null) {
            Point current = prevPoint[y][x];
            visited[y][x] = true;
            path.add(x + "," + y);
            x = current.x;
            y = current.y;

            if (visited[y][x]) {
                return null;
            }
        }
        path.add(x + "," + y);
        Collections.reverse(path);

        return new Route(path, visited);
    }

    private static List<String> findEncirclingSeaRoute(char[][] map, int startX, int startY) {
        int mapSize = map.length;
        int[][] distance = new int[mapSize][mapSize];

        for (int[] row : distance) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        Queue<Point> queue = new LinkedList<>();
        queue.offer(new Point(startX, startY));
        distance[startY][startX] = 0;

        List<String> bestRoute = null;
        int shortestDistance = Integer.MAX_VALUE;
        boolean[][] visited = new boolean[mapSize][mapSize];

        while (!queue.isEmpty()) {
            Point current = queue.poll();
            visited[current.y][current.x] = true;

            if (distance[current.y][current.x] > shortestDistance) {
                continue;
            }

            Route route = DijkstraSearch(map, current.x, current.y);

            if (route != null) {
                int routeLength = route.path.size();

                if (routeLength > 0 && routeLength < shortestDistance) {
                    shortestDistance = routeLength;
                    bestRoute = route.path;
                }

                for (int y = 0; y < mapSize; y++) {
                    for (int x = 0; x < mapSize; x++) {
                        if (!visited[y][x] && !route.visited[y][x]) {
                            queue.offer(new Point(x, y));
                            distance[y][x] = Math.min(distance[y][x], routeLength);
                        }
                    }
                }
            }
        }

        return bestRoute;
    }

    private static void writeRouteToFile(List<String> route, FileWriter writer) {
        if (route != null && !route.isEmpty()) {
            try {
                writer.write(String.join(" ", route) + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
