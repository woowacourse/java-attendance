package view;

import model.AttendanceStatus;
import model.Crew;
import model.ExpulsionType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OutputView {

    public static void printAttendance(final TypeInfoDto dto) {
        System.out.println(String.format("%s", dto.getFormattedDateTime()));
    }

    public static void printAttendanceCorrection(final TypeInfoDto oldDto, final TypeInfoDto newDto) {
        System.out.println(String.format("%s -> %s 수정 완료!", oldDto.getFormattedDateTime(), newDto.getFormattedTime()));
        System.out.println();
    }

    public static void printCrewAttendanceBook(final Crew crew, final List<TypeInfoDto> typeInfoDtos, final ExpulsionInfoDto expulsionInfoDto) {
        System.out.println();
        System.out.println(String.format("이번 달 %s의 출석 기록입니다.", crew.getNickname().getValue()));
        System.out.println();
        for (final TypeInfoDto dto : typeInfoDtos) {
            System.out.println(String.format("%s", dto.getFormattedDateTime()));
        }
        System.out.println();

        final Map<AttendanceStatus, Integer> map = expulsionInfoDto.countsDto().map();

        for (final AttendanceStatus attendanceStatus : AttendanceStatus.values()) {
            final String statusDisplayName = attendanceStatus.getDisplayName();
            final int count = map.get(attendanceStatus);
            System.out.println(String.format("%s: %d회", statusDisplayName, count));
        }
        System.out.println();

        final String expulsionDisplayName = expulsionInfoDto.expulsionType().getDisplayName();
        if (!expulsionDisplayName.equals(ExpulsionType.NONE.getDisplayName())) {
            System.out.println(String.format("%s 대상자입니다.", expulsionDisplayName));
        }
        System.out.println();
    }

    public static void printRiskOfExpulsion(final Map<Crew, ExpulsionInfoDto> expulsionInfoDtosByCrew) {
        final List<Crew> keySet = getCrews(expulsionInfoDtosByCrew);

        keySet.forEach(crew -> System.out.println(String.format("%s: 결석 %d회, 지각 %d회 (%s)",
                crew.getNickname().getValue(),
                expulsionInfoDtosByCrew.get(crew).countsDto().getMap().get(AttendanceStatus.ABSENCE),
                expulsionInfoDtosByCrew.get(crew).countsDto().getMap().get(AttendanceStatus.TARDINESS),
                expulsionInfoDtosByCrew.get(crew).expulsionType().getDisplayName())));
        System.out.println();
    }

    private static List<Crew> getCrews(final Map<Crew, ExpulsionInfoDto> expulsionInfoDtosByCrew) {
        final List<Crew> keySet = new ArrayList<>(expulsionInfoDtosByCrew.keySet());

        keySet.sort((crew1, crew2) -> {
            final ExpulsionInfoDto info1 = expulsionInfoDtosByCrew.get(crew1);
            final ExpulsionInfoDto info2 = expulsionInfoDtosByCrew.get(crew2);

            final int discriminationCount1 = ExpulsionType.calculateDiscriminationCount(info1.countsDto());
            final int discriminationCount2 = ExpulsionType.calculateDiscriminationCount(info2.countsDto());

            return Integer.compare(discriminationCount2, discriminationCount1);
        });
        return keySet;
    }
}
