package attendance.controller;

import attendance.domain.*;
import attendance.domain.AttendanceStatusChecker.AttendanceStatus;
import attendance.view.AttendanceConfirmView;
import attendance.view.AttendanceModifyView;
import attendance.view.FileLineReader;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class AttendanceController {

    public static final String ATTENDANCE_FILE_PATH = "src/main/resources/";
    public static final String ATTENDANCE_FILE_NAME = "attendances.csv";
    private final AttendanceConfirmView attendanceConfirmView;
    private final AttendanceModifyView attendanceModifyView;

    public AttendanceController(final AttendanceConfirmView attendanceConfirmView,
                                final AttendanceModifyView attendanceModifyView) {
        this.attendanceConfirmView = attendanceConfirmView;
        this.attendanceModifyView = attendanceModifyView;
    }

    public void run() {
        AttendanceBook attendanceBook = initializeAttendanceBook();
        modifyAttendance(attendanceBook);
    }

    private AttendanceBook initializeAttendanceBook() {
        FileLineReader fileLineReader = new FileLineReader();
        List<String> crewAttendanceTexts = fileLineReader.readAllLines(ATTENDANCE_FILE_PATH, ATTENDANCE_FILE_NAME);
        crewAttendanceTexts.removeFirst();
        AttendanceBookInitializer attendanceBookInitializer = new AttendanceBookInitializer();
        Map<Crew, List<AttendanceDateTime>> crewAttendances = attendanceBookInitializer.parseTexts(crewAttendanceTexts);
        return new AttendanceBook(crewAttendances);
    }

    private void confirmAttendance(AttendanceBook attendanceBook) {
        String nickname = attendanceConfirmView.readCrewNickname();
        Crew crew = new Crew(nickname);
        attendanceBook.validateRegisteredCrew(crew);
        LocalDateTime dateTime = attendanceConfirmView.readAttendanceTime();
        AttendanceDateTime attendanceDateTime = new AttendanceDateTime(dateTime);
        attendanceBook.validateDuplicateAttendanceDate(crew, attendanceDateTime);
        attendanceBook.saveAttendanceDateTime(crew, attendanceDateTime);
        AttendanceStatus attendanceStatus = AttendanceStatusChecker.checkStatus(attendanceDateTime);
        attendanceConfirmView.printAttendanceResult(attendanceDateTime, attendanceStatus);
    }

    private void modifyAttendance(AttendanceBook attendanceBook) {
        String nickname = attendanceModifyView.readCrewNickname();
        Crew crew = new Crew(nickname);
        attendanceBook.validateRegisteredCrew(crew);
        int dayToModify = attendanceModifyView.readDayToModify();
        AttendanceDateTime originalDateTime = attendanceBook.findAttendanceDateTimeByCrewAndDay(crew, dayToModify);
        attendanceBook.removeAttendanceDateTime(crew, originalDateTime);
        LocalTime newTime = attendanceModifyView.readTimeToModify();
        AttendanceDateTime newDateTime = originalDateTime.changeTime(newTime);
        attendanceBook.saveAttendanceDateTime(crew, newDateTime);
        AttendanceStatus originalAttendanceStatus = AttendanceStatusChecker.checkStatus(originalDateTime);
        AttendanceStatus newAttendanceStatus = AttendanceStatusChecker.checkStatus(newDateTime);
        attendanceModifyView.printAttendanceModifyResult(originalDateTime, originalAttendanceStatus, newDateTime, newAttendanceStatus);
    }
}
