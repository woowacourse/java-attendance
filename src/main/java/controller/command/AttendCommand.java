package controller.command;

import domain.AttendanceBook;
import domain.AttendanceDateTime;
import domain.Crew;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import util.DateUtils;
import view.InputView;
import view.OutputView;

public class AttendCommand implements ControllerCommand {

    @Override
    public void execute(AttendanceBook book) {
        LocalDate today = LocalDate.now();
        validateTodayIsWorkingDay(today);

        String nickName = InputView.readNickName();
        Crew crew = retrieveCrew(book, nickName);

        String attendTime = InputView.readAttendTime();
        LocalTime time = LocalTime.parse(attendTime);
        LocalDateTime dateTime = LocalDateTime.of(today, time);

        book.attend(crew, AttendanceDateTime.from(dateTime));

        AttendanceDateTime result = book.findRecordByCrewAndDate(crew, today).orElseThrow();
        OutputView.printAttendResult(result);
    }

    private static void validateTodayIsWorkingDay(LocalDate today) {
        if (DateUtils.isDayOff(today)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM월 dd일 EEE요일");
            String formattedDate = formatter.format(today);
            throw new IllegalArgumentException(formattedDate + "은 등교일이 아닙니다.");
        }
    }

    private Crew retrieveCrew(AttendanceBook book, String nickName) {
        return book.findCrewByName(nickName)
            .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 닉네임입니다."));
    }
}
