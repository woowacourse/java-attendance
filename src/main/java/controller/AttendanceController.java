package controller;

import static domain.Crew.DECEMBER_DAYS_END;
import static domain.Crew.DECEMBER_DAYS_START;
import static domain.Crew.SYSTEM_MONTH;
import static domain.Crew.SYSTEM_YEAR;

import domain.AttendanceBook;
import domain.AttendanceStatus;
import domain.Crew;
import domain.DayType;
import domain.Penalty;
import domain.UserSelection;
import dto.AttendanceRecordResponse;
import dto.AttendanceStatusCountResponse;
import dto.CheckAttendanceResponse;
import dto.CrewWithPenaltyResponse;
import dto.ModifyAttendanceResponse;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import util.FileReader;
import util.OutputParser;
import view.InputView;
import view.OutputView;

public class AttendanceController {

    private final AttendanceBook attendanceBook;
    private final InputView inputView;
    private final OutputView outputView;
    private final FileReader fileReader;
    private final LocalDate nowDate = LocalDate.now();
    private final LocalTime nowTime = LocalTime.now();
    private final Map<UserSelection, Runnable> selection = new EnumMap<>(UserSelection.class);

    public AttendanceController(AttendanceBook attendanceBook, InputView inputView, OutputView outputView,
                                FileReader fileReader) {
        this.attendanceBook = attendanceBook;
        this.inputView = inputView;
        this.outputView = outputView;
        this.fileReader = fileReader;
    }

    public void start() {
        fileReader.readFile();
        initializeSelection();
        askSelection();
    }

    private void initializeSelection() {
        selection.put(UserSelection.CHECK_ATTENDANCE, () -> retryRunnableUntilValid(this::checkAttendance));
        selection.put(UserSelection.MODIFY_ATTENDANCE, () -> retryRunnableUntilValid(this::modifyAttendance));
        selection.put(UserSelection.GET_ATTENDANCE_RECORDS, () -> retryRunnableUntilValid(this::getAttendanceRecords));
        selection.put(UserSelection.GET_CREWS_WITH_PENALTY, () -> retryRunnableUntilValid(this::getCrewsWithPenalty));
    }

    private void askSelection() {
        while (true) {
            UserSelection userSelection = retrySupplierUntilValid(
                    () -> inputView.readUserSelection(OutputParser.parseDateInKorean(nowDate)));

            if (userSelection == UserSelection.QUIT) {
                break;
            }

            retryRunnableUntilValid(() -> selection.get(userSelection).run());
        }
    }

    private void checkAttendance() {
        DayType.validateIsWorkingDay(nowDate);
        String name = retrySupplierUntilValid(this::readName);
        retryRunnableUntilValid(() -> AttendanceStatus.validateIsOperationHour(nowTime));
        outputView.printCheckAttendanceResult(checkAttendance(name, nowDate, nowTime));
    }

