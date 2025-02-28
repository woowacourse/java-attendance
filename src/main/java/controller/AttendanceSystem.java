package controller;

import static view.OutputView.getFormattedDayInfo;
import static view.OutputView.printMenu;

import domain.AllCrew;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Scanner;

public class AttendanceSystem {
    private final LocalDate today;
    private final AllCrew allCrew;

    public AttendanceSystem() throws FileNotFoundException {
        today = LocalDate.now();
        allCrew = new AllCrew(new File("src/main/resources/attendances.csv"));
    }

    public void run() throws FileNotFoundException {
        System.out.println("오늘은 " + getFormattedDayInfo(today) + "입니다. 기능을 선택해 주세요.");
        // 오늘 기준으로 모든 크루의 빈 결석 정보 업데이트
        printMenu();
        String option = new Scanner(System.in).next();
    }

}
