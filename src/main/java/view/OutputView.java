package view;

import domain.DateProvider;

public class OutputView {

    public void printWellComeMessage(final DateProvider dateProvider) {

        String wellComeMessageFormat = String.format(
                "오늘은 %02d월 %02d일 %s입니다. 기능을 선택해 주세요.",
                dateProvider.getMonth(),
                dateProvider.getDayOfMonth(),
                dateProvider.getDayOfWeek().getDescription());

        System.out.println(wellComeMessageFormat);
    }

    public void printExit() {
        System.out.println("프로그램을 종료합니다.");
    }
}
