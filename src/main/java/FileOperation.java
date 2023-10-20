import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created: 20.10.23
 */

public class FileOperation {
    public List<String> readFile(Path path) {
        List<String> returnLines = new ArrayList<>();
        try (Stream<String> lines = Files.lines(path)) {
            lines.forEach(returnLines::add);
        } catch (IOException e) {
            System.out.println("Error reading file: " + path.toString());
            e.printStackTrace();
        }
        return returnLines;
    }

    public void writeFile(Path path, String lines) {
        try {
            Files.writeString(path, lines);
        } catch (IOException e) {
            System.out.println("Error writing file: " + path.toString());
            e.printStackTrace();
        }
    }
}
