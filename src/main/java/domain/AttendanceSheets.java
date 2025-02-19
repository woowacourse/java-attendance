package domain;

import java.util.List;

public class AttendanceSheets {

    List<AttendanceSheet> attendanceSheets;

    public AttendanceSheets(List<AttendanceSheet> attendanceSheets) {
        this.attendanceSheets = attendanceSheets;
    }

    public void add(AttendanceSheet attendanceSheet) {
        validateIsAlreadyAttendance(attendanceSheet);
        attendanceSheets.add(attendanceSheet);
    }

    private void validateIsAlreadyAttendance(AttendanceSheet attendanceSheet) {
        if(findAttendanceByNickname(attendanceSheet.getNickname()).stream()
                .anyMatch(attendanceSheet1 -> attendanceSheet1.isSame(attendanceSheet)))
        {
            throw new IllegalArgumentException("[ERROR] 이미 출석했습니다. 수정 기능을 이용하세요");
        }

    }


    public List<AttendanceSheet> findAttendanceByNickname(String nickname){
        return attendanceSheets.stream()
                .filter(attendanceSheet -> attendanceSheet.hasNickname(nickname))
                .toList();
    }

    public List<String> findAllNames() {
        return attendanceSheets.stream()
                .map(AttendanceSheet::getNickname)
                .distinct()
                .toList();
    }
}
