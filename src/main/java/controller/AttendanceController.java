package controller;

import static constant.PathConstant.ATTENDANCE_FILE_PATH;

import dto.AttendanceCheckInRequest;
import dto.AttendanceCheckInResponse;
import dto.AttendanceHistoryRequest;
import dto.AttendanceHistoryResponse;
import dto.AttendanceOptionRequest;
import dto.AttendanceRiskCrewsResponse;
import dto.AttendanceUpdateRequest;
import dto.AttendanceUpdateResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Supplier;
import model.Attendance;
import model.Attendances;
import model.Option;
import util.FileParser;
import view.InputView;
import view.OutputView;

public class AttendanceController {

    public void run() {
        Attendances attendances = initialize();

        while (true) {
            Option option = processWithRetry(this::selectOption);

            if (option.equals(Option.ONE)) {
                processWithRetry(() -> checkInAttendance(attendances));
            }
            if (option.equals(Option.TWO)) {
                processWithRetry(() -> updateAttendance(attendances));
            }
            if (option.equals(Option.THREE)) {
                processWithRetry(() -> findAttendanceHistoryByCrew(attendances));
            }
            if (option.equals(Option.FOUR)) {
                processWithRetry(() -> findRiskCrews(attendances));
            }
            if (option.equals(Option.QUIT)) {
                break;
            }
        }
    }

    public Attendances initialize() {
        List<String> lines = FileParser.readLines(ATTENDANCE_FILE_PATH.getPath());
        return Attendances.from(lines, LocalDate.now());
    }

    private Option selectOption() {
        AttendanceOptionRequest request = InputView.readAttendanceOptionRequest(LocalDate.now());
        return Option.find(request.option());
    }

    private void checkInAttendance(Attendances attendances) {
        LocalDate now = LocalDate.now();

        AttendanceCheckInRequest request = InputView.readAttendanceCheckInRequest();
        Attendance attendance = attendances.add(request.nickname(), request.checkInTime(), now);
        AttendanceCheckInResponse response = AttendanceCheckInResponse.convertToAttendanceCheckInResponse(
                attendance.getCheckInDate(),
                attendance.getCheckInTime(),
                attendance.getAttendanceType());
        OutputView.printCheckInAttendance(response);
    }

    private void updateAttendance(Attendances attendances) {
        LocalDate now = LocalDate.now();

        AttendanceUpdateRequest request = InputView.readAttendanceUpdateRequest(now);
        Attendance previousAttendance = attendances.find(request.nickname(), request.day(), now).copy();
        Attendance updateAttendance = attendances.update(request.nickname(), request.day(), request.updateTime(), now);
        AttendanceUpdateResponse response = AttendanceUpdateResponse.convertToAttendanceUpdateResponse(
                now.withDayOfMonth(Integer.parseInt(request.day())),
                previousAttendance.getCheckInTime(),
                previousAttendance.getAttendanceType(),
                updateAttendance.getCheckInTime(),
                updateAttendance.getAttendanceType());
        OutputView.printUpdateAttendance(response);
    }

    private void findAttendanceHistoryByCrew(Attendances attendances) {
        AttendanceHistoryRequest request = InputView.readAttendanceHistoryRequest();
        AttendanceHistoryResponse response = attendances.findHistoryByCrew(request.nickname(), LocalDate.now());
        OutputView.printAttendanceHistory(response);
    }

    private void findRiskCrews(Attendances attendances) {
        AttendanceRiskCrewsResponse response = attendances.findRiskCrews(LocalDate.now());
        OutputView.printRiskCrews(response);
    }

    private <T> T processWithRetry(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void processWithRetry(Runnable runnable) {
        while (true) {
            try {
                runnable.run();
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
