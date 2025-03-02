package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileReader {

    public static List<String> fileReadLine(String fileName) {
        try (BufferedReader br = loadFile(fileName)) {
            List<String> items = new ArrayList<>();
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                items.add(line);
            }
            return items;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static BufferedReader loadFile(String fileName) {
        InputStream inputStream = FileReader.class.getClassLoader().getResourceAsStream(fileName);
        if (inputStream == null) {
            throw new IllegalArgumentException("[ERROR] 파일이 존재하지 않습니다");
        }
        return new BufferedReader(new InputStreamReader(inputStream));
    }

}
