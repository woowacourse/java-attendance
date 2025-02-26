package attendance.view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class OutputView {

    public void printDate(LocalDate date) {
        System.out.print(date.format(DateTimeFormatter.ofPattern("오늘은 MM월 dd일 E요일입니다. ")));
    }
}
