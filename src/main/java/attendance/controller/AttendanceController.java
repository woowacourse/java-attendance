package attendance.controller;

import attendance.model.Attendance;
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

    // 1번, 출석하기 기능
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

    // 2번, 출석 수정 기능
    private void modifyAttendance() {
        Crew crew = crews.findCrew(inputView.readCrewName());
        LocalDate modifyDate = organizeCrewModifyDate(crew);
        LocalTime modifyTime = organizeCrewModifyTime(crew, modifyDate);

        Attendance originalAttendance = crews.findCrewAttendance(crew, modifyDate);
        crews.modifyAttendance(crew, modifyDate, modifyTime);
        Attendance newAttendance = crews.findCrewAttendance(crew, modifyDate);

        outputView.printModifyMessage(originalAttendance, newAttendance);
    }

    // 3번, 크루별 출석 기록 및 상태 조회 기능
    private void showStatistic() {
        outputView.printCrewStatistic(crews.findCrew(inputView.readCrewName()));
    }

    // 4번, 제적 위험자 조회 기능
    private void checkStatus() {

    }

    private LocalDate organizeCrewModifyDate(Crew crew) {
        String dateInfo = inputView.readModifyDate();
        validateDateFormat(dateInfo);
        LocalDate modifyDate = LocalDate.of(2025, 2, Integer.parseInt(dateInfo));
        if (isOverDate(modifyDate)) {
            throw new IllegalArgumentException("출석 수정은 오늘 기록까지만 가능합니다!");
        }
        if (isModifyTodayButNotAttend(crew, modifyDate)) {
            throw new IllegalArgumentException("오늘은 출석 기록이 없어서 수정이 안됩니다!");
        }
        return modifyDate;
    }

    private LocalTime organizeCrewModifyTime(Crew crew, LocalDate modifyDate) {
        String timeInfo = inputView.readModifyTime();
        validateTimeFormat(timeInfo);
        LocalTime modifyTime = LocalTime.parse(timeInfo);
        if (isSameTimeModify(crew, modifyDate, modifyTime)) {
            throw new IllegalArgumentException("같은 시간으로 출석 수정을 하고 있습니다!");
        }
        return modifyTime;
    }

    private boolean isOverDate(LocalDate modifyDate) {
        LocalDate today = LocalDate.now();
        return modifyDate.isAfter(today);
    }

    private boolean isModifyTodayButNotAttend(Crew crew, LocalDate modifyDate) {
        return LocalDate.now().isEqual(modifyDate)
                && !isCrewAttendToday(crew);
    }

    private boolean isCrewAttendToday(Crew crew) {
        return crews.hasTodayAttendance(crew);
    }

    private boolean isSameTimeModify(Crew crew, LocalDate modifyDate, LocalTime modifyTime) {
        return crews.findCrewAttendanceTime(crew, modifyDate).equals(modifyTime);
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

    private void validateDateFormat(final String date) {
        final String DATE_PATTERN = "^([1-2][0-8])|([1-9])$";
        if (!date.matches(DATE_PATTERN)) {
            throw new IllegalArgumentException("올바르지 않은 날짜 형식을 입력했습니다.");
        }
    }

}
