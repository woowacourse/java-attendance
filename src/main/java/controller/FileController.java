package controller;

import domain.Crews;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import util.parser.DateTimeParser;

public class FileController {

    private static final String FILE_ERROR_MESSAGE = "파일을 불러오는 데 문제가 발생했습니다. 잠시 후 다시 시도해 주세요.";

    private final Crews crews;

    public FileController(Crews crews) {
        this.crews = crews;
    }

    public void initializeFile(String filePath) {
        try {
            Map<String, List<LocalDateTime>> result = createRecords(loadFile(filePath));
            result.forEach(crews::createCrew);
        } catch (FileNotFoundException e) {
            System.err.println(FILE_ERROR_MESSAGE);
        }
    }

    private Scanner loadFile(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);

        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
        return scanner;
    }

    private Map<String, List<LocalDateTime>> createRecords(Scanner attendaceScanner) {
        Map<String, List<LocalDateTime>> result = new HashMap<>();
        while (attendaceScanner.hasNextLine()) {
            String[] attributes = attendaceScanner.nextLine().split(",");
            String name = attributes[0];
            LocalDateTime dateTime = DateTimeParser.parseStringToDateTime(attributes[1]);

            result.putIfAbsent(name, new ArrayList<>());
            result.get(name).add(dateTime);
        }
        return result;
    }
}
