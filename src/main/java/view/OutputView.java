package view;

import java.time.LocalDate;
import util.Convertor;

public class OutputView {

    public void printMenuHeader(LocalDate nowDate) {
        System.out.print(String.format("오늘은 %d월 %d일 %s요일입니다. 기능을 선택해 주세요.", nowDate.getMonthValue(), nowDate.getDayOfMonth(),
                Convertor.convertDayOfWeekToKorean(nowDate.getDayOfWeek())));
    }
}
