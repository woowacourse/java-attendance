package controller;

import static constant.Selection.ADD;
import static constant.Selection.EDIT;
import static constant.Selection.FIND_EXPULSION;
import static constant.Selection.FIND_HISTORY;
import static constant.Selection.QUIT;

import constant.Selection;
import domain.AttendanceAnalyze;
import domain.AttendanceHistory;
import domain.AttendanceHistory.AttendanceValidator;
import domain.AttendanceResult;
import domain.Crews;
import domain.Username;
import dto.AttendanceHistoriesDto;
import dto.EditResponseDto;
import dto.ExpulsionCrewsDto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import util.DateFormatter;
import util.FileReader;
import view.InputView;
import view.OutputView;

public class AttendanceController {

    private final OutputView outputView;
    private final InputView inputView;

    public AttendanceController(OutputView outputView, InputView inputView) {
        this.outputView = outputView;
        this.inputView = inputView;
    }

    public void start() {
        Crews crews = initializeCrews();
        Selection selection;
        while (!(selection = retryAboutInvalidInput(this.inputView::getSelection)).equals(QUIT)) {
            retryAboutInvalidInput(this::startAttendanceService, selection, crews);
        }
    }

    private void startAttendanceService(Selection selection, Crews crews) {
        if (selection.equals(ADD)) {
            addAttendanceHistory(crews);
        }
        if (selection.equals(EDIT)) {
            editAttendanceHistory(crews);
        }
        if (selection.equals(FIND_HISTORY)) {
            showAttendanceHistories(crews);
        }
        if (selection.equals(FIND_EXPULSION)) {
            showExpulsionCrews(crews);
        }
    }

    private void showExpulsionCrews(Crews crews) {
        LocalDate now = DateFormatter.getTodayDate();
        Map<Username, AttendanceAnalyze> expulsionCrews = crews.findExpulsionCrews(now);
        outputView.printExpulsionCrews(ExpulsionCrewsDto.from(expulsionCrews));
    }

    private void showAttendanceHistories(Crews crews) {
        String username = inputView.getUsername();
        LocalDate now = DateFormatter.getTodayDate();
        AttendanceAnalyze attendanceAnalyze = crews.getAttendanceAnalyze(username, now);
        outputView.printAttendanceHistories(
                AttendanceHistoriesDto.of(username, attendanceAnalyze));
    }

    private void editAttendanceHistory(Crews crews) {
        String username = inputView.getUsernameForEditHistory();
        crews.validateHasCrew(username);
        LocalDate attendDate = inputView.getEditDay();
        AttendanceValidator.validateAttendanceDate(attendDate);
        AttendanceHistory beforeHistory = crews.findAttendanceHistory(username, attendDate);
        LocalTime attendTime = inputView.getEditTime();
        LocalDateTime attendanceTime = LocalDateTime.of(attendDate, attendTime);
        AttendanceResult result = crews.editAttendanceHistory(username, attendanceTime);
        outputView.printEditResult(EditResponseDto.of(beforeHistory, attendanceTime, result));
    }

    private void addAttendanceHistory(Crews crews) {
        LocalDate now = DateFormatter.getTodayDate();
        AttendanceValidator.validateAttendanceDate(now);
        String username = inputView.getUsername();
        crews.validateHasCrew(username);
        LocalDateTime attendanceTime = inputView.getAttendanceTime();
        AttendanceResult result = crews.addAttendanceHistory(username, attendanceTime);
        outputView.printAddAttendanceResult(attendanceTime, result.getResult());
    }

    private Crews initializeCrews() {
        Map<String, List<LocalDateTime>> histories = FileReader.getCrewsAttendanceHistories();
        LocalDate now = DateFormatter.getTodayDate();
        return new Crews(histories, now);
    }

    private <T> T retryAboutInvalidInput(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private <T, U> void retryAboutInvalidInput(BiConsumer<T, U> biConsumer, T arg1, U arg2) {
        try {
            biConsumer.accept(arg1, arg2);
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
        }
    }
}
