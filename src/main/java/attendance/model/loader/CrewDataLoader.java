package attendance.model.loader;

import attendance.util.DateFormatUtil;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.Objects;

public class CrewDataLoader {
    public static final String ROW_DELIMITER = ",";
    public static final int CREW_NAME_COLUMN_INDEX = 0;
    public static final int ATTENDANCE_DATE_TIME_COLUMN_INDEX = 1;

    private final RawAttendanceStore rawAttendanceStore;


    public CrewDataLoader(RawAttendanceStore rawAttendanceStore) {
        this.rawAttendanceStore = rawAttendanceStore;
    }

    public void load(String path) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(path)))
        );
        bufferedReader.lines().skip(1).forEach(row -> {
            String[] parsed = parseRow(row);
            String crewName = parsed[CREW_NAME_COLUMN_INDEX];
            LocalDateTime dateTime = DateFormatUtil.parseLocalDateTime(parsed[ATTENDANCE_DATE_TIME_COLUMN_INDEX]);
            rawAttendanceStore.add(crewName, dateTime);
        });
        //fillAbsencesForNoAttendance();
    }

    private String[] parseRow(String row) {
        return row.split(ROW_DELIMITER);
    }

//    private void fillAbsencesForNoAttendance() {
//        LocalDate currentDate = LocalDate.of(2024, 12, 1);
//        while (isAttendableDate(currentDate)) {
//            addAbsence(currentDate);
//            currentDate = currentDate.plusDays(1);
//        }
//    }
//
//    private boolean isAttendableDate(LocalDate currentDate) {
//        return currentDate.isBefore(LocalDate.of(2025, 1, 1)) &&
//                currentDate.isBefore(fixedDateTime.toLocalDate());
//    }
//
//    private void addAbsence(LocalDate currentDate) {
//        for (Crew crew : crews.getCrews()) {
//            AttendanceHistory attendanceHistory = crew.getAttendanceHistory();
//            if (!canAttend(attendanceHistory, currentDate)) {
//                continue;
//            }
//            LocalDateTime absenceDatetime = LocalDateTime.of(currentDate, ABSENCE_TIME);
//            crew.attend(new AttendanceDetail(absenceDatetime));
//        }
//    }
//
//    private boolean canAttend(AttendanceHistory attendanceHistory, LocalDate currentDate) {
//        return !(attendanceHistory.containsDate(currentDate) || CustomLocalDateTime.isWeekendOrHoliday(currentDate));
//    }

//    private void addCrew(Crew crew, LocalDateTime dateTime) {
//        if (!crews.containsCrew(crew.getName())) {
//            crews.add(crew);
//            crew.attend(new AttendanceDetail(dateTime));
//            return;
//        }
//        crews.findCrew(crew.getName()).attend(new AttendanceDetail(dateTime));
//    }


}
