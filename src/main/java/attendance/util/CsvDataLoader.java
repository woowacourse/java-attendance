package attendance.util;

import attendance.domain.Attendances;
import attendance.domain.Holiday;
import attendance.view.DataFileReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvDataLoader implements DataLoader{
    private static final String ATTENDANCE_FILE_PATH = "src/main/resources/attendances.csv";
    private static final int ATTENDANCE_FILE_HEADER_COUNT = 1;
    private static final String HOLIDAY_FILE_PATH = "src/main/resources/holidays.csv";
    private static final int HOLIDAY_FILE_HEADER_COUNT = 1;

    private static final String NAME_DATE_DELIMITER = ",";
    private static final DateTimeFormatter CONVERT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final int ATTENDANCE_CREW_NAME_INDEX = 0;
    private static final int ATTENDANCE_DATE_TIME_INDEX = 1;
    private static final int HOLIDAY_NAME_INDEX = 0;
    private static final int HOLIDAY_MONTH_INDEX = 1;
    private static final int HOLIDAY_DAY_INDEX = 2;

    @Override
    public Map<String, Attendances> loadAttendancesData() {
        Map<String, Attendances> crewAttendances = new HashMap<>();
        List<String> readFile = DataFileReader.readFile(ATTENDANCE_FILE_PATH, ATTENDANCE_FILE_HEADER_COUNT);

        readFile
                .forEach(line -> addData(crewAttendances, line));

        return crewAttendances;
    }

    private void addData(Map<String, Attendances> crewAttendances, String line) {
        String[] split = line.split(NAME_DATE_DELIMITER);
        String name = split[ATTENDANCE_CREW_NAME_INDEX];
        LocalDateTime dateTime = parseDateTime(split[ATTENDANCE_DATE_TIME_INDEX]);

        crewAttendances.putIfAbsent(name, new Attendances());
        Attendances attendances = crewAttendances.get(name);
        attendances.addAttendance(dateTime.toLocalDate(), dateTime.toLocalTime());
    }

    private LocalDateTime parseDateTime(String dateTimeString) {
        return LocalDateTime.parse(dateTimeString, CONVERT_FORMATTER);
    }

    @Override
    public List<Holiday> loadHolidayData(){
        List<Holiday> holidays = new ArrayList<>();
        List<String> readFile = DataFileReader.readFile(HOLIDAY_FILE_PATH, HOLIDAY_FILE_HEADER_COUNT);
        readFile
                .forEach(line -> {
                    try {
                        String[] split = line.split(",");
                        String name = split[HOLIDAY_NAME_INDEX];
                        int month = Integer.parseInt(split[HOLIDAY_MONTH_INDEX]);
                        int day = Integer.parseInt(split[HOLIDAY_DAY_INDEX]);
                        Holiday holiday = new Holiday(name, month, day);
                        holidays.add(holiday);
                    } catch (NumberFormatException e){
                        throw new RuntimeException("[ERROR] 날짜 정보는 숫자여야 합니다.");
                    }
                });
        return holidays;
    }

}
