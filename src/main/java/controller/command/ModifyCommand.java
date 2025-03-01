package controller.command;

import domain.AttendanceBook;
import domain.AttendanceDateTime;
import domain.Crew;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import view.InputView;
import view.OutputView;

public class ModifyCommand implements ControllerCommand {

    @Override
    public void execute(AttendanceBook book) {
        String nickName = InputView.readNickNameToModify();
        Crew crew = retrieveCrew(book, nickName);

        String rawDateToBeModified = InputView.readDateToBeModified();
        int dayOfMonth = Integer.parseInt(rawDateToBeModified);
        LocalDate dateToBeModified = LocalDate.now().withDayOfMonth(dayOfMonth);

        String rawTimeToModify = InputView.readAttendTimeToModify();
        LocalTime timeToModify = LocalTime.parse(rawTimeToModify);

        AttendanceDateTime before = book.findRecordByCrewAndDate(crew, dateToBeModified).orElse(AttendanceDateTime.ofAbsence(dateToBeModified));

        LocalDateTime dateTime = LocalDateTime.of(dateToBeModified, timeToModify);
        AttendanceDateTime after = AttendanceDateTime.from(dateTime);

        book.modify(crew, dateToBeModified, after);
        OutputView.printModifyResult(before, after);
    }

    private Crew retrieveCrew(AttendanceBook book, String nickName) {
        return book.findCrewByName(nickName)
            .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 닉네임입니다."));
    }
}
