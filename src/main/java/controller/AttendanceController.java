package controller;

import domain.Attendance;
import domain.AttendanceBook;
import domain.AttendanceDate;
import domain.AttendanceStatistics;
import domain.AttendanceTime;
import domain.Attendances;
import domain.ExpulsionCandidates;
import domain.rule.AbsentRule;
import util.FileReaderUtil;
import util.FormatUtil;
import util.RepeatUntilUserQuitSelectUtil;
import util.TimeMachine;
import view.InputView;
import view.OutputView;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceController {

    private final String dataPath;
    private final InputView inputView;
    private final OutputView outputView;

    public AttendanceController(String dataPath, InputView inputView, OutputView outputView) {
        this.dataPath = dataPath;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() throws IOException {
        TimeMachine.timeTravelAt(inputView.inputToday());

        AttendanceBook attendanceBook = AttendanceBook.initialize(FileReaderUtil.read(dataPath));

        RepeatUntilUserQuitSelectUtil.repeat(() -> selectMenu(attendanceBook));
    }

    private Boolean selectMenu(AttendanceBook attendanceBook) {
        String select = inputView.inputMenu(TimeMachine.dateOfNow()).toUpperCase();
        switch (select) {
            case "1" -> attend(attendanceBook);
            case "2" -> updateAttendance(attendanceBook);
            case "3" -> displayAttendances(attendanceBook);
            case "4" -> displayExpulsionCandidates(attendanceBook);
            case "Q" -> {
                return false;
            }
            default -> throw new IllegalArgumentException("올바른 기능을 선택해주세요.");
        }
        return true;
    }

    private void attend(AttendanceBook attendanceBook) {
        String nickname = inputView.inputNickname();

        AttendanceTime attendanceTime = AttendanceTime.from(LocalTime.parse(inputView.inputTime(), FormatUtil.TIME_FORMATTER));
        AttendanceDate attendanceDate = AttendanceDate.from(TimeMachine.dateOfNow());
        Attendance attendance = Attendance.from(attendanceDate, attendanceTime);

        outputView.printAttendance(attendanceBook.add(nickname, attendance));
    }

    private void updateAttendance(AttendanceBook attendanceBook) {
        String nickname = inputView.inputUpdateNickname();
        Attendances attendances = attendanceBook.findAllByNickname(nickname);

        int day = inputView.inputUpdateDate();
        AttendanceDate attendanceDate = AttendanceDate.from(LocalDate.of(TimeMachine.FIXED_YEAR, TimeMachine.FIXED_MONTH, day));
        AttendanceTime attendanceTimeForUpdate = AttendanceTime.from(LocalTime.parse(inputView.inputUpdateTime(), FormatUtil.TIME_FORMATTER));

        Attendance originalAttendance = Attendance.from(attendanceDate, attendances.findByDate(attendanceDate));
        Attendance updatedAttendance = attendances.update(attendanceDate, attendanceTimeForUpdate);

        outputView.printAttendanceUpdate(originalAttendance, updatedAttendance);
    }

    private void displayAttendances(AttendanceBook attendanceBook) {
        String nickname = inputView.inputNickname();
        Attendances attendances = attendanceBook.findAllByNickname(nickname);

        outputView.printAttendanceIntro(nickname);
        outputView.printAttendances(attendances);

        AttendanceStatistics attendanceStatistics = attendances.calculateStatistics(nickname, TimeMachine.dateOfNow());
        outputView.printAttendanceStatistics(attendanceStatistics);

        AbsentRule absentRule = AbsentRule.calculateAbsentPolicy(attendanceStatistics);
        outputView.printAbsentPolicy(absentRule);
    }

    private void displayExpulsionCandidates(AttendanceBook attendanceBook) {
        ExpulsionCandidates expulsionCandidates = attendanceBook.findExpulsionCandidates().orderByExpulsionRiskLevelAndNickname();
        outputView.printRiskOfExpulsionBanner();
        outputView.printExpulsionCandidate(expulsionCandidates);
    }
}