    private <T> T retrySupplierUntilValid(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();

            } catch (Exception e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private void retryRunnableUntilValid(Runnable runnable) {
        while (true) {
            try {
                runnable.run();
                break;

            } catch (Exception e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private void modifyAttendance() {
        String name = retrySupplierUntilValid(this::askNameToModify);
        LocalDate date = retrySupplierUntilValid(() -> askDateToModify(name));
        LocalTime time = retrySupplierUntilValid(this::askTimeToModify);
        outputView.printModifyAttendanceResult(modifyAttendance(name, date, time));
    }

    private LocalTime askTimeToModify() {
        LocalTime time = inputView.readTimeToModify();
        AttendanceStatus.validateIsOperationHour(time);
        return time;
    }

    private void getAttendanceRecords() {
        String name = retrySupplierUntilValid(this::readName);
        outputView.printGetAttendanceRecordsResult(name, getAttendanceRecordResponses(name),
                getAttendanceStatusCountResponseByName(name),
                getPenaltyResponseByName(name));
    }

    private void getCrewsWithPenalty() {
        outputView.printCrewWithPenaltyResponses(getCrewWithPenaltyResponses());
    }

    private LocalDate askDateToModify(String name) {
        LocalDate date = inputView.readDateToModify();
        attendanceBook.validateAttendanceRecordExistsByDate(name, date);
        return date;
    }

    private String askNameToModify() {
        String name = inputView.readNameToModify();
        attendanceBook.validateNameExists(name);
        return name;
    }

    private String readName() {
        String name = inputView.readName();
        attendanceBook.validateNameExists(name);
        return name;
    }

    public CheckAttendanceResponse checkAttendance(String name, LocalDate date, LocalTime time) {
        attendanceBook.putAttendanceRecordByName(name, date, time);
        return new CheckAttendanceResponse(
                OutputParser.parseDateInKorean(date), OutputParser.parseTimeToString(time),
                AttendanceStatus.findMessageByAttendDateAndTime(date, time)
        );
    }

    public ModifyAttendanceResponse modifyAttendance(String name, LocalDate date, LocalTime timeToModify) {
        LocalTime originalTime = attendanceBook.findTimeByNameAndDate(name, date);
        attendanceBook.modifyAttendanceRecordByName(name, date, timeToModify);
        return new ModifyAttendanceResponse(
                OutputParser.parseDateInKorean(date),
                OutputParser.parseTimeToString(originalTime),
                OutputParser.parseTimeToString(timeToModify),
                AttendanceStatus.findMessageByAttendDateAndTime(date, originalTime),
                AttendanceStatus.findMessageByAttendDateAndTime(date, timeToModify)
        );
    }

    public List<AttendanceRecordResponse> getAttendanceRecordResponses(String name) {
        return IntStream.rangeClosed(DECEMBER_DAYS_START, DECEMBER_DAYS_END)
                .mapToObj(day -> createAttendanceRecordResponseByName(name, day))
                .collect(Collectors.toList());
    }

    private AttendanceRecordResponse createAttendanceRecordResponseByName(String name, int day) {
        LocalDate date = LocalDate.of(SYSTEM_YEAR, SYSTEM_MONTH, day);
        LocalTime time = attendanceBook.findTimeByNameAndDate(name, date);
        return new AttendanceRecordResponse(
                OutputParser.parseDateInKorean(date),
                OutputParser.parseTimeToString(time),
                AttendanceStatus.findByAttendDateAndTime(date, time).getMessage()
        );
    }

    public AttendanceStatusCountResponse getAttendanceStatusCountResponseByName(String name) {
        return new AttendanceStatusCountResponse(
                attendanceBook.getAttendanceStatusCountByName(name, AttendanceStatus.ATTEND),
                attendanceBook.getAttendanceStatusCountByName(name, AttendanceStatus.LATE),
                attendanceBook.getAttendanceStatusCountByName(name, AttendanceStatus.ABSENT)
        );
    }

    public String getPenaltyResponseByName(String name) {
        int lateCount = attendanceBook.getAttendanceStatusCountByName(name, AttendanceStatus.LATE);
        int absentCount = attendanceBook.getAttendanceStatusCountByName(name, AttendanceStatus.ABSENT);
        return Penalty.findPenaltyMessageByAttendanceStatusCount(lateCount, absentCount);
    }

    public List<CrewWithPenaltyResponse> getCrewWithPenaltyResponses() {
        List<CrewWithPenaltyResponse> mergedResponses = new ArrayList<>();

        Penalty.valuesWithoutNone().stream()
                .map(this::createCrewsWithPenaltyResponseByPenalty)
                .forEach(mergedResponses::addAll);
        return mergedResponses;
    }

    private List<CrewWithPenaltyResponse> createCrewsWithPenaltyResponseByPenalty(Penalty penalty) {
        List<Crew> penaltyCrews = attendanceBook.findCrewsWithPenalty();
        return penaltyCrews.stream()
                .map(CrewWithPenaltyResponse::fromCrew)
                .toList();
    }
}
