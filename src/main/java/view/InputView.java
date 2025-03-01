package view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class InputView {

    private static final Scanner SCANNER = new Scanner(System.in);

    public static String readSelection() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM월 dd일 EEE요일");
        String formatted = formatter.format(LocalDate.now());
        System.out.printf("오늘은 %s입니다. 기능을 선택해 주세요.\n", formatted);
        System.out.println("""
            1. 출석 확인
            2. 출석 수정
            3. 크루별 출석 기록 확인
            4. 제적 위험자 확인
            Q. 종료
            """);
        return SCANNER.nextLine();
    }

    public static String readNickName() {
        System.out.println("닉네임을 입력해 주세요.");
        return SCANNER.nextLine();
    }

    public static String readAttendTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        return SCANNER.nextLine();
    }

    public static String readNickNameToModify() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        return SCANNER.nextLine();
    }

    public static String readDateToBeModified() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        return SCANNER.nextLine();
    }

    public static String readAttendTimeToModify() {
        System.out.println("언제로 변경하시겠습니까?");
        return SCANNER.nextLine();
    }
}
