package view;

import domain.Crews;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import static global.util.Date.TODAY;

public class InputView {
    private final Scanner scanner;

    public InputView(final Scanner scanner) {
        this.scanner = scanner;
    }

    public String inputMenu() {
        return inputByMessage(String.format("""
                오늘은 %d월 %02d일 %s입니다. 기능을 선택해 주세요.
                1. 출석 확인
                2. 출석 수정
                3. 크루별 출석 기록 확인
                4. 제적 위험자 확인
                Q. 종료""", TODAY.getMonth().getValue(), TODAY.getDayOfMonth(), ViewUtil.getDayOfWeekToMessage(TODAY.getDayOfWeek())));
    }

    private String inputByMessage(String message) {
        System.out.println(message);
        return scanner.nextLine();
    }

    public String inputName() {
        return inputByMessage("닉네임을 입력해 주세요.");
    }

    public String inputAttendTime() {
        return inputByMessage("등교 시간을 입력해 주세요.");
    }

    public Crews getFile() {
        try {
            InputStream inputStream = ClassLoader.getSystemClassLoader().getResource("./attendances.csv").openStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            Crews crews = new Crews();
            bufferedReader.readLine();
            getFileInput(bufferedReader, crews);
            return crews;
        } catch (IOException e) {
            throw new IllegalArgumentException("출석 파일을 불러올 수 없습니다.");
        }
    }

    private void getFileInput(final BufferedReader bufferedReader, final Crews crews) throws IOException {
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] tokens = line.split(",");
            validateName(tokens[0]);
            crews.initAttendStatus(tokens[0], LocalDateTime.parse(tokens[1], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        }
    }

    public String inputEditCrewName() {
        return inputByMessage("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
    }

    public String inputEditDay() {
        return inputByMessage("수정하려는 날짜(일)를 입력해 주세요.");
    }

    public String inputEditTime() {
        return inputByMessage("언제로 변경하겠습니까?");
    }

    private void validateName(final String name) {
        if (name.length() > 4 || name.length() < 2) {
            throw new IllegalArgumentException("크루 닉네임은 2자 이상, 4자 이하만 입력할 수 있습니다.");
        }
    }
}
