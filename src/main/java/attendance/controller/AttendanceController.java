package attendance.controller;

import attendance.domain.Attendance;
import attendance.domain.AttendanceDismissStatus;
import attendance.domain.AttendanceHistories;
import attendance.domain.AttendanceManager;
import attendance.domain.AttendanceStatuses;
import attendance.domain.CrewAttendanceHistory;
import attendance.domain.CrewName;
import attendance.domain.DateTimeFormatterWrapper;
import attendance.exception.AttendanceArgumentException;
import attendance.view.ConsoleInputView;
import attendance.view.OutputView;
import attendance.view.dto.AttendanceMethod;
import attendance.view.dto.AttendanceStatusCount;
import attendance.view.dto.ModifyAttendanceDto;
import attendance.view.dto.RequestModifyAttendanceDto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.function.Supplier;

public class AttendanceController {

    private final static OutputView outputView = new OutputView();
    private final static ConsoleInputView inputView = new ConsoleInputView();
    private final static String ATTENDANCE_AVAILABLE_MONTH = "12";
    private final static String ATTENDANCE_REQUEST_DATE_FORMAT = "%s %s ";
    private final static String ATTENDANCE_AVAILABLE_YEAR = "2024";
    private final static String NOT_SUPPORT_METHOD = "지원하지 않는 기능입니다.";
    private final AttendanceManager attendanceManager;

    public AttendanceController(AttendanceManager attendanceManager) {
        this.attendanceManager = attendanceManager;
    }

    public void start() {
        AttendanceMethod method = null;
        while (method != AttendanceMethod.QUIT) {
            method = inputAttendanceMethod();
            doMethod(method);
        }
    }

    private void doMethod(AttendanceMethod method) {
        switch (method) {
            case ATTENDANCE -> handleAttendance();
            case MODIFY -> handleModifyAttendance();
            case ATTENDANCE_HISTORY -> handleAttendanceHistory();
            case CREW_DISMISS_VIEW -> handleShowCrewDismiss();
            default -> throw new AttendanceArgumentException(NOT_SUPPORT_METHOD);
        }
    }

    private void handleAttendanceHistory() {
        CrewAttendanceHistory crewAttendanceHistory = requestAttendanceHistory();
        AttendanceStatuses attendanceStatuses = crewAttendanceHistory.attendanceStatuses();
        AttendanceDismissStatus attendanceDismissStatus = attendanceStatuses.calculateAttendanceDismiss();
        AttendanceStatusCount attendanceStatusCount = attendanceStatuses.attendanceStatusCount();
        AttendanceHistories attendanceHistories = crewAttendanceHistory.attendanceHistories();
        String nickname = crewAttendanceHistory.nickname();

        outputView.printAttendanceHistory(attendanceHistories.getImmutableAttendanceHistories(), nickname);
        outputView.printCrewDismisses(attendanceDismissStatus.getStatus(), attendanceStatusCount);
    }

    private CrewAttendanceHistory requestAttendanceHistory() {
        return handleRequest(() -> {
            String nickname = handleRequest(this::inputAttendanceHistoryNickname);
            return attendanceManager.crewAttendanceHistory(nickname);
        });
    }

    private void handleShowCrewDismiss() {
        List<CrewAttendanceHistory> attendanceHistories = handleRequest(attendanceManager::crewDismissHistory);
        StringBuilder crewDismissHistoryBuilder = new StringBuilder();
        appendCrewDismissHistory(attendanceHistories, crewDismissHistoryBuilder);
        outputView.printCrewDismisses(crewDismissHistoryBuilder.toString());
    }

    private void appendCrewDismissHistory(List<CrewAttendanceHistory> attendanceHistories,
                                          StringBuilder crewDismissHistoryBuilder) {
        for (CrewAttendanceHistory crewAttendanceHistory : attendanceHistories) {
            AttendanceStatuses attendanceStatuses = crewAttendanceHistory.attendanceStatuses();
            AttendanceDismissStatus attendanceDismissStatus = attendanceStatuses.calculateAttendanceDismiss();
            crewDismissHistoryBuilder.append(
                    outputView.crewDismiss(crewAttendanceHistory.nickname(), attendanceStatuses.attendanceStatusCount(),
                            attendanceDismissStatus.getStatus()));
        }
    }

