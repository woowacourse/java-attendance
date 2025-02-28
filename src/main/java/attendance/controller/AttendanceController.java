package attendance.controller;

import attendance.dto.CrewAttendanceDTO;
import attendance.model.AcademicStatus;
import attendance.model.AttendanceBook;
import attendance.model.AttendanceStatus;
import attendance.model.AttendanceTime;
import attendance.model.Function;
import attendance.util.AttendanceReader;
import attendance.util.Parser;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.function.Supplier;

public class AttendanceController {

    private final InputView inputView;
    private final OutputView outputView;

    public AttendanceController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void start() {

        AttendanceBook attendanceBook = new AttendanceBook();
        initAttendances(attendanceBook);
        runSystem(attendanceBook);
    }

    private static void initAttendances(AttendanceBook attendanceBook) {

        AttendanceReader.initAttendances(attendanceBook);
        attendanceBook.initCrewsAbsence();
    }

    private void runSystem(final AttendanceBook attendanceBook) {

        Function function = inputFunction();
        if (function == Function.QUIT) {
            return;
        }

        try {
            doFunction(function, attendanceBook);
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
        }
        runSystem(attendanceBook);
    }

    private void doFunction(final Function function, final AttendanceBook attendanceBook) {
        if (function == Function.ADD_ATTENDANCE) {
            addAttendance(attendanceBook);
        }
        if (function == Function.MODIFY_ATTENDANCE) {
            modifyAttendance(attendanceBook);
        }
        if (function == Function.GET_CREW_ATTENDANCES) {
            getCrewAttendances(attendanceBook);
        }
        if (function == Function.GET_EXPULSION_CANDIDATES) {

        }
    }

    private boolean isWeekend(final LocalDate date) {

        return date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    private Function inputFunction() {

        return retryInput(() -> Function.getFunction(inputView.inputFunction()));
    }

    private void addAttendance(final AttendanceBook attendanceBook) {

        validateAttendDate();

        final String crewName = inputCrewName(attendanceBook);
        validateAlreadyAttended(crewName, attendanceBook);
        final AttendanceTime attendanceTime = inputAttendanceTime();

        attendanceBook.add(crewName, attendanceTime);

        outputView.printAttendance(attendanceTime);
        outputView.printLine();
    }

    private void validateAlreadyAttended(final String crewName, final AttendanceBook attendanceBook) {

        if (attendanceBook.isAlreadyExists(crewName, LocalDate.now())) {
            throw new IllegalArgumentException("[ERROR] 이미 출석 기록이 존재합니다. 출석 수정 기능을 이용해 주세요.");
        }
    }

    private void validateAttendDate() {

        if (isWeekend(LocalDate.now())) {
            final int month = LocalDate.now().getMonthValue();
            final int date = LocalDate.now().getDayOfMonth();
            final String day = LocalDate.now().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
            throw new IllegalArgumentException(
                    String.format("[ERROR] %02d월 %02d일 %s는 등교일이 아닙니다.", month, date, day));
        }
    }

    private String inputCrewName(final AttendanceBook attendanceBook) {
        return retryInput(() -> {
            String name = inputView.inputCrewName();
            validateCrewNickname(attendanceBook, name);
            return name;
        });
    }

    private AttendanceTime inputAttendanceTime() {
        LocalDate now = LocalDate.now();
        return retryInput(() -> {
            String[] split = inputView.inputAttendTime().split(":");
            return new AttendanceTime(LocalDate.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth()),
                    Parser.parseInt(split[0]), Parser.parseInt(split[1]));
        });
    }

    private static void validateCrewNickname(final AttendanceBook attendanceBook, final String name) {
        if (!attendanceBook.isCrewExists(name)) {
            throw new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다.");
        }
    }

    private void modifyAttendance(final AttendanceBook attendanceBook) {

        final String crewName = inputModifyCrewName(attendanceBook);
        final int modifyDate = inputModifyDate();
        final AttendanceTime modifyTime = inputModifyTime(modifyDate);

        AttendanceTime attendance = attendanceBook.getAttendance(crewName,
                LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), modifyDate));

        outputView.printAttendance(attendance);

        attendance.modify(modifyTime.getHour(), modifyTime.getMinute());

        outputView.printAfterAttendance(modifyTime);
    }

    private String inputModifyCrewName(final AttendanceBook attendanceBook) {

        return retryInput(() -> {
            String name = inputView.inputModifyCrewName();
            validateCrewNickname(attendanceBook, name);
            return name;
        });
    }

    private int inputModifyDate() {

        return retryInput(inputView::inputModifyDate);
    }

    private AttendanceTime inputModifyTime(final int date) {

        return retryInput(() -> {
            LocalDate now = LocalDate.now();
            String[] split = inputView.inputModifyTime().split(":");
            return new AttendanceTime(LocalDate.of(now.getYear(), now.getMonthValue(), date),
                    Parser.parseInt(split[0]), Parser.parseInt(split[1]));
        });
    }

    private void getCrewAttendances(final AttendanceBook attendanceBook) {

        final String crewName = inputCrewName(attendanceBook);
        List<AttendanceTime> attendances = attendanceBook.getAttendancesByName(crewName)
                .stream()
                .sorted(Comparator.comparing(AttendanceTime::getDate))
                .toList();

        int attend = attendanceBook.getAttendanceStatusCount(crewName, AttendanceStatus.ATTEND);
        int late = attendanceBook.getAttendanceStatusCount(crewName, AttendanceStatus.LATE);
        int absent = attendanceBook.getAttendanceStatusCount(crewName, AttendanceStatus.ABSENT);

        EnumMap<AttendanceStatus, Integer> attendanceStatusMap = getAttendanceStatusMap(
                attend, late, absent);
        AcademicStatus academicStatus = AcademicStatus.getAcademicStatus(late, absent);

        outputView.printCrewAttendances(
                new CrewAttendanceDTO(crewName, attendances, attendanceStatusMap, academicStatus));
    }

    private EnumMap<AttendanceStatus, Integer> getAttendanceStatusMap(int attend, int late,
                                                                      int absent) {
        EnumMap<AttendanceStatus, Integer> attendanceStatusMap = new EnumMap<>(AttendanceStatus.class);
        attendanceStatusMap.put(AttendanceStatus.ATTEND, attend);
        attendanceStatusMap.put(AttendanceStatus.LATE, late);
        attendanceStatusMap.put(AttendanceStatus.ABSENT, absent);
        return attendanceStatusMap;
    }

    private <T> T retryInput(Supplier<T> supplier) {

        try {
            return supplier.get();
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
            return retryInput(supplier);
        }
    }
}
