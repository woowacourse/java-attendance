package view;

import domain.CustomDayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class InputView {
    private final Scanner scanner = new Scanner(System.in);

    public String getOption(LocalDate today) {
        System.out.printf("""
                오늘은 %d월 %d일 %s입니다. 기능을 선택해 주세요.
                1. 출석 확인
                2. 출석 수정
                3. 크루별 출석 기록 확인
                4. 제적 위험자 확인
                Q. 종료
                """, today.getMonth().getValue(), today.getDayOfMonth(), CustomDayOfWeek.getInstance(today).getName());
        return scanner.nextLine();
    }

    public String getNickname() {
        System.out.println("닉네임을 입력해 주세요.");
        return scanner.nextLine();
    }

    public LocalTime getAttendanceTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        return LocalTime.parse(scanner.nextLine());
    }

    public String getNicknameForModification() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        return scanner.nextLine();
    }

    public Integer getDayOfMonth() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        return Integer.parseInt(scanner.nextLine());
    }

    public LocalTime getModifiedAttendanceTime() {
        System.out.println("언제로 변경하겠습니까?");
        return LocalTime.parse(scanner.nextLine());
    }
}
