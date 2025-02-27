package controller;

import static domain.DangerousStatus.DISMISSAL;
import static domain.DangerousStatus.INTERVIEW;
import static domain.DangerousStatus.WARNING;
import static domain.December.DEFAULT_MONTH;
import static domain.December.DEFAULT_YEAR;

import domain.AttendTime;
import domain.Crew;
import domain.Crews;
import domain.December;
import infrastructure.AttendanceFileReader;
import java.time.LocalDateTime;
import view.InputView;
import view.OutputView;

public class AttendanceController {

    private final InputView inputView;
    private final OutputView outputView;

    public AttendanceController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run(AttendanceFileReader attendanceFileReader) {
        Crews crews = attendanceFileReader.readFile("src/main/resources/attendances.csv");

        String command;
        do {
            command = inputView.readCommand();
            execute(command, crews);
        } while (!command.equals("Q"));
    }

    private void execute(String command, Crews crews) {
        try {
            switch (command) {
                case "1" -> attendCrew(crews);
                case "2" -> modifyAttendanceTime(crews);
                case "3" -> findAttendanceHistory(crews);
                case "4" -> findDangerousCrews(crews);
                default -> throw new IllegalArgumentException("[ERROR] 올바른 명령어를 입력해주세요.");
            }
        } catch (Exception e) {
            outputView.printErrorMessage(e.getMessage());
        }
    }

    private void attendCrew(final Crews crews) {
        December.checkWeekday(LocalDateTime.now());

        String nickname = inputView.readNickname();
        Crew crew = crews.findByNickname(nickname);

        String time = inputView.readTime();
        crew.addAttendTime(time);

        AttendTime attendTime = crew.findAttendTimeByDate(LocalDateTime.now().getDayOfMonth());
        String attendanceStatus = attendTime.checkAttendanceStatus();
        outputView.printTodayAttendance(attendTime, attendanceStatus);
    }

    private void modifyAttendanceTime(final Crews crews) {
        String nickname = inputView.readNickNameForChange();
        Crew crew = crews.findByNickname(nickname);
        int date = inputView.readDateForChange();
        December.checkWeekday(LocalDateTime.of(DEFAULT_YEAR, DEFAULT_MONTH, date, 0, 0));
        String time = inputView.readTimeForChange();

        AttendTime beforeAttendTime = crew.findAttendTimeByDate(date);
        String beforeAttendanceStatus = beforeAttendTime.checkAttendanceStatus();
        outputView.printBeforeAttendTime(beforeAttendTime, beforeAttendanceStatus);

        AttendTime modifiedAttendTime = beforeAttendTime.modifyAttendTime(time);
        String modifiedAttendanceStatus = modifiedAttendTime.checkAttendanceStatus();
        outputView.printModifiedAttendTime(modifiedAttendTime, modifiedAttendanceStatus);
    }

    private void findAttendanceHistory(final Crews crews) {
        String nickname = inputView.readNickname();
        outputView.printCrewAttendance(crews.findByNickname(nickname));
    }

    private void findDangerousCrews(final Crews crews) {
        outputView.printDismissalCrews(
                crews.getDangerousCrews(DISMISSAL),
                crews.getDangerousCrews(INTERVIEW),
                crews.getDangerousCrews(WARNING)
        );
    }

}
