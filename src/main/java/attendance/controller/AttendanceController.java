package attendance.controller;

import attendance.domain.Attendance;
import attendance.domain.AttendanceBook;
import attendance.domain.AttendanceStatus;
import attendance.domain.Crew;
import attendance.domain.FileReader;
import attendance.domain.dto.AttendanceResult;
import attendance.domain.dto.AttendanceStatusResult;
import attendance.domain.dto.ModifyAttendanceResult;
import attendance.util.UserInputParser;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.LocalDateTime;
import java.util.List;

public class AttendanceController {
    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();
    private final FileReader fileReader = new FileReader();
    private final AttendanceBook attendanceBook = fileReader.readCSV();

    public void run() {
        while (true) {
            String userChoice = inputView.displayMainMenu();
            switch (userChoice) {
                case "1":
                    createAttendance();
                case "2":
                    modifyAttendance();
                case "3":
                    checkCrewAttendanceRecord();
                case "4":
                    checkExpelledCrews();
                case "Q":
                    closeProgram();
                default: break;
            }
        }

    }

    private void createAttendance() {
        String inputCrewName = inputView.inputAttendanceCrew();
        Crew crew = new Crew(inputCrewName);

        String inputAttendanceDateTime = inputView.inputAttendanceDateTime();
        LocalDateTime attendanceDateTime = UserInputParser.parseAttendanceTime(inputAttendanceDateTime);
        Attendance attendance = attendanceBook.registerAttendance(crew, attendanceDateTime);
        AttendanceResult attendanceResult = AttendanceResult.from(attendance);
        outputView.displayAttendanceResult(attendanceResult);
    }

    private void modifyAttendance() {
        String inputCrewName = inputView.inputModifyCrew();
        Crew crew = new Crew(inputCrewName);

        String inputModifyDate = inputView.inputModifyDate();
        String inputModifyTime = inputView.inputModifyTime();
        LocalDateTime localDateTime = attendance.util.UserInputParser.parseModifyDateTime(inputModifyDate,inputModifyTime);

        List<Attendance> attendances = attendanceBook.modifyAttendance(crew, localDateTime);
        ModifyAttendanceResult modifyAttendanceResult = ModifyAttendanceResult.of(attendances);
        outputView.displayModifyResult(modifyAttendanceResult);
    }

    private void checkCrewAttendanceRecord() {
        String inputCrewName = inputView.inputCheckCrew();
        Crew crew = new Crew(inputCrewName);

        List<Attendance> attendances = attendanceBook.checkAttendancesRecord(crew);
        AttendanceStatus attendanceStatus = attendanceBook.checkAttendanceCrewStatus(crew);
        List<AttendanceResult> attendanceResults = attendances.stream()
            .map(AttendanceResult::from)
            .toList();
        AttendanceStatusResult attendanceStatusResult = new AttendanceStatusResult(
            attendanceStatus.getAttendanceCount(),
            attendanceStatus.getLateCount(),
            attendanceStatus.getAbsentCount(),
            attendanceStatus.getSubjectStatus()
            );

        outputView.displayAttendanceRecord(attendanceResults,attendanceStatusResult);
    }

    private void checkExpelledCrews() {
        
    }

    private void closeProgram() {
    }
}
