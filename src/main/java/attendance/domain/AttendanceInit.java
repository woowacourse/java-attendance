package attendance.domain;

import attendance.utility.DateGenerator;
import attendance.utility.DateTimeParser;
import attendance.utility.FileUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.IntStream;

public class AttendanceInit {

    private static final String INIT_FILE_NAME = "attendances.csv";
    private static final String INFO_DELIMITER = ",";

    private final AttendanceManager attendanceManager;
    private final DateGenerator dateGenerator;
    private final Holiday holiday;

    public AttendanceInit(final AttendanceManager attendanceManager, final Holiday holiday, final DateGenerator dateGenerator) {
        this.attendanceManager = attendanceManager;
        this.dateGenerator = dateGenerator;
        this.holiday = holiday;
    }

    public void initSystem() {
        initAttendances();
        initHoliday();
    }

    private void initAttendances() {
        List<String> lines = FileUtil.readFile(INIT_FILE_NAME);
        lines.forEach(this::initAttendance);
    }

    public void initHoliday() {
        holiday.addHoliday(LocalDate.of(2024, 12, 25));
    }

    private void initAttendance(String line) {
        List<String> attendanceInfo = List.of(line.split(INFO_DELIMITER));

        String nickname = attendanceInfo.getFirst();
        LocalDateTime dateTime = DateTimeParser.parseDateTime(attendanceInfo.get(1));

        addNewCrew(nickname);
        insertAttendance(nickname, dateTime);
    }

    private void addNewCrew(String nickname) {
        if (!attendanceManager.containsNickname(nickname)) {

            int day = dateGenerator.now().getDayOfMonth();

            Attendances newAttendances = new Attendances();
            IntStream.range(1, day + 1)
                    .mapToObj(index -> LocalDateTime.of(dateGenerator.now().withDayOfMonth(index), LocalTime.MAX))
                    .filter(dateTime -> !holiday.isHoliday(dateTime.toLocalDate()))
                    .forEach(newAttendances::addAttendance);

            attendanceManager.addCrew(nickname, newAttendances);
        }
    }

    private void insertAttendance(String nickname, LocalDateTime dateTime) {
        Attendances attendances = attendanceManager.findCrewAttendance(nickname);

        attendances.deleteAttendance(dateTime.toLocalDate());
        attendances.addAttendance(dateTime);
    }
}
