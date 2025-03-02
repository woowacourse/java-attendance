package view;

import model.AttendanceCountsDto;
import model.AttendanceStatus;
import model.Crew;
import model.ExpulsionType;

import java.util.List;
import java.util.Map;

public class OutputView {

    public static void printAttendance(final TypeInfoDto dto) {
        System.out.println(String.format("%s", dto.getFormattedDateTime()));
    }

    public static void printAttendanceCorrection(final TypeInfoDto oldDto, final TypeInfoDto newDto) {
        System.out.println(String.format("%s -> %s 수정 완료!", oldDto.getFormattedDateTime(), newDto.getFormattedTime()));
    }

    public static void printCrewAttendanceBook(final Crew crew, final List<TypeInfoDto> dtos, final AttendanceCountsDto countsDto, final ExpulsionType expulsionType) {
        System.out.println(String.format("이번 달 %s의 출석 기록입니다.", crew.getNickname().getValue()));
        System.out.println();
        for (final TypeInfoDto dto : dtos) {
            System.out.println(String.format("%s", dto.getFormattedDateTime()));
        }
        System.out.println();

        final Map<AttendanceStatus, Integer> map = countsDto.map();

        for (final AttendanceStatus attendanceStatus : AttendanceStatus.values()) {
            final String statusDisplayName = attendanceStatus.getDisplayName();
            final int count = map.get(attendanceStatus);
            System.out.println(String.format("%s: %d회", statusDisplayName, count));
        }
        System.out.println();

        final String expulsionDisplayName = expulsionType.getDisplayName();
        if (!expulsionDisplayName.equals(ExpulsionType.NONE.getDisplayName())) {
            System.out.println(String.format("%s 대상자입니다.", expulsionDisplayName));
        }
    }
}
