package controller;

import static domain.AttendanceStatus.DISMISSAL;
import static domain.AttendanceStatus.INTERVIEW;
import static domain.AttendanceStatus.WARNING;
import static domain.December.DEFAULT_MONTH;
import static domain.December.DEFAULT_YEAR;

import domain.AttendTime;
import domain.Crew;
import domain.Crews;
import domain.December;
import infrastructure.AttendanceFileReader;
import java.time.LocalDateTime;
import java.util.List;
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
        List<String> crewsOfFile = attendanceFileReader.readFile("src/main/resources/attendances.csv");
        Crews crews = new Crews();
        crews.loadCrews(crewsOfFile);

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
            System.out.println();
            System.out.println(e.getMessage());
            System.out.println();
        }
    }

    private void attendCrew(final Crews crews) {
        December.checkWeekday(LocalDateTime.now());

        String nickname = inputView.readNickname();
        Crew crew = crews.findByNickname(nickname);

        String time = inputView.readTime();
        crew.addAttendTime(time);

        AttendTime attendTime = crew.findAttendTimeByDate(LocalDateTime.now().getDayOfMonth());

        outputView.printTodayAttendance(attendTime);
    }

    private void modifyAttendanceTime(final Crews crews) {
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
        crews.findCrew(nickname).addAttendTime(inputTime);
        String status = crews.findCrew(nickname).attend(inputTime);

        outputView.printChangedCrewAttendance(time, status);
    }

    private void findAttendanceHistory(final Crews crews) {
        String nickname = inputView.readNickname();
        outputView.printCrewAttendance(crews.findCrew(nickname));
    }

    private void findDangerousCrews(final Crews crews) {
        outputView.printDismissalCrews(
                crews.getDangerousCrews(DISMISSAL),
                crews.getDangerousCrews(INTERVIEW),
                crews.getDangerousCrews(WARNING)
        );
    }

}
