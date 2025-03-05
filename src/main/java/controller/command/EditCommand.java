package controller.command;

import controller.AttendanceController;
import controller.DateTimeConverter;
import domain.AttendanceBook;
import domain.attendance.Attendance;
import domain.attendance.AttendanceStatus;
import domain.attendance.Attendances;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.function.Consumer;
import view.InputView;
import view.OutputView;

public class EditCommand implements Consumer<AttendanceBook> {
    private final InputView inputView;
    private final OutputView outputView;

    public EditCommand(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    @Override
    public void accept(AttendanceBook attendanceBook) {
        String nickname = getNickname(attendanceBook);
        LocalDate editDate = getEditDate(nickname, attendanceBook);
        LocalDateTime editDateTime = getAttendDateTime(editDate);

        Attendances attendances = attendanceBook.findAttendancesByCrew(nickname);
        Attendance beforeAttendanceInfo = attendances.editAttendanceDate(editDateTime);

        outputView.printEdit(
                beforeAttendanceInfo.getAttendanceDateTime(),
                beforeAttendanceInfo.getStatus().getStatus(),
                editDateTime,
                AttendanceStatus.calculateStatus(editDateTime).getStatus());
    }

    private String getNickname(AttendanceBook attendanceBook) {
        String nickname = inputView.inputEditNickname();
        if (!attendanceBook.has(nickname)) {
            throw new IllegalArgumentException("등록되지 않은 닉네임입니다");
        }
        return nickname;
    }

    private LocalDate getEditDate(String nickname, AttendanceBook attendanceBook) {
        String editDate = inputView.getEditDate();
        LocalDate date = LocalDate.of(
                AttendanceController.END_DATE.getYear(),
                AttendanceController.END_DATE.getMonthValue(),
                Integer.parseInt(editDate));

        Attendances attendances = attendanceBook.findAttendancesByCrew(nickname);

        if (date.isEqual(AttendanceController.END_DATE) && !attendances.has(date)) {
            throw new IllegalArgumentException("먼저 출석 후에 수정해주세요.");
        }
        if (!attendances.has(date)) {
            throw new IllegalArgumentException("미래의 날짜는 수정할 수 없습니다.");
        }
        return date;
    }

    private LocalDateTime getAttendDateTime(LocalDate date) {
        String attendTime = inputView.getEditTime();
        return DateTimeConverter.convertStringToLocalTime(date, attendTime);
    }
}
