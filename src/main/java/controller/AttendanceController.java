package controller;

import domain.AttendanceManager;
import domain.AttendanceRecord;
import domain.AttendanceStatus;
import domain.Crew;
import domain.DateProvider;
import infrastructure.AttendanceFileReader;
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
        AttendanceStatus attendanceStatus = crew.getAttendanceStatus(dateProvider.getDate().getDayOfMonth());

        outputView.printAttendanceResult(attendanceTime, attendanceStatus);
    }

    private void modifyAttendanceTime(AttendanceManager attendanceManager) {
        String nickname = inputView.readNickname();
        Crew crew = attendanceManager.findCrewExactlyByNickname(nickname);
        AttendanceRecord attendanceRecord = crew.getAttendanceRecord();

        int modifyDay = inputView.readModifyDay();
        LocalTime modifyTime = inputView.readTime();

        LocalDateTime beforeAttendanceTime = attendanceRecord.findAttendanceTimeByDay(modifyDay);
        AttendanceStatus beforeAttendanceStatus = attendanceRecord.getAttendanceStatus(modifyDay);
        outputView.printAttendance(beforeAttendanceTime, beforeAttendanceStatus);

        attendanceRecord.modifyAttendanceTime(modifyDay, modifyTime);

        LocalDateTime modifiedAttendanceTime = attendanceRecord.findAttendanceTimeByDay(modifyDay);
        AttendanceStatus modifiedAttendanceStatus = attendanceRecord.getAttendanceStatus(modifyDay);
        outputView.printModifiedAttendance(modifiedAttendanceTime, modifiedAttendanceStatus);
    }
}
