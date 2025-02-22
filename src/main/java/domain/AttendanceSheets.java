package domain;

import java.util.ArrayList;
import java.util.List;

public class AttendanceSheets {

    List<AttendanceSheet> attendanceSheets;

    public AttendanceSheets(List<AttendanceSheet> attendanceSheets) {
        this.attendanceSheets = new ArrayList<>(attendanceSheets);
    }

    public void add(AttendanceSheet attendanceSheet) {
        validateIsAlreadyAttendance(attendanceSheet);
        attendanceSheets.add(attendanceSheet);
    }

    private void validateIsAlreadyAttendance(AttendanceSheet attendanceSheet) {
        if (findAttendanceByNickname(attendanceSheet.getNickname()).stream()
                .anyMatch(attendanceSheet1 -> attendanceSheet1.isSame(attendanceSheet))) {
            throw new IllegalArgumentException("[ERROR] 이미 출석했습니다. 수정 기능을 이용하세요.");
        }

    }


    public List<AttendanceSheet> findAttendanceByNickname(String nickname) {
        validateHasNickname(nickname);

        List<AttendanceSheet> foundAttendance = attendanceSheets.stream()
                .filter(attendanceSheet -> attendanceSheet.hasNickname(nickname))
                .toList();

        validateExistAttendanceSheetsByNickname(foundAttendance);

        return foundAttendance;
    }

    private static void validateExistAttendanceSheetsByNickname(List<AttendanceSheet> foundAttendance) {
        if (foundAttendance == null || foundAttendance.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 해당 닉네임은 출석 기록이 존재하지 않습니다.");
        }
    }

    private static void validateHasNickname(String nickname) {
        if (nickname == null || nickname.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 해당 닉네임은 존재하지 않습니다.");
        }
    }

    public List<String> findAllNames() {
        return attendanceSheets.stream()
                .map(AttendanceSheet::getNickname)
                .distinct()
                .toList();
    }
}
