package controller;

import domain.AttendTime;
import domain.AttendanceFileReader;
import domain.Crews;
import domain.December;
import java.time.LocalDateTime;
import java.util.List;
import view.InputView;
import view.OutputView;

public class AttendanceController {

    private final InputView inputView;
    private final OutputView outputView;

    public AttendanceController(final InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        List<String> students = AttendanceFileReader.readFile("src/main/resources/attendances.csv");
        Crews crews = new Crews(students);

        while (true) {
            String command = inputView.readCommand();

            try {
                if (command.equals("1")) {
                    December.checkWeekday(LocalDateTime.now());

                    String nickname = inputView.readNickname();
                    String time = inputView.readTime();

                    crews.ifFindNameAddTime(nickname, time);

                    AttendTime attendTime = crews.findCrew(nickname)
                            .findAttendanceByDate(LocalDateTime.now().getDayOfMonth());

                    outputView.printTodayAttendance(attendTime);
                }

                if (command.equals("2")) {
                    String nickname = inputView.readNickNameForChange();
                    int date = inputView.readDateForChange();
                    December.checkWeekday(LocalDateTime.of(2024, 12, date, 0, 0));

                    String time = inputView.readTimeForChange();
                    AttendTime attendTime = crews.deleteAttendance(nickname, date);

                    outputView.printBeforeChangedCrewAttendance(attendTime);

                    int year = attendTime.getAttendTime().getYear();
                    int month = attendTime.getAttendTime().getMonthValue();
                    int date2 = attendTime.getAttendTime().getDayOfMonth();

                    String inputTime = String.format("%d-%d-%d %s", year, month, date2, time);
                    crews.findCrew(nickname).addAttendTime(inputTime);
                    String status = crews.findCrew(nickname).attend(inputTime);

                    outputView.printChangedCrewAttendance(time, status);
                }

                if (command.equals("3")) {
                    String nickname = inputView.readNickname();
                    System.out.printf("이번 달 %s의 출석 기록입니다.%n", nickname);
                    System.out.println();
                    outputView.printCrewAttendance(crews.findCrew(nickname));
                }

                if (command.equals("4")) {
                    outputView.printDismissalCrews(
                            crews.getDangerousCrews("제적"),
                            crews.getDangerousCrews("면담"),
                            crews.getDangerousCrews("경고")
                    );
                }

                if (command.equals("Q")) {
                    break;
                }
            } catch (Exception e) {
                System.out.println();
                System.out.println(e.getMessage());
                System.out.println();
            }
        }

    }

}
