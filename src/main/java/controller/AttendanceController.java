package controller;

import domain.AttendanceManager;
import domain.AttendanceRecord;
import domain.AttendanceStatus;
import domain.Crew;
import domain.DateProvider;
import infrastructure.file.AttendanceFileReader;
import java.time.LocalDateTime;
import java.time.LocalTime;
import view.InputView;
import view.OutputView;

public class AttendanceController {

    private final InputView inputView;
    private final OutputView outputView;
    private final DateProvider dateProvider;

    public AttendanceController(InputView inputView, OutputView outputView, DateProvider dateProvider) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.dateProvider = dateProvider;
    }

    public void run(AttendanceFileReader attendanceFileReader) {
        AttendanceManager attendanceManager = new AttendanceManager(dateProvider);
        attendanceFileReader.readFiles(attendanceManager);

        String command;
        do {
            command = inputView.readCommand(dateProvider.getDate());
            execute(command, attendanceManager);
        } while (!command.equals("Q"));
    }

    private void execute(String command, AttendanceManager attendanceManager) {
        try {
            switch (command) {
                case "1" -> attend(attendanceManager);
                case "2" -> modifyAttendanceTime(attendanceManager);
                case "3" -> findAttendanceRecord(attendanceManager);
                case "4" -> findDangerousCrews(attendanceManager);
                default -> throw new IllegalArgumentException("[ERROR] 올바른 명령어를 입력해주세요.");
            }
        } catch (Exception e) {
            outputView.printErrorMessage(e.getMessage());
        }
    }


    private void attend(AttendanceManager attendanceManager) {
        String nickname = inputView.readNickname();
        Crew crew = attendanceManager.findCrewExactlyByNickname(nickname);

        LocalTime time = inputView.readTime();
        LocalDateTime attendanceTime = crew.attend(time);
        AttendanceStatus attendanceStatus = crew.getAttendanceStatus(attendanceTime.getDayOfMonth());

        outputView.printAttendanceResult(attendanceTime, attendanceStatus);
    }

    private void modifyAttendanceTime(AttendanceManager attendanceManager) {
        String nickname = inputView.readNickname();
        AttendanceRecord attendanceRecord = attendanceManager.findAttendanceRecordByNickname(nickname);

        int modifyDay = inputView.readModifyDay();
        LocalTime modifyTime = inputView.readTime();

        outputView.printAttendance(
                attendanceRecord.findAttendanceTimeByDay(modifyDay),
                attendanceRecord.getAttendanceStatus(modifyDay));

        attendanceRecord.modifyAttendanceTime(modifyDay, modifyTime);

        outputView.printModifiedAttendance(
                attendanceRecord.findAttendanceTimeByDay(modifyDay),
                attendanceRecord.getAttendanceStatus(modifyDay));
    }

    private void findAttendanceRecord(AttendanceManager attendanceManager) {
        String nickname = inputView.readNickname();
        AttendanceRecord attendanceRecord = attendanceManager.findAttendanceRecordByNickname(nickname);

        outputView.printAttendanceRecord(nickname, attendanceRecord, dateProvider.getDate());
    }

    private void findDangerousCrews(AttendanceManager attendanceManager) {
        outputView.printDangerousCrews(attendanceManager.getCrews());
    }
}
