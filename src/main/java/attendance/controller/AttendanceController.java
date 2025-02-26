package attendance.controller;

import attendance.model.Crew;
import attendance.model.Crews;
import attendance.model.MenuOption;
import attendance.util.CSVReader;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AttendanceController {
    private static final Path path = Paths.get("src/main/resources/attendances.csv");

    private final Crews crews;
    private final InputView inputView;
    private final OutputView outputView;

    public AttendanceController() {
        this.crews = new Crews();
        this.inputView = new InputView();
        this.outputView = new OutputView();
    }

    public void start() {
        List<List<String>> data = CSVReader.readCSV(path);
        crews.initCrews(data);
        crews.initCrewsAttendance(data);
        run();
    }

    public void run() {
        MenuOption menuOption;
        while (!MenuOption.QUIT.equals(menuOption = readCommand())) {
            manageOption(menuOption);
        }
    }

    private void manageOption(MenuOption menuOption) {
        try {
            executeOption(menuOption);
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e);
            run();
        }
    }

    private MenuOption readCommand() {
        LocalDate today = LocalDate.now();
        String month = String.valueOf(today.getMonthValue());
        String date = String.valueOf(today.getDayOfMonth());
        String day = today.getDayOfWeek().getDisplayName(TextStyle.NARROW, Locale.KOREAN);
        return MenuOption.of(inputView.readCommand(month, date, day));
    }

    private void executeOption(MenuOption menuOption) {
        Map<MenuOption, Runnable> optionActions = Map.of(
                MenuOption.ATTEND_TODAY, this::attendToday,
                MenuOption.MODIFY_ATTENDANCE, this::modifyAttendance,
                MenuOption.SHOW_STATISTIC, this::showStatistic,
                MenuOption.CHECK_STATUS, this::checkStatus
        );

        optionActions.get(menuOption).run();
    }

    private void attendToday() {
        Crew crew = crews.findCrew(inputView.readCrewName());

        if (hasTodayAttendance(crew)) {
            throw new IllegalArgumentException("해당 크루는 이미 오늘 출석했습니다!");
        }

        String timeInfo = inputView.readAttendTime();
        validateTimeFormat(timeInfo);
        LocalTime attendTime = LocalTime.parse(timeInfo);

        crews.attendToday(crew, attendTime);
        outputView.printAttendMessage(crews.findTodayAttendance(crew));
    }

    private void modifyAttendance() {

    }

    private void showStatistic() {
        outputView.printCrewStatistic(crews.findCrew(inputView.readCrewName()));
    }

    private void checkStatus() {

    }

    private boolean hasTodayAttendance(Crew crew) {
        return crews.hasTodayAttendance(crew);
    }

    private void validateTimeFormat(String timeInfo) {
        final String TIME_PATTERN = "(2[0-3]|[01][0-9]):[0-5][0-9]";
        if (!timeInfo.matches(TIME_PATTERN)) {
            throw new IllegalArgumentException(("올바르지 않은 시간 형식을 입력했습니다."));
        }
    }

}
