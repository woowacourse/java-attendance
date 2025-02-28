package attendance.domain;

import attendance.util.DateGenerator;
import attendance.util.DateTimeParser;
import attendance.util.FileUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrewAttendanceManager {

    private final Map<String, Attendances> crewAttendance = new HashMap<>();
    private final DateGenerator dateGenerator;
    private final Holidays holidays;

    public CrewAttendanceManager(DateGenerator dateGenerator) {
        this.dateGenerator = dateGenerator;
        holidays = new Holidays();
    }

    public void initAttendanceFromFile() {
        List<String> readLines = FileUtil.readFile("attendances.csv");

        for (String readLine : readLines) {
            String[] split = readLine.split(",");

            String nickname = split[0];
            LocalDateTime dateTime = DateTimeParser.parseDateTime(split[1]);

            if (!crewAttendance.containsKey(nickname)) {
                LocalDate nowDate = dateGenerator.generate();
                int lengthOfMonth = nowDate.lengthOfMonth();

                List<Attendance> defaultAttendances = new ArrayList<>();
                for (int day = 1; day < lengthOfMonth; day++) {
                    LocalDate date = nowDate.withDayOfMonth(day);
                    if (holidays.isHoliday(date)) {
                        continue;
                    }

                    LocalDateTime defaultDateTime = LocalDateTime.of(date, LocalTime.MAX);
                    defaultAttendances.add(Attendance.fromDateTime(defaultDateTime));
                }
                addNewCrew(nickname, new Attendances(defaultAttendances));
            }

            processAttendanceCheck(nickname, dateTime);
        }
    }

    public void addNewCrew(String nickname, Attendances attendances) {
        crewAttendance.put(nickname, attendances);
    }

    public Attendance processAttendanceCheck(final String nickname, final LocalDateTime dateTime) {
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
}
