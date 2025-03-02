package controller;

import domain.AttendTime;
import domain.Command;
import domain.Crew;
import domain.Crews;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import utils.CrewAttendanceFileReader;
import view.InputView;
import view.OutputView;

public class AttendanceController {

    public static final String TODAY_LOCAL_DATE = "2024-12-16";
    public static final DateTimeFormatter TODAY_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final String ERROR_MESSAGE = "[ERROR]";

    private final InputView inputView;
    private final OutputView outputView;

    public AttendanceController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {

        Crews crews = new Crews(CrewAttendanceFileReader.readFile("src/main/resources/attendances.csv"));
        while (true) {
            Command command = inputView.getCommand();
            try {
                if (command.equals(Command.ATTEND_TODAY)) {
                    attendToday(crews);
                }
                if (command.equals(Command.CHANGE_ATTENDANCE)) {
                    changeAttendance(crews);
                }
                if (command.equals(Command.SHOW_CREW_ATTENDANCES)) {
                    showCrewAttendances(crews);
                }
                if (command.equals(Command.SHOW_DISMISSAL_CREW)) {
                    showDismissalCrew(crews);
                }
                if (command.equals(Command.QUIT)) {
                    return;
                }
            } catch (Exception e) {
                System.out.println(ERROR_MESSAGE + e.getMessage());
            }
        }
    }

    private void attendToday(Crews crews) {
        String nickname = inputView.getNickname();
        String localDateTimeToday = inputView.getTodayLocalDateTime();
        AttendTime attendTime = crews.addCrewAttendance(nickname, localDateTimeToday);
        outputView.printAttendanceResult(attendTime);
    }

    private void changeAttendance(Crews crews) {
        String nickname = inputView.getNickname();
        int date = inputView.getChangeableDate();
        LocalTime localTime = inputView.getChangeableTime();

        Crew crew = crews.findCrewByNickname(nickname).orElseThrow(IllegalArgumentException::new);
        outputView.printBeforeChangedAttendance(crew, date);
        outputView.printAfterChangedAttendance(crew.changeAttendanceTime(date, localTime));
    }

    private void showCrewAttendances(Crews crews) {
        String nickname = inputView.getNickname();
        Crew crew = crews.findCrewByNickname(nickname).orElseThrow(() -> new IllegalArgumentException("없는 사용자입니다."));
        outputView.printAttendanceTimeLine(crew);
    }

    private void showDismissalCrew(Crews crews) {
        outputView.printDismissalCrews(crews.findDismissalCrewsByImportance());
    }
}
