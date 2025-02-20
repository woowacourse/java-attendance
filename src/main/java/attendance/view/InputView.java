package attendance.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class InputView {
    private static final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    public static String readOption() {
        return readLine();
    }

    public static String readNickName() {
        System.out.println("닉네임을 입력해 주세요.");
        return readLine();
    }

    private static String readLine(){
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("입출력 오류 발생");
        }
    }

    public static LocalTime readAttendanceTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        try {
            return LocalTime.parse(readLine());
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("잘못된 시간 형식입니다. HH:mm 형식으로 입력해주세요.");
        }
    }

    public static String readModifyNickName() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        return readLine();
    }

    public static int readModifyDay() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        try{
            return Integer.parseInt(readLine());
        } catch (NumberFormatException e){
            throw new IllegalArgumentException("[ERROR] 날짜를 숫자로 입력해주세요.");
        }
    }

    public static LocalTime readModifyTime() {
        System.out.println("언제로 변경하겠습니까?");
        try{
            return LocalTime.parse(readLine());
        } catch (DateTimeParseException e){
            throw new IllegalArgumentException("[ERROR] 시간을 HH:mm 형식으로 입력해주세요.");
        }
    }
}
