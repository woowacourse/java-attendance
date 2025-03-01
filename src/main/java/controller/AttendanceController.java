package controller;

import domain.Attendance;
import domain.AttendanceBook;
import domain.AttendanceCounts;
import domain.AttendanceDate;
import domain.AttendanceStatistics;
import domain.AttendanceTime;
import domain.Attendances;
import domain.Nickname;
import domain.policy.absent.AbsentRule;
import reader.AttendanceFileReader;
import reader.FileReadException;
import util.FormatUtil;
import util.TimeMachine;
import view.InputView;
import view.OutputView;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceController {

    private final InputView inputView;
    private final OutputView outputView;

    public AttendanceController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    private static void quitProgram() {
        throw new ProgramQuitException(ProgramQuitException.MESSAGE);
    }

    public void run(AttendanceFileReader attendanceFileReader,
                    String attendanceFilePath) throws IOException {
        setDayOfMonth();

        AttendanceBook attendanceBook = initializeByAttendanceFile(attendanceFileReader, attendanceFilePath);

        RepeatUntilUserQuitSelect.repeat(() -> selectMenu(attendanceBook));
    }

    private void setDayOfMonth() {
        while (!TimeMachine.timeTravelAt(inputView.inputToday())) {
        }
    }

    private AttendanceBook initializeByAttendanceFile(AttendanceFileReader attendanceFileReader, String attendanceFilePath) throws FileReadException {
        try {
            AttendanceBook attendanceBook = AttendanceBook.initialize();
            attendanceBook.loadAttendance(attendanceFileReader, attendanceFilePath);
            return attendanceBook;
        } catch (FileReadException e) {
            System.out.println(FormatUtil.ERROR_PREFIX + e.getMessage());
            throw e;
        }
    }

    private void selectMenu(AttendanceBook attendanceBook) {
        switch (MenuSelectCommand.from(inputView.inputMenu())) {
            case ATTEND -> attend(attendanceBook);
            case UPDATE -> updateAttendance(attendanceBook);
            case DISPLAY -> displayAttendances(attendanceBook);
            case EXPULSION -> displayExpulsionCandidates(attendanceBook);
            case QUIT -> quitProgram();
        }
    }

    private void attend(AttendanceBook attendanceBook) {
        Nickname nickname = Nickname.from(inputView.inputNickname());

        AttendanceTime attendanceTime = AttendanceTime.from(LocalTime.parse(inputView.inputTime(), FormatUtil.TIME_FORMATTER));
        AttendanceDate attendanceDate = AttendanceDate.from(TimeMachine.dateOfNow());
        Attendance attendance = Attendance.of(attendanceDate, attendanceTime);

        outputView.printAttendance(attendanceBook.add(nickname, attendance));
    }

    private void updateAttendance(AttendanceBook attendanceBook) {
        Nickname nickname = Nickname.from(inputView.inputUpdateNickname());

        Attendances attendances = attendanceBook.findByNickname(nickname);

        int day = inputView.inputUpdateDate();
        AttendanceDate attendanceDate = AttendanceDate.from(LocalDate.of(TimeMachine.FIXED_YEAR, TimeMachine.FIXED_MONTH, day));
        AttendanceTime attendanceTimeForUpdate = AttendanceTime.from(LocalTime.parse(inputView.inputUpdateTime(), FormatUtil.TIME_FORMATTER));
        Attendance attendanceForUpdate = Attendance.of(attendanceDate, attendanceTimeForUpdate);

        Attendance originalAttendance = attendances.findByDate(attendanceDate);
        Attendance updatedAttendance = attendances.update(attendanceForUpdate);

        outputView.printAttendanceUpdate(originalAttendance, updatedAttendance);
    }

    private void displayAttendances(AttendanceBook attendanceBook) {
        Nickname nickname = Nickname.from(inputView.inputNickname());

        Attendances attendances = attendanceBook.findByNickname(nickname);

        outputView.printAttendanceIntro(nickname.value());
        outputView.printAttendances(attendances);

        AttendanceCounts attendanceCounts = attendances.calculateAttendanceCounts(nickname);
        outputView.printAttendanceStatistics(attendanceCounts);

        AbsentRule absentRule = AbsentRule.calculateAbsentPolicy(attendanceCounts);
        outputView.printAbsentPolicy(absentRule);
    }

    private void displayExpulsionCandidates(AttendanceBook attendanceBook) {
        AttendanceStatistics expulsionCandidates = attendanceBook.findExpulsionCandidates().orderByExpulsionRiskLevelAndNickname();
        outputView.printRiskOfExpulsionBanner();
        outputView.printExpulsionCandidate(expulsionCandidates);
    }
}
