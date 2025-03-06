package util;

import domain.AttendanceManager;
import domain.CrewAttendanceBook;
import java.util.List;

public class DataInitializer {

    private final FileLoader fileLoader;
    private final StringParser stringParser;
    private final AttendanceManager attendanceManager;

    public DataInitializer(FileLoader fileLoader, StringParser stringParser, AttendanceManager attendanceManager) {
        this.fileLoader = fileLoader;
        this.stringParser = stringParser;
        this.attendanceManager = attendanceManager;
    }

    public CrewAttendanceBook initCrewAttendanceBook(String filePath) {
        List<String> rawAttendanceInfo = fileLoader.loadLinesExcludeTitle(filePath);
        List<AttendanceInfo> attendanceInfos = rawAttendanceInfo.stream()
                .map(stringParser::parseToAttendanceInfo)
                .toList();
        attendanceInfos.forEach(info -> attendanceManager.processAttendance(
                info.crewName(),
                info.attendanceDate(),
                info.attendanceTime())
        );
        return attendanceManager.crewAttendanceBook();
    }
}
