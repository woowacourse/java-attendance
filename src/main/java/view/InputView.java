package view;

import domain.AttendanceBook;
import domain.Crew;
import domain.Crews;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class InputView {
    private final Scanner scanner;

    public InputView() {
        scanner = new Scanner(System.in);
    }

    public String readLine(String message) {
        System.out.println(message);
        return scanner.nextLine();
    }

    public String readLine() {
        return scanner.nextLine();
    }

    public String enterMenuItem() {
        return readLine("1. 출석 확인\n2. 출석 수정\n3. 크루별 출석 기록 확인\n4. 제적 위험자 확인\nQ. 종료");
    }

    public String enterNickname() {
        return readLine("닉네임을 입력해 주세요.");
    }

    public String enterAttendanceTime() {
        return readLine("등교 시간을 입력해 주세요.");
    }

    public String enterNicknameForEdit() {
        return readLine("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
    }

    public String enterAttendanceDateForEdit() {
        return readLine("수정하려는 날짜(일)을 입력해 주세요.");
    }

    public String enterAttendanceTimeForEdit() {
        return readLine("언제로 변경하겠습니까?");
    }

    public void readFile(AttendanceBook attendanceBook, Crews crews) {
        try {
            BufferedReader bufferedReader = loadFile();
            String line = bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                String[] tokens = line.split(",");
                Crew crew = crews.initCrew(tokens[0]);
                attendanceBook.initAttendance(crew, LocalDateTime.parse(tokens[1], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("파일을 불러오는 중 예외가 발생하였습니다.");
        }
    }

    private BufferedReader loadFile() {
        try {
            Path filePath = Paths.get("src", "main", "resources", "attendances.csv");
            File file = filePath.toFile();
            return new BufferedReader(new FileReader(file));
        }
        catch (Exception e) {
            throw new IllegalArgumentException("파일을 불러오는 중 예외가 발생하였습니다.");
        }
    }
}
