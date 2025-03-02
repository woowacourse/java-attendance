package attendance.view;

import attendance.utils.Console;

public class InputView {

    public String inputAttendanceFunction(final int month, final int day, final String dayOfWeek) {
        System.out.println("오늘은 " + month + "월 " + day + "일 " + dayOfWeek + "입니다. 기능을 선택해 주세요.");
        System.out.println("1. 출석 확인");
        System.out.println("2. 출석 수정");
        System.out.println("3. 크루별 출석 기록 확인");
        System.out.println("4. 제적 위험자 확인");
        System.out.println("5. 종료");

        return Console.readLine();
    }

    public String inputCrewName() {
        System.out.println("닉네임을 입력해 주세요.");
        return Console.readLine();
    }

    public String inputAttendTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        return Console.readLine();
    }

    public String inputModifyCrewName() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        return Console.readLine();
    }

    public String inputModifyDay() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        return Console.readLine();
    }

    public String inputModifyAttendTime() {
        System.out.println("언제로 변경하시겠습니까?");
        return Console.readLine();
    }
}