    private void handleModifyAttendance() {
        RequestModifyAttendanceDto requestModifyAttendanceDto = inputModifyAttendance();
        var previousAttendance = getAttendance(requestModifyAttendanceDto.nickname(),
                requestModifyAttendanceDto.date());
        attendanceManager.modifyAttendance(requestModifyAttendanceDto);
        var afterAttendance = getAttendance(requestModifyAttendanceDto.nickname(), requestModifyAttendanceDto.date());
        ModifyAttendanceDto previousModifyAttendanceDto = new ModifyAttendanceDto(previousAttendance.getStatus(),
                LocalDateTime.of(requestModifyAttendanceDto.date(), previousAttendance.time()));
        ModifyAttendanceDto afterModifyAttendanceDto = new ModifyAttendanceDto(afterAttendance.getStatus(),
                LocalDateTime.of(requestModifyAttendanceDto.date(), afterAttendance.time()));
        outputView.printModifyAttendance(previousModifyAttendanceDto, afterModifyAttendanceDto);
    }

    private Attendance getAttendance(String nickname, LocalDate date) {
        return attendanceManager.findAttendance(nickname, date);
    }

    private RequestModifyAttendanceDto inputModifyAttendance() {
        String nickname = handleRequest(this::inputAttendanceExistNickname);
        LocalDate date = handleRequest(this::attendanceModifyDate);
        LocalTime time = handleRequest(this::inputAttendanceTime);
        RequestModifyAttendanceDto modifyAttendanceDto = new RequestModifyAttendanceDto(nickname, date, time);
        return modifyAttendanceDto;
    }


    private LocalDate attendanceModifyDate() {
        return handleRequest(() -> {
            outputView.printAttendanceModifyDateInput();
            String dateInput =
                    String.format(ATTENDANCE_REQUEST_DATE_FORMAT, ATTENDANCE_AVAILABLE_YEAR, ATTENDANCE_AVAILABLE_MONTH)
                            + inputView.input();
            LocalDate date = DateTimeFormatterWrapper.parsingAttendanceDate(dateInput);
            attendanceManager.validateIsAttendanceAvailable(date);
            return date;
        });
    }

    private String inputAttendanceExistNickname() {
        return handleRequest(() -> {
            outputView.printAttendanceModifyNicknameInput();
            String nickname = inputView.input();
            validateCrewName(nickname);
            return nickname;
        });
    }

    private void validateCrewName(String nickname) {
        CrewName.from(nickname);
    }

    private String inputAttendanceHistoryNickname() {
        return handleRequest(() -> {
            outputView.printNicknameInput();
            String nickname = inputView.input();
            validateCrewName(nickname);
            validateAttendanceExist(nickname);
            attendanceManager.validateAttendanceExist(nickname);
            return nickname;
        });
    }

    private void validateAttendanceExist(String nickname) {
        attendanceManager.validateAttendanceExist(nickname);
    }

    private void handleAttendance() {
        String nickname = handleRequest(this::inputAttendanceNickname);
        LocalTime inputTime = handleRequest(this::inputAttendanceTime);
        LocalDate attendanceDate = LocalDate.now();
        try {
            var attendance = addAttendance(nickname, attendanceDate, inputTime);
            outputView.printAttendanceResult(attendance.getStatus(), inputTime, attendanceDate);
        } catch (AttendanceArgumentException e) {
            outputView.println(e.getMessage());
        }
    }

    private Attendance addAttendance(String nickname, LocalDate attendanceDate, LocalTime inputTime) {
        try {
            attendanceManager.addAttendance(nickname, LocalDateTime.of(attendanceDate, inputTime));
            return getAttendance(nickname, attendanceDate);
        } catch (AttendanceArgumentException e) {
            throw new AttendanceArgumentException(
                    DateTimeFormatterWrapper.formattingAttendanceDateError(attendanceDate));
        }
    }

    private LocalTime inputAttendanceTime() {
        try {
            outputView.printAttendanceTimeInput();
            String inputTime = inputView.input();
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
        validateCrewName(nickname);
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
            outputView.println(e.getMessage());
            return handleRequest(inputSupplier);
        }
    }
}
