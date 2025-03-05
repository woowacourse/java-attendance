package attendance.domain;

import attendance.util.DateGenerator;
import attendance.util.DateTimeParser;
import attendance.util.FileUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class CrewAttendanceManager {

    public static final String HOLIDAYS_CSV_FILE_NAME = "holidays.csv";

    private final Map<String, Attendances> crewAttendance = new HashMap<>();
    private final DateGenerator dateGenerator;
    private final Holidays holidays;

    public CrewAttendanceManager(DateGenerator dateGenerator, final String fileName) {
        initAttendanceFromFile(fileName);

        this.dateGenerator = dateGenerator;
        holidays = new Holidays(HOLIDAYS_CSV_FILE_NAME);
    }

    public void addNewCrew(final String nickname, final Attendances attendances) {
        crewAttendance.put(nickname, attendances);
    }

    public void validateNicknameExists(final String nickname) {
        if (!crewAttendance.containsKey(nickname)) {
            throw new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다.");
        }
    }

    public Attendance processAttendanceCheck(final String nickname, final LocalTime time) {
        LocalDateTime dateTime = LocalDateTime.of(dateGenerator.generate(), time);

        Attendances attendances = crewAttendance.get(nickname);
        Attendances newAttendances = attendances.registerAttendance(dateTime);
        crewAttendance.put(nickname, newAttendances);

        return newAttendances.findAttendanceByDate(dateTime.toLocalDate());
    }

    public AttendanceUpdate processAttendanceUpdate(final String nickname, final LocalDateTime dateTime) {
        Attendances attendances = crewAttendance.get(nickname);
        Attendances newAttendances = attendances.updateAttendance(dateTime);
        crewAttendance.put(nickname, newAttendances);

        Attendance beforeAttendance = attendances.findAttendanceByDate(dateTime.toLocalDate());
        Attendance afterAttendance = newAttendances.findAttendanceByDate(dateTime.toLocalDate());
        return new AttendanceUpdate(beforeAttendance, afterAttendance);
    }

    public AttendanceRecord getAttendanceRecord(String nickname) {
        Attendances attendances = crewAttendance.get(nickname);
        List<Attendance> excludingToday = attendances.getAttendancesBefore(dateGenerator.generate());
        return AttendanceRecord.fromNicknameAndAttendances(nickname, excludingToday);
    }

    public AttendanceRecords getAttendanceRecords() {
        List<AttendanceRecord> records = crewAttendance.keySet().stream()
                .map(this::getAttendanceRecord)
                .toList();
        return new AttendanceRecords(records);
    }

    private void initAttendanceFromFile(final String fileName) {
        List<String> fileLines = FileUtil.readFile(fileName);
        fileLines.forEach(this::initAttendanceFromLine);
    }

    private void initAttendanceFromLine(final String line) {
        List<String> lineComponents = List.of(line.split(","));

        String nickname = lineComponents.getFirst();
        LocalDateTime dateTime = DateTimeParser.parseDateTime(lineComponents.getLast());

        if (!crewAttendance.containsKey(nickname)) {
            initDefaultAttendancesForCrew(nickname);
        }
        processAttendanceUpdate(nickname, dateTime);
    }

    private void initDefaultAttendancesForCrew(final String nickname) {
        LocalDate nowDate = dateGenerator.generate();
        int lengthOfMonth = nowDate.lengthOfMonth();

        List<Attendance> defaultAttendances = IntStream.range(1, lengthOfMonth + 1)
                .mapToObj(nowDate::withDayOfMonth)
                .filter(holidays::isNotHoliday)
                .map(date -> LocalDateTime.of(date, LocalTime.MAX))
                .map(Attendance::createFromDateTime)
                .toList();
        addNewCrew(nickname, new Attendances(defaultAttendances));
    }
}
