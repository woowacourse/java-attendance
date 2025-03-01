package domain;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class AllCrew {
    private final List<Crew> allCrew;

    public AllCrew() {
        allCrew = new ArrayList<Crew>();
    }

    public AllCrew(Scanner fileScanner) {
        allCrew = new ArrayList<>();
        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            List<String> crewNameAndAttendanceTime = List.of(line.split("-"));
            String rawDateTime = crewNameAndAttendanceTime.get(1);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd HH mm");
            loadFileAllCrewData(rawDateTime, formatter, crewNameAndAttendanceTime);
        }
    }

    private void loadFileAllCrewData(String rawDateTime, DateTimeFormatter formatter, List<String> crewNameAndAttendanceTime) {
        try {
            LocalDateTime dateTime = LocalDateTime.parse(rawDateTime, formatter);
            Attendance attendance = new Attendance(dateTime);
            addCrewInfoWithNameAndAttendance(crewNameAndAttendanceTime.getFirst(), attendance);
        } catch (DateTimeParseException e) {
            throw new DateTimeException(ERROR_MESSAGE.INVALID_FILE_TIME_FORMAT.getMessage());
        }
    }

    public void addCrewInfoWithNameAndAttendance(String crewName, Attendance attendance) {
        if (containsCrewName(crewName)) {
            findCrewByName(crewName).addAttendance(attendance);
            return;
        }
        Crew crew = new Crew(crewName);
        crew.addAttendance(attendance);
        allCrew.add(crew);
    }

    public boolean containsCrewName(String crewName) {
        return allCrew.stream().anyMatch(crew -> crew.isSameName(crewName));
    }

    public Crew findCrewByName(String crewName) {
        for (Crew crew : allCrew) {
            if (crew.isSameName(crewName))
                return crew;
        }
        throw new NoSuchElementException("[ERROR] 등록되지 않은 닉네임입니다.");
    }

    public void fillAllCrewsEmptyDateWithAbsent(LocalDate date) {
        for (Crew crew : allCrew) {
            crew.fillEmptyDateWithAbsent(date);
        }
    }

    public int getAbsentCountWithCrewName(String crewName) {
        return findCrewByName(crewName).getAbsentCount();
    }

    public List<Crew> getPenaltyReceivedCrew() {
        List<Crew> penaltyReceivedCrew = new ArrayList<>();
        for (Crew crew : allCrew) {
            if (crew.getPenalty() != Penalty.NONE)
                penaltyReceivedCrew.add(crew);
        }
        return penaltyReceivedCrew;
    }
}