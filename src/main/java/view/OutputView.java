package view;

import java.time.LocalDate;
import util.DayOfWeekConvertor;

public class OutputView {

    public void printWelcomeMessage(LocalDate nowDate) {
        System.out.println(String.format(" 오늘은 %d월 %d일 %s요일입니다. 기능을 선택해 주세요.",
                nowDate.getMonthValue(),
                nowDate.getDayOfMonth(),
                DayOfWeekConvertor.convertToKorean(nowDate.getDayOfWeek())));
    }
}
