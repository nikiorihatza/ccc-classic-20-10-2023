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
        String inputFile = "D:\\OutsourcedIdeaProject\\ccc-classic-20-10-2023\\src\\resources\\level4_5.in";
        String outputFile = "D:\\OutsourcedIdeaProject\\ccc-classic-20-10-2023\\src\\resources\\level4\\4_5.txt";

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
                List<String> validRoute = findValidSeaRoute(map, coordinates);
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

    private static List<String> findValidSeaRoute(char[][] map, String[] coordinates) {
        String[] startCoordinates = coordinates[0].split(",");
        String[] endCoordinates = coordinates[1].split(",");

        int startX = Integer.parseInt(startCoordinates[0]);
        int startY = Integer.parseInt(startCoordinates[1]);
        int endX = Integer.parseInt(endCoordinates[0]);
        int endY = Integer.parseInt(endCoordinates[1]);

        List<String> validRoute = dijkstra(map, startX, startY, endX, endY);

        return validRoute;
    }

    private static List<String> dijkstra(char[][] map, int startX, int startY, int endX, int endY) {
        int[][] distance = new int[map.length][map[0].length];
        boolean[][] visited = new boolean[map.length][map[0].length];

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

            int[] dx = { -1, 0, 1, 0, -1, -1, 1, 1 };
            int[] dy = { 0, -1, 0, 1, -1, 1, -1, 1 };

            for (int i = 0; i < 8; i++) {
                int newX = current.x + dx[i];
                int newY = current.y + dy[i];

                if (isValidCoordinate(newX, newY, map) && map[newY][newX] == 'W' &&
                        !visited[newY][newX]) {
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

    private static boolean isValidCoordinate(int x, int y, char[][] map) {
        return x >= 0 && x < map[0].length && y >= 0 && y < map.length;
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
