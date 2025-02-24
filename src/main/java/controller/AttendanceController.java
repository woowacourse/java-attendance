package controller;

import domain.AttendTime;
import domain.AttendanceFileReader;
import domain.Crews;
import domain.December;
import view.InputView;
import view.OutputView;

import java.time.LocalDateTime;
import java.util.List;

import static domain.December.DEFAULT_MONTH;
import static domain.December.DEFAULT_YEAR;
import static domain.WarningStatusType.*;

public class AttendanceController {

    private final InputView inputView;
    private final OutputView outputView;

    public AttendanceController(final InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    List<String> students;
    Crews crews;
    public void run() {
        students = AttendanceFileReader.readFile("src/main/resources/attendances.csv");
        crews = new Crews(students);

        while (true) {
            String command = inputView.readCommand();

            try {
                if (command.equals("1")) {
                    executeAttendance();
                }
                if (command.equals("2")) {
                    executeChangingAttendance();
                }

                if (command.equals("3")) {
                    executePrintCrewAttendance();
                }

                if (command.equals("4")) {
                    executeWarningCrews();
                }

                if (command.equals("Q")) {
                    break;
                }
            } catch (Exception e) {
                outputView.printErrorMessage(e);
            }
        }
    }

    private void executeWarningCrews() {
        outputView.printDismissalCrews(
                crews.getDangerousCrews(DISMISSAL),
                crews.getDangerousCrews(INTERVIEW),
                crews.getDangerousCrews(WARNING)
        );
    }

    private void executePrintCrewAttendance() {
        String nickname = inputView.readNickname();
        outputView.printCrewAttendance(crews.findCrew(nickname).orElseThrow(() -> new IllegalArgumentException("없는 학생입니다.")), nickname);
    }

    private void executeChangingAttendance() {
        String nickname = inputView.readNickNameForChange();
        int date = inputView.readDateForChange();
        December.checkWeekday(LocalDateTime.of(DEFAULT_YEAR, DEFAULT_MONTH, date, 0, 0));

        String time = inputView.readTimeForChange();
        AttendTime attendTime = crews.deleteAttendance(nickname, date);

        outputView.printBeforeChangedCrewAttendance(attendTime);

        int year = attendTime.getAttendTime().getYear();
        int month = attendTime.getAttendTime().getMonthValue();
        int date2 = attendTime.getAttendTime().getDayOfMonth();

        String inputTime = String.format("%d-%d-%d %s", year, month, date2, time);
        crews.findCrew(nickname).orElseThrow(() -> new IllegalArgumentException("[Error] 없는 학생입니다.")).addAttendTime(inputTime);
        String status = crews.findCrew(nickname).orElseThrow(() -> new IllegalArgumentException("[Error] 없는 학생입니다.")).attend(inputTime);

        outputView.printChangedCrewAttendance(time, status);
    }

    private void executeAttendance() {
        December.checkWeekday(LocalDateTime.now());

        String nickname = inputView.readNickname();
        crews.ifFindNameAddTime(nickname);

        String time = inputView.readTime();
        crews.initializeAttendTime(nickname, time);

        AttendTime attendTime = crews.findCrew(nickname).orElseThrow(() -> new IllegalArgumentException("[Error] 없는 학생입니다."))
                .findAttendanceByDate(LocalDateTime.now().getDayOfMonth());

        outputView.printTodayAttendance(attendTime);
    }
}
