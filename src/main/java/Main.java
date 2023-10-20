


import java.nio.file.Path;
import java.util.List;


public class Main {

    String path = "C:\\Users\\sjozw\\Desktop\\szkola\\pos\\CCC_SJJ_20_10_23\\src\\main\\resources";

    List<String> testFiles = List.of(
            (path + "\\level1_1.in"),
            (path + "\\level1_2.in"),
            (path + "\\level1_3.in"),
            (path + "\\level1_4.in"),
            (path + "\\level1_5.in"),
            (path + "\\level1_6.in"),
            (path + "\\level1_7.in")
    );

    public void runTest(FileOperation fileOperation) {
        for (String testFile : testFiles) {
            List<String> lines = fileOperation.readFile(Path.of(testFile));
            String result = "";
            fileOperation.writeFile(Path.of("src/level_2/level2_1.out"), result);
            System.out.println(lines);
        }
    }

}
