package view;

import domain.AttendanceType;
import domain.Crew;
import domain.PenaltyType;
import dto.AttendanceStatusDto;
import dto.AttendanceStatusesOfCrewDto;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

public class OutputView {
    public static void printToday() {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        String date = now.format(DateTimeFormatter.ofPattern("M월 d일"));
        String dayOfWeek = now.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
        System.out.printf("오늘은 %s %s입니다. ", date, dayOfWeek);
    }

    public static void printAttendanceStatus(AttendanceStatusDto dto) {
        if (Objects.equals(dto.hour(), "--") || Objects.equals(dto.minute(), "--")) {
            System.out.printf("%n%02d월 %02d일 %s %s:%s (%s)%n%n",
                    dto.month(),
                    dto.day(),
                    dto.dayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN),
                    dto.hour(),
                    dto.minute(),
                    dto.attendanceType().getName());
            return;
        }

        System.out.printf("%n%02d월 %02d일 %s %02d:%02d (%s)%n%n",
                dto.month(),
                dto.day(),
                dto.dayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN),
                Integer.parseInt(dto.hour()),
                Integer.parseInt(dto.minute()),
                dto.attendanceType().getName());
    }

    public static void printAttendanceStatus(AttendanceStatusesOfCrewDto dto, String nickname) {
        List<AttendanceStatusDto> statusDtos = dto.attendanceStatusDtos();

        System.out.printf("%n이번 달 %s의 출석 기록입니다.%n%n", nickname);
        for (AttendanceStatusDto statusDto : statusDtos) {
            printAttendanceStatus(statusDto);
        }

        System.out.println();
        for (Entry<AttendanceType, Integer> entry : dto.attendanceTypeCount().entrySet()) {
            String typeName = entry.getKey().getName();
            int count = entry.getValue();

            System.out.printf("%s: %d회%n", typeName, count);
        }
        String penaltyName = dto.penaltyType().getName();
        System.out.printf("%n%s 대상자입니다.%n%n", penaltyName);
    }

    public static void printErrorMessage(String message) {
        System.out.println("[ERROR] " + message);
    }

    public static void printEditAttendanceStatus(List<AttendanceStatusDto> statusDtos) {
        AttendanceStatusDto oldStatusDto = statusDtos.getFirst();
        AttendanceStatusDto newStatusDto = statusDtos.getLast();

        // 이전 기록
        System.out.printf("%n%02d월 %02d일 %s %02d:%02d (%s) -> ",
                oldStatusDto.month(),
                oldStatusDto.day(),
                oldStatusDto.dayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN),
                Integer.parseInt(oldStatusDto.hour()),
                Integer.parseInt(oldStatusDto.minute()),
                oldStatusDto.attendanceType().getName());

        // 바뀐 기록
        System.out.printf("%02d:%02d (%s) 수정 완료!%n%n",
                Integer.parseInt(newStatusDto.hour()),
                Integer.parseInt(newStatusDto.minute()),
                newStatusDto.attendanceType().getName());
    }

    public static void printCrewOfBanRisk(Map<Crew, Map<AttendanceType, Integer>> attendanceTypeCountOfCrew) {
        Comparator<Map.Entry<Crew, Map<AttendanceType, Integer>>> penaltyTypeComparator = (p1, p2) -> {
            List<PenaltyType> order = List.of(PenaltyType.BAN, PenaltyType.ONE_ON_ONE, PenaltyType.WARNING,
                    PenaltyType.NONE);
            int priorityP1 = order.indexOf(PenaltyType.getFrom(p1.getValue()));
            int priorityP2 = order.indexOf(PenaltyType.getFrom(p2.getValue()));
            return Integer.compare(priorityP1, priorityP2);
        };

        Comparator<Map.Entry<Crew, Map<AttendanceType, Integer>>> absenceComparator = (p1, p2) -> {
            int absenceCountP1 = p1.getValue().getOrDefault(AttendanceType.ABSENCE, 0)
                    + p1.getValue().getOrDefault(AttendanceType.LATE, 0) / 3;
            int absenceCountP2 = p2.getValue().getOrDefault(AttendanceType.ABSENCE, 0)
                    + p2.getValue().getOrDefault(AttendanceType.LATE, 0) / 3;

            return Integer.compare(absenceCountP2, absenceCountP1);
        };

        List<Map.Entry<Crew, Map<AttendanceType, Integer>>> sortedAttendanceTypeCountOfCrew = attendanceTypeCountOfCrew.entrySet()
                .stream()
                .sorted(penaltyTypeComparator
                        .thenComparing(absenceComparator)
                        .thenComparing(entry -> entry.getKey().getNickname()))
                .toList();

        System.out.println("\n제적 위험자 조회 결과");
        for (Entry<Crew, Map<AttendanceType, Integer>> entry : sortedAttendanceTypeCountOfCrew) {
            String nickname = entry.getKey().getNickname();
            Map<AttendanceType, Integer> attendanceTypeCount = entry.getValue();

            if (PenaltyType.getFrom(attendanceTypeCount) == PenaltyType.NONE) {
                continue;
            }

            System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)%n",
                    nickname,
                    attendanceTypeCount.getOrDefault(AttendanceType.ABSENCE, 0),
                    attendanceTypeCount.getOrDefault(AttendanceType.LATE, 0),
                    PenaltyType.getFrom(attendanceTypeCount).getName()
            );
        }
        System.out.println();
    }
}
