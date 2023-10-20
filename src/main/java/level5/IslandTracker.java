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
        String inputFile = "D:\\OutsourcedIdeaProject\\ccc-classic-20-10-2023\\src\\resources\\level5_example.in";
        String outputFile = "D:\\OutsourcedIdeaProject\\ccc-classic-20-10-2023\\src\\resources\\level5\\test5.txt";

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

    private static List<String> findEncirclingSeaRoute(char[][] map, int startX, int startY) {
        int mapSize = map.length;

        int[] dx = { 0, 1, 1, 1, 0, -1, -1, -1 };
        int[] dy = { -1, -1, 0, 1, 1, 1, 0, -1 };

        for (int i = 0; i < 8; i++) {
            int endX = startX + dx[i];
            int endY = startY + dy[i];

            if (isValidCoordinate(endX, endY, mapSize) && map[endY][endX] == 'W') {
                List<String> validRoute = dijkstra(map, startX, startY, endX, endY);
                if (validRoute != null) {
                    return validRoute;
                }
            }
        }

        return null; // No valid route found
    }

    private static List<String> dijkstra(char[][] map, int startX, int startY, int endX, int endY) {
        int mapSize = map.length;
        int[][] distance = new int[mapSize][mapSize];
        boolean[][] visited = new boolean[mapSize][mapSize];

        for (int[] row : distance) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        Queue<Point> queue = new LinkedList<>();
        queue.offer(new Point(startX, startY));
        distance[startY][startX] = 0;

        while (!queue.isEmpty()) {
            Point current = queue.poll();

            if (current.x == endX && current.y == endY) {
                return reconstructPath(current);
            }

            if (visited[current.y][current.x]) {
                continue;
            }

            visited[current.y][current.x] = true;

            int[] dx = { 0, 1, 1, 1, 0, -1, -1, -1 };
            int[] dy = { -1, -1, 0, 1, 1, 1, 0, -1 };

            for (int i = 0; i < 8; i++) {
                int newX = current.x + dx[i];
                int newY = current.y + dy[i];

                if (isValidCoordinate(newX, newY, mapSize) && map[newY][newX] == 'W' && !visited[newY][newX]) {
                    int newDistance = distance[current.y][current.x] + 1;
                    if (newDistance < distance[newY][newX]) {
                        distance[newY][newX] = newDistance;
                        Point next = new Point(newX, newY);
                        next.prev = current;
                        queue.offer(next);
                    }
                }
            }
        }

        return null; // No valid route found
    }

    private static boolean isValidCoordinate(int x, int y, int mapSize) {
        return x >= 0 && x < mapSize && y >= 0 && y < mapSize;
    }

    private static List<String> reconstructPath(Point end) {
        List<String> path = new ArrayList<>();
        while (end != null) {
            path.add(end.x + "," + end.y);
            end = end.prev;
        }
        Collections.reverse(path);
        return path;
    }
}
