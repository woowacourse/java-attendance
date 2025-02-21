package attendance;

import attendance.config.AppConfig;
import attendance.controller.AttendanceController;
import attendance.domain.AttendanceMethod;
import attendance.domain.RequestParser;
import attendance.domain.dto.AttendanceHistoryDto;
import attendance.exception.AttendanceArgumentException;
import attendance.view.ConsoleInputView;
import attendance.view.OutputView;
import java.util.EnumSet;
import java.util.function.Supplier;

public class Application {

    private final static OutputView outputView = new OutputView();
    private final static ConsoleInputView inputView = new ConsoleInputView();
    private final static AppConfig appConfig = new AppConfig();
    private final static RequestParser requestParser = new RequestParser();
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
            AttendanceHistoryDto attendanceHistory = requestParser.parseAttendanceNickname(nickname);
            return attendanceController.attendanceHistory(attendanceHistory);
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
        String date = handleInput(() -> attendanceModifyDate());
        String time = handleInput(() -> attendanceTime());
        String result = attendanceController.attendanceModify(
                requestParser.parseAttendanceModifyRequest(nickname, time, date));
        outputView.println(result);
    }

    private static String attendanceModifyDate() {
        return handleInput(() -> {
            outputView.printAttendanceModifyDateInput();
            String date = ATTENDANCE_MONTH + inputView.input();
            attendanceController.validateDate(requestParser.parseDateValidateRequest(date));
            return date;
        });
    }

    private static String attendanceModifyNickname() {
        return handleInput(() -> {
            outputView.printAttendanceModifyNicknameInput();
            String nickname = inputView.input();
            attendanceController.validateNickname(requestParser.parseNicknameValidateRequest(nickname));
            return nickname;
        });
    }

    private static void attendance() {
        String nickname = handleInput(() -> attendanceNickname());
        String time = handleInput(() -> attendanceTime());
        String result = attendanceController.attendance(requestParser.parseAttendanceRequest(nickname, time));
        outputView.println(result);
    }

    private static String attendanceTime() {
        outputView.printAttendanceTimeInput();
        String time = inputView.input();
        attendanceController.validateTime(requestParser.parseTimeValidateRequest(time));
        return time;
    }

    private static String attendanceNickname() {
        outputView.printNicknameInput();
        String nickname = inputView.input();
        attendanceController.validateNickname(requestParser.parseNicknameValidateRequest(nickname));
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
