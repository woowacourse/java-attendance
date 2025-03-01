package config;

import domain.Attendance;
import domain.AttendanceSheet;
import domain.policy.AbsentPolicy;
import domain.policy.TimePolicy;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class AttendanceSheetFactory extends ReadFile<Attendance,AttendanceSheet> {

    private static final String SPLIT_DELIMITER = ",";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final int LINE_SPLIT_COUNT = 2;

    private final TimePolicy timePolicy;
    private final AbsentPolicy absentPolicy;

    public AttendanceSheetFactory(TimePolicy timePolicy, AbsentPolicy absentPolicy) {
        this.timePolicy = timePolicy;
        this.absentPolicy = absentPolicy;
    }

    private Attendance createAttendance(String line) {
        String[] splitLine = line.split(SPLIT_DELIMITER);
        validateSplitLineFormat(splitLine);

        String nickname = splitLine[0];
        LocalDateTime dateTime = parseAttendanceDateTime(splitLine[1]);

        return new Attendance(nickname, dateTime.toLocalDate(), dateTime.toLocalTime(), absentPolicy.checkAttendanceStatus(dateTime));
    }

    private void validateSplitLineFormat(String[] splitLine) {
        if (splitLine.length != LINE_SPLIT_COUNT) {
            throw new IllegalArgumentException("[ERROR] 파일 형식이 잘못되었습니다");
        }
    }

    private LocalDateTime parseAttendanceDateTime(String dateTime) {
        try{
            return LocalDateTime.parse(dateTime, DATE_TIME_FORMATTER);
        }catch (DateTimeParseException e){
            throw new IllegalArgumentException("[ERROR] 날짜 형식이 잘못되었습니다");
        }
    }

    @Override
    protected Attendance createInstance(String line) {
        return createAttendance(line);
    }

    @Override
    protected AttendanceSheet createInstances(List<Attendance> instances) {
        return new AttendanceSheet(timePolicy, absentPolicy, instances);
    }
}
