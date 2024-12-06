package attendance.controller;

import static attendance.util.UserInputPaser.parseAttendanceTime;

import attendance.domain.Attendance;
import attendance.domain.AttendanceBook;
import attendance.domain.Crew;
import attendance.domain.FileReader;
import attendance.domain.dto.AttendanceResult;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
                    createAttendance(); break;
                case "2":
                    modifyAttendance(); break;
                case "3":
                    checkCrewAttendanceRecord();break;
                case "4":
                    checkExpelledCrews();break;
                case "Q":
                    closeProgram();break;
                default: break;
            }
        }

    }

    private void createAttendance() {
        String inputCrewName = inputView.inputCrew();
        Crew crew = new Crew(inputCrewName);

        String inputAttendanceTime = inputView.inputAttendanceTime();
        LocalDateTime localDateTime = parseAttendanceTime(inputAttendanceTime);
        Attendance attendance = attendanceBook.registerAttendance(crew, localDateTime);
        AttendanceResult attendanceResult = AttendanceResult.from(attendance);
        outputView.displayAttendanceResult(attendanceResult);

    }

    private void modifyAttendance() {
    }

    private void checkCrewAttendanceRecord() {
    }

    private void checkExpelledCrews() {
    }

    private void closeProgram() {
    }
}
