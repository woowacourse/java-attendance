package controller.command;

import controller.AttendanceController;
import controller.DateTimeConverter;
import domain.AttendanceBook;
import domain.attendance.AttendanceStatus;
import domain.attendance.Attendances;
import java.time.LocalDateTime;
import java.util.function.Consumer;
import view.InputView;
import view.OutputView;

public class AttendCommand implements Consumer<AttendanceBook> {
    private final InputView inputView;
    private final OutputView outputView;

    public AttendCommand(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    @Override
    public void accept(AttendanceBook attendanceBook) {
        String nickname = getNickname(attendanceBook);
        LocalDateTime attendedTime = getAttendTime();

        Attendances attendances = attendanceBook.findAttendancesByCrew(nickname);
        AttendanceStatus attendanceStatus = attendances.attend(AttendanceController.END_DATE, attendedTime);

        outputView.printAttend(attendedTime, attendanceStatus.getStatus());
    }

    private LocalDateTime getAttendTime() {
        String attendTime = inputView.getAttendTime();
        return DateTimeConverter.convertStringToLocalTime(AttendanceController.END_DATE, attendTime);
    }

    private String getNickname(AttendanceBook attendanceBook) {
        String nickname = inputView.inputAttendNickname();
        if (!attendanceBook.has(nickname)) {
            throw new IllegalArgumentException("등록되지 않은 닉네임입니다");
        }
        return nickname;
    }
}
