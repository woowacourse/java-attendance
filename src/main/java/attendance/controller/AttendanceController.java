package attendance.controller;

import attendance.domain.Attendance;
import attendance.domain.AttendanceDismissStatus;
import attendance.domain.AttendanceHistory;
import attendance.domain.AttendanceManager;
import attendance.domain.AttendanceMethod;
import attendance.domain.AttendanceStatus;
import attendance.domain.DateTimeFormatterWrapper;
import attendance.exception.AttendanceArgumentException;
import attendance.validation.AttendanceInputValidator;
import attendance.view.ConsoleInputView;
import attendance.view.OutputView;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class AttendanceController {

    private final AttendanceManager attendanceManager;
    private final AttendanceInputValidator attendanceInputValidator;

    private final static OutputView outputView = new OutputView();
    private final static ConsoleInputView inputView = new ConsoleInputView();
    private final static EnumSet<AttendanceMethod> SUPPORTED_METHODS =
            EnumSet.of(AttendanceMethod.ATTENDANCE, AttendanceMethod.MODIFY,
                    AttendanceMethod.ATTENDANCE_HISTORY, AttendanceMethod.CREW_DISMISS_VIEW);
    private final static String ATTENDANCE_MONTH = "2024 12 ";
    private final static String NOT_SUPPORT_METHOD = "지원하지 않는 기능입니다.";

    public AttendanceController(AttendanceManager attendanceManager,
                                AttendanceInputValidator attendanceInputValidator) {
        this.attendanceManager = attendanceManager;
        this.attendanceInputValidator = attendanceInputValidator;
    }

    public void start() {
        AttendanceMethod method = null;
        while (method != AttendanceMethod.QUIT) {
            method = inputAttendanceMethod();
            doMethod(method);
        }
    }

    private void doMethod(AttendanceMethod method) {
        if (!SUPPORTED_METHODS.contains(method)) {
            throw new AttendanceArgumentException(NOT_SUPPORT_METHOD);
        }

        switch (method) {
            case ATTENDANCE -> handleAttendance();
            case MODIFY -> handleModifyAttendance();
            case ATTENDANCE_HISTORY -> handleAttendanceHistory();
            case CREW_DISMISS_VIEW -> handleShowCrewDismiss();
        }
    }

    private void handleAttendanceHistory() {
        AttendanceHistory attendanceHistory = requestAttendanceHistory();
        Map<String, Integer> status = attendanceHistory.statusMap();
        int absenceCount = AttendanceStatus.absenceCount(status);
        AttendanceDismissStatus attendanceDismissStatus = AttendanceDismissStatus.calculateAttendanceDismiss(
                absenceCount, AttendanceStatus.lateCount(status));
        outputView.printAttendanceHistory(attendanceHistory.nickname(), attendanceHistory.attendanceHistories(),
                AttendanceStatus.lateCount(status), AttendanceStatus.attendanceCount(status),
                AttendanceStatus.lateCount(status), attendanceDismissStatus.getStatus());
    }

    private AttendanceHistory requestAttendanceHistory() {
        return handleRequest(() -> {
            String nickname = handleRequest(this::inputAttendanceHistoryNickname);
            return attendanceManager.crewAttendanceHistory(nickname);
        });
    }

    private void handleShowCrewDismiss() {
        List<AttendanceHistory> attendanceHistories = handleRequest(attendanceManager::crewDismissHistory);
        StringBuilder crewDismissHistoryBuilder = new StringBuilder();
        appendCrewDismissHistory(attendanceHistories, crewDismissHistoryBuilder);
        outputView.printCrewDismisses(crewDismissHistoryBuilder.toString());
    }

    private void appendCrewDismissHistory(List<AttendanceHistory> attendanceHistories,
                                          StringBuilder crewDismissHistoryBuilder) {

        for (AttendanceHistory attendanceHistory : attendanceHistories) {
            Map<String, Integer> status = attendanceHistory.statusMap();
            AttendanceDismissStatus attendanceDismissStatus = AttendanceDismissStatus
                    .calculateAttendanceDismiss(AttendanceStatus.absenceCount(status),
                            AttendanceStatus.lateCount(status));
            crewDismissHistoryBuilder.append(
                    outputView.crewDismiss(attendanceHistory.nickname(), AttendanceStatus.absenceCount(status),
                            AttendanceStatus.lateCount(status), attendanceDismissStatus.getStatus()));
        }
    }

    private void handleModifyAttendance() {
        String nickname = handleRequest(this::inputAttendanceExistNickname);
        LocalDate date = handleRequest(this::attendanceModifyDate);
        LocalTime time = handleRequest(this::inputAttendanceTime);
        var previousAttendance = handleRequest(() -> attendanceManager.getAttendance(nickname, date));
        attendanceManager.modifyAttendance(nickname, date, time);
        var afterAttendance = handleRequest(() -> attendanceManager.getAttendance(nickname, date));
        outputView.printModifyAttendance(previousAttendance.attendanceStatus().getStatus(),
                LocalDateTime.of(date, previousAttendance.time()),
                afterAttendance.attendanceStatus().getStatus(), afterAttendance.time());
    }

    private LocalDate attendanceModifyDate() {
        return handleRequest(() -> {
            outputView.printAttendanceModifyDateInput();
            String dateInput = ATTENDANCE_MONTH + inputView.input();
            LocalDate date = DateTimeFormatterWrapper.parsingAttendanceDate(dateInput);
            attendanceManager.validateIsAttendanceAvailable(date);
            return date;
        });
    }

    private String inputAttendanceExistNickname() {
        return handleRequest(() -> {
            outputView.printAttendanceModifyNicknameInput();
            String nickname = inputView.input();
            attendanceInputValidator.validateAttendanceNickname(nickname);
            attendanceManager.validateAttendanceExist(nickname);
            return nickname;
        });
    }

    private String inputAttendanceHistoryNickname() {
        return handleRequest(() -> {
            outputView.printNicknameInput();
            String nickname = inputView.input();
            attendanceInputValidator.validateAttendanceNickname(nickname);
            attendanceManager.validateAttendanceExist(nickname);
            return nickname;
        });
    }

    private void handleAttendance() {
        String nickname = handleRequest(this::inputAttendanceNickname);
        LocalTime inputTime = handleRequest(this::inputAttendanceTime);
        LocalDate attendanceDate = LocalDate.now();
        try {
            var attendance = addAttendance(nickname, attendanceDate, inputTime);
            outputView.printAttendanceResult(attendance.attendanceStatus()
                    .getStatus(), inputTime, attendanceDate);
        } catch (AttendanceArgumentException e) {
            outputView.printError(e.getMessage());
        }
    }

    private Attendance addAttendance(String nickname, LocalDate attendanceDate, LocalTime inputTime) {
        try {
            attendanceManager.addAttendance(nickname, LocalDateTime.of(attendanceDate, inputTime));
            return attendanceManager.getAttendance(nickname, attendanceDate);
        } catch (AttendanceArgumentException e) {
            throw new AttendanceArgumentException(
                    DateTimeFormatterWrapper.formattingAttendanceDateError(attendanceDate));
        }
    }

    private LocalTime inputAttendanceTime() {
        try {
            outputView.printAttendanceTimeInput();
            String inputTime = inputView.input();
            attendanceInputValidator.validateAttendanceTime(inputTime);
            LocalTime time = DateTimeFormatterWrapper.parsingAttendanceTime(inputTime);
            attendanceManager.validateIsSchoolOpen(time);
            return time;
        } catch (AttendanceArgumentException e) {
            throw e;
        }
    }

    private String inputAttendanceNickname() {
        outputView.printNicknameInput();
        String nickname = inputView.input();
        attendanceInputValidator.validateAttendanceNickname(nickname);
        return nickname;
    }

    private AttendanceMethod inputAttendanceMethod() {
        return handleRequest(() -> {
            outputView.printMethod();
            String method = inputView.input();
            return AttendanceMethod.of(method);
        });
    }

    private <T> T handleRequest(Supplier<T> inputSupplier) {
        try {
            return inputSupplier.get();
        } catch (AttendanceArgumentException e) {
            outputView.printError(e.getMessage());
            return handleRequest(inputSupplier);
        }
    }
}
