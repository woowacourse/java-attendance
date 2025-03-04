package domain;

import error.ErrorMessage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

import static error.ErrorMessage.NO_SUCH_NICKNAME;

public class AllCrew {
    private final List<Crew> allCrew;

    public AllCrew() {
        allCrew = new ArrayList<Crew>();
    }

    public void updateFile(String filePath) {
        processAllCrewFileData(readFileData(filePath));
    }

    public List<String> readFileData(String filePath) {
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(Path.of(filePath));
        } catch (IOException e) {
            throw new IllegalArgumentException(ErrorMessage.NO_ATTENDANCES_FILE.getMessage());
        }
        return lines;
    }

    private void processAllCrewFileData(List<String> rawData) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd HH mm");
        for (String line : rawData) {
            List<String> crewNameAndAttendanceTime = List.of(line.split("-"));
            String rawDateTime = crewNameAndAttendanceTime.get(1);
            loadCrewData(rawDateTime, formatter, crewNameAndAttendanceTime);
        }
    }

    private void loadCrewData(String rawDateTime, DateTimeFormatter formatter, List<String> crewNameAndAttendanceTime) {
        try {
            LocalDateTime dateTime = LocalDateTime.parse(rawDateTime, formatter);
            Attendance attendance = new Attendance(dateTime);
            addCrewInfoWithNameAndAttendance(crewNameAndAttendanceTime.getFirst(), attendance);
        } catch (DateTimeParseException e) {
            throw new DateTimeException(ErrorMessage.INVALID_FILE_TIME_FORMAT.getMessage());
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
        return allCrew.stream()
                .filter(crew -> crew.isSameName(crewName))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(NO_SUCH_NICKNAME.getMessage()));
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
        return allCrew.stream()
                .filter(crew -> crew.getPenalty() != Penalty.NONE)
                .collect(Collectors.toList());
    }

    public void sortPenaltyReceivedCrew(List<Crew> penaltyReceivedCrew) {
        penaltyReceivedCrew
                .sort(Comparator.comparing(Crew::getPenaltyStandard)
                        .reversed()
                        .thenComparing(Crew::getName));
    }

}