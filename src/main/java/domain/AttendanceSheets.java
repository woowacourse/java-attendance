package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class AttendanceSheets {

    private List<AttendanceSheet> attendanceSheets;

    public AttendanceSheets(List<AttendanceSheet> attendanceSheets) {
        this.attendanceSheets = new ArrayList<>(attendanceSheets);
    }

    private void validateExistAttendanceSheetsByNickname(List<AttendanceSheet> foundAttendance) {
        if (foundAttendance.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 해당 닉네임은 출석 기록이 존재하지 않습니다.");
        }
    }

    private void validateNullOrEmptyNickname(String nickname) {
        if (nickname == null || nickname.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다.");
        }
    }

    public void add(AttendanceSheet attendanceSheet) {
        validateIsAlreadyAttendance(attendanceSheet);
        attendanceSheets.add(attendanceSheet);
    }

    private void validateIsAlreadyAttendance(AttendanceSheet attendanceSheet) {
        if (attendanceSheets.stream()
                .anyMatch(sheet -> sheet.equals(attendanceSheet))) {
            throw new IllegalArgumentException("[ERROR] 이미 출석했습니다. 수정 기능을 이용하세요.");
        }
    }

    public AttendanceSheet findAttendanceSheetByNicknameAndDay(String nickname, int day) {
        List<AttendanceSheet> attendanceByNickname = findAttendanceByNickname(nickname);

        return attendanceByNickname.stream().filter(sheet -> sheet.isSameDay(day)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 출석 기록이 없는 날짜입니다."));
    }

    public List<AttendanceSheet> findAttendanceByNickname(String nickname) {
        validateNullOrEmptyNickname(nickname);

        List<AttendanceSheet> foundAttendance = attendanceSheets.stream()
                .filter(attendanceSheet -> attendanceSheet.hasNickname(nickname))
                .toList();

        validateExistAttendanceSheetsByNickname(foundAttendance);

        return foundAttendance;
    }

    public List<String> findAllNames() {
        return attendanceSheets.stream().map(AttendanceSheet::getNickname).distinct().toList();
    }

    public AttendanceStatics calculateRiskOfExpulsionBy(String nickname, LocalDate today) {
        int lateCount = calculateLateCountBy(nickname);
        int absentCount = calculateAbsentCount(nickname, today);

        return new AttendanceStatics(0, lateCount, absentCount);
    }

    public AttendanceStatics calculateAttendanceStaticsBy(String nickname, LocalDate today) {
        int attendCount = calculateAttendCountBy(nickname);
        int lateCount = calculateLateCountBy(nickname);
        int absentCount = calculateAbsentCount(nickname, today);

        return new AttendanceStatics(attendCount, lateCount, absentCount);
    }

    public int calculateAttendCountBy(String nickname) {
        return calculateCountByNicknameAndState(nickname, AttendanceState.ATTEND);
    }

    public int calculateLateCountBy(String nickname) {
        return calculateCountByNicknameAndState(nickname, AttendanceState.LATE);
    }

    private int calculateCountByNicknameAndState(String nickname, AttendanceState state) {
        List<AttendanceSheet> attendanceByNickname = findAttendanceByNickname(nickname);
        int count = 0;

        for (AttendanceSheet attendanceSheet : attendanceByNickname) {
            AttendanceDateTime attendanceDateTime = attendanceSheet.getAttendanceDateTime();

            count += countStateByAttendanceDateTime(attendanceDateTime, state);
        }

        return count;
    }

    public int calculateAbsentCount(String nickname, LocalDate today) {
        List<AttendanceSheet> attendanceByNickname = findAttendanceByNickname(nickname);
        return IntStream.range(Calendar.DECEMBER.startDay, today.getDayOfMonth())
                .boxed()
                .mapToInt(day -> countAbsentState(attendanceByNickname, day))
                .sum();
    }

    private int countAbsentState(List<AttendanceSheet> attendanceByNickname, int day) {
        AttendanceSheet attendanceSheet = attendanceByNickname.stream()
                .filter(sheet -> sheet.isSameDay(day))
                .findAny()
                .orElse(null);
        return countAbsentByDay(attendanceSheet, day);
    }

    private int countAbsentByDay(AttendanceSheet attendanceSheet, int day) {
        if (attendanceSheet == null) {
            return calculateCountByDayOfWeek(day);
        }

        return countStateByAttendanceDateTime(attendanceSheet.getAttendanceDateTime(), AttendanceState.ABSENT);
    }

    private int countStateByAttendanceDateTime(AttendanceDateTime datetime, AttendanceState state) {
        if (datetime.check() == state) {
            return 1;
        }

        return 0;
    }

    private int calculateCountByDayOfWeek(int day) {
        LocalDate localDate = LocalDate.of(2024, 12, day);
        DayOfWeek week = localDate.getDayOfWeek();

        if (week == DayOfWeek.SATURDAY || week == DayOfWeek.SUNDAY || localDate.isEqual(LocalDate.of(2024, 12, 25))) {
            return 0;
        }

        return 1;
    }
}
