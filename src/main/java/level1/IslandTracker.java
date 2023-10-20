package level1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class IslandTracker {
    public static void check(String[] args) {
        String inputFile = "C:\\Users\\vasil\\htlstp\\ccc-classic-20-10-2023\\src\\resources\\level1\\level1_5.in";
        String outputFile = "level5.txt";

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
                String[] coordinate = scanner.nextLine().split(",");
                int x = Integer.parseInt(coordinate[0]);
                int y = Integer.parseInt(coordinate[1]);

                char tileType = map[y][x];
                writer.write(tileType + "\n");
            }

            scanner.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
