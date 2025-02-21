package attendance;

import attendance.config.AppConfig;
import attendance.controller.AttendanceController;
import attendance.domain.AttendanceMethod;
import attendance.domain.DateTimeFormatterWrapper;
import attendance.exception.AttendanceArgumentException;
import attendance.view.ConsoleInputView;
import attendance.view.OutputView;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.EnumSet;
import java.util.function.Supplier;

public class Application {

    private final static OutputView outputView = new OutputView();
    private final static ConsoleInputView inputView = new ConsoleInputView();
    private final static AppConfig appConfig = new AppConfig();
    private final static AttendanceController attendanceController = appConfig.attendanceController();
    private final static EnumSet<AttendanceMethod> SUPPORTED_METHODS =
            EnumSet.of(AttendanceMethod.ATTENDANCE, AttendanceMethod.MODIFY,
                    AttendanceMethod.ATTENDANCE_HISTORY, AttendanceMethod.CREW_DISMISS_VIEW);
    private final static String ATTENDANCE_MONTH = "2024 12 ";
    private final static String NOT_SUPPORT_METHOD = "지원하지 않는 기능입니다.";

    public static void main(String[] args) {
        AttendanceMethod method = null;
        while (method != AttendanceMethod.QUIT) {
            method = inputAttendanceMethod();
            doMethod(method);
        }
    }

    private static void doMethod(AttendanceMethod method) {
        if (!SUPPORTED_METHODS.contains(method)) {
            throw new AttendanceArgumentException(NOT_SUPPORT_METHOD);
        }

        switch (method) {
            case ATTENDANCE -> attendance();
            case MODIFY -> modifyAttendance();
            case ATTENDANCE_HISTORY -> attendanceHistory();
            case CREW_DISMISS_VIEW -> showCrewDismiss();
        }
    }

    private static void attendanceHistory() {
        String result = handleInput(() -> {
            outputView.printNicknameInput();
            String nickname = inputView.input();
            return attendanceController.attendanceHistory(nickname);
        });
        outputView.println(result);
    }

    private static void showCrewDismiss() {
        String result = handleInput(() -> {
            return attendanceController.crewDismiss();
        });
        outputView.println(result);
    }

    private static void modifyAttendance() {
        String nickname = handleInput(() -> attendanceModifyNickname());
        LocalDate date = handleInput(() -> attendanceModifyDate());
        LocalTime time = handleInput(() -> attendanceTime());
        String result = attendanceController.attendanceModify(nickname, date, time);
        outputView.println(result);
    }

    private static LocalDate attendanceModifyDate() {
        return handleInput(() -> {
            outputView.printAttendanceModifyDateInput();
            String dateInput = ATTENDANCE_MONTH + inputView.input();
            LocalDate date = DateTimeFormatterWrapper.parsingAttendanceDate(dateInput);
            attendanceController.validateDate(date);
            return date;
        });
    }

    private static String attendanceModifyNickname() {
        return handleInput(() -> {
            outputView.printAttendanceModifyNicknameInput();
            String nickname = inputView.input();
            attendanceController.validateNickname(nickname);
            return nickname;
        });
    }

    private static void attendance() {
        String nickname = handleInput(() -> attendanceNickname());
        LocalTime time = handleInput(() -> attendanceTime());
        String result = attendanceController.attendance(nickname, LocalDateTime.of(LocalDate.now(), time));
        outputView.println(result);
    }

    private static LocalTime attendanceTime() {
        outputView.printAttendanceTimeInput();
        String inputTime = inputView.input();
        LocalTime time = DateTimeFormatterWrapper.parsingAttendanceTime(inputTime);
        attendanceController.validateTime(time);
        return time;
    }

    private static String attendanceNickname() {
        outputView.printNicknameInput();
        String nickname = inputView.input();
        attendanceController.validateNickname(nickname);
        return nickname;
    }

    private static AttendanceMethod inputAttendanceMethod() {
        return handleInput(() -> {
            outputView.printMethod();
            String method = inputView.input();
            return AttendanceMethod.of(method);
        });
    }

    private static <T> T handleInput(Supplier<T> inputSupplier) {
        try {
            return inputSupplier.get();
        } catch (AttendanceArgumentException e) {
            outputView.printError(e.getMessage());
            return handleInput(inputSupplier);
        }
    }
}
