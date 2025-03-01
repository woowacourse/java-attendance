package view;

import domain.Crew;
import domain.CrewGroup;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

import static util.DateTimeUtils.*;

public class FileInputView {
    private static final int SKIP_DISTANCE = 1;
    private static final int NAME_INDEX = 0;
    private static final int ATTENDANCE_DATE_INDEX = 1;

    public static CrewGroup loadInitFileData() throws IOException {
        CrewGroup crews = new CrewGroup();
        List<String> fileLines = Files.readAllLines(Path.of("src/main/resources/attendances.csv"));
        fileLines.stream().skip(SKIP_DISTANCE).forEach(fileLine -> addInitData(crews,fileLine));

        return crews;
    }

    private static void addInitData(CrewGroup crews, String attendanceLine){
        String crewName = parseCrewName(attendanceLine);
        LocalDateTime attendRecord = parseCrewAttendance(attendanceLine);

        if(!crews.has(crewName)) crews.addCrew(crewName);

        Crew findCrew = crews.findByName(crewName);
        findCrew.getAttendanceRecord().addAttendance(attendRecord);
    }

    private static String parseCrewName(String line){
        return line.split(",")[NAME_INDEX];
    }

    private static LocalDateTime parseCrewAttendance(String line){
        String parseLine = line.split(",")[ATTENDANCE_DATE_INDEX];
        return LocalDateTime.parse(parseLine, dateTimeInputFormatter);
    }
}
