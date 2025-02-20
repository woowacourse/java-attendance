package io;

import controller.dto.AttendanceRequestDto;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import view.Parser;

public class CustomFileReader {
    private static List<String> readMarkdownFile(String path) throws FileNotFoundException {
        List<String> result = new ArrayList<>();

        Scanner scanner = new Scanner(new File(path));
        scanner.nextLine();
        while (scanner.hasNextLine()) {
            result.add(scanner.nextLine());
        }
        return result;
    }

    public static List<String> readCrewNames() throws FileNotFoundException {
        List<String> datum = readMarkdownFile("src/main/resources/attendances.csv");
        List<String> names = datum.stream().map(data -> data.split(",")[0]).toList();
        Set<String> nonDuplicatedNames = new HashSet<>(names);
        return new ArrayList<>(nonDuplicatedNames);
    }

    public static List<AttendanceRequestDto> readAttendanceInfo() throws FileNotFoundException {
        List<String> datum = readMarkdownFile("src/main/resources/attendances.csv");
        List<AttendanceRequestDto> result = new ArrayList<>();

        for (String data : datum) {
            String crewName = data.split(",")[0];
            String info = data.split(",")[1];
            String[] splitInfo = info.split(" ");
            int date = Integer.parseInt(splitInfo[0].split("-")[2]);
            result.add(new AttendanceRequestDto(date, Parser.parseAttendanceTime(splitInfo[1]), crewName));
        }

        return result;
    }
}
