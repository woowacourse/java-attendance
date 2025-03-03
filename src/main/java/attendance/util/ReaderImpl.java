package attendance.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReaderImpl implements Reader {

    @Override
    public List<String> getContents(final String filePath) {

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            return readContents(reader);
        } catch (IOException e) {
            throw new IllegalArgumentException("[ERROR] 파일을 읽어오는 중 오류가 발생했습니다.");
        }
    }

    private List<String> readContents(final BufferedReader reader) throws IOException {

        List<String> contents = new ArrayList<>();
        String content;
        while ((content = reader.readLine()) != null) {
            contents.add(content);
        }
        return contents;
    }
}
