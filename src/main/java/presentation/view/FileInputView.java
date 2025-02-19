package presentation.view;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileInputView {
    public Map<String, List<String>> getFileInput() {

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("attendances.csv");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        //수정
        Map<String, List<String>> map = new HashMap<>();

        reader.lines().skip(1).forEach(fileData -> addInitDate(map, fileData));
        return map;
    }

    private void addInitDate(Map<String, List<String>> map, String fileData) {
        String[] parsedData = fileData.split(",");

        // todo: 수정
        List<String> mapInside = map.getOrDefault(parsedData[0], new ArrayList<>());
        mapInside.add(parsedData[1]);
        map.put(parsedData[0], mapInside);
    }
}
