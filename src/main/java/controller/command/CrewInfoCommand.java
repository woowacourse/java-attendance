package controller.command;

import domain.AttendanceBook;
import domain.attendance.AttendanceDate;
import domain.attendance.Attendances;
import java.util.List;
import java.util.function.Consumer;
import view.InputView;
import view.OutputView;

public class CrewInfoCommand implements Consumer<AttendanceBook> {
    private final InputView inputView;
    private final OutputView outputView;

    public CrewInfoCommand(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    @Override
    public void accept(AttendanceBook attendanceBook) {
        String nickname = getNickname(attendanceBook);
        Attendances attendances = attendanceBook.findAttendancesByCrew(nickname);
        List<AttendanceDate> attendanceDates = attendances.getAttendanceDates();

        outputView.printCrewInfo(nickname, attendanceDates);
        printCrewStatus(attendances);
    }

    private void printCrewStatus(Attendances attendances) {
        int countAttendance = attendances.countAttendance();
        int countTardy = attendances.countTardy();
        int countAbsence = attendances.countAbsence();
        int warningStatus = attendances.countAllAbsence();
        outputView.printCrewWarningStatus(countAttendance, countTardy, countAbsence, warningStatus);
    }

    private String getNickname(AttendanceBook attendanceBook) {
        String nickname = inputView.inputAttendNickname();
        if (!attendanceBook.has(nickname)) {
            throw new IllegalArgumentException("등록되지 않은 닉네임입니다");
        }
        return nickname;
    }
}
