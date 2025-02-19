package attendance.service;

import attendance.domain.AttendanceManager;
import attendance.domain.DateTimeFormatterWrapper;
import attendance.repository.AttendanceFileRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class AttendanceManagerService {
    private final AttendanceManager attendanceManager;
    private final AttendanceFileRepository attendanceFileRepository;
    private String ATTENDANCE_RESULT_FORMAT = "%s (%s)";


    public AttendanceManagerService(AttendanceManager attendanceManager, AttendanceFileRepository attendanceFileRepository) {
        this.attendanceManager = attendanceManager;
        this.attendanceFileRepository = attendanceFileRepository;
        initiateAttendanceManager();
    }

    private void initiateAttendanceManager(){
        List<String> attendanceLines = attendanceFileRepository.loadAttendanceLinesFromAttendanceFile();

        for(String attendanceLine : attendanceLines){
            String[] attendanceUnits = attendanceLine.split(",");
            String nickname = attendanceUnits[0];
            LocalDateTime datetime = DateTimeFormatterWrapper.parsingAttendanceDate(attendanceUnits[1]);
            addAttendance(nickname, datetime);
        }
    }

    private void addAttendance(String nickname, LocalDateTime datetime) {
        attendanceManager.addAttendance(nickname, datetime);
    }

    public String attendanceResult(String nickname, LocalDate attendanceDate) {
        var attendances = attendanceManager.findAttendances(nickname);
        var attendanceTime = attendances.getAttendanceTime(attendanceDate);
        var attendanceStatus = attendances.getAttendanceStatus(attendanceDate);

        var dateTimeFormatResult = DateTimeFormatterWrapper.parsingAttendanceResult(LocalDateTime.of(attendanceDate, attendanceTime));
        return String.format(ATTENDANCE_RESULT_FORMAT,dateTimeFormatResult,attendanceStatus.getStatus());
    }
}
