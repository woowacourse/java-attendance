package attendance.view;

import static attendance.domain.AcademicStatus.NOT;

import attendance.domain.Attendance;
import attendance.domain.Time;
import dto.AcademicStatusResultDTO;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class OutputView {

    public void printErrorMessage(final String message) {
        System.out.println(message);
    }

    public void printAttendance(final Time attendanceTime, final String attendanceStatus) {
        System.out.printf("%02d월 %02d일 %s %02s:%02s (%s)%n",
                attendanceTime.getMonth(), attendanceTime.getDay(),
                attendanceTime.getDayOfWeek(), attendanceTime.getHour(), attendanceTime.getMinute(),
                attendanceStatus);
    }

    public void printModifyAttendanceResult(final Time originTime, final String originAttendanceStatus,
                                            final Time modifyTime, final String modifyAttendanceStatus) {
        System.out.printf("%02d월 %02d일 %s %02s:%02s (%s)",
                originTime.getMonth(), originTime.getDay(),
                originTime.getDayOfWeek(), originTime.getHour(), originTime.getMinute(),
                originAttendanceStatus);

        System.out.print(" -> ");

        System.out.printf("%02s:%02s (%s)",
                modifyTime.getHour(), modifyTime.getMinute(),
                modifyAttendanceStatus);

        System.out.println(" 수정 완료!");
    }

    public void printNameAndAttendances(final Map<LocalDate, Attendance> monthlyAttendances, final String crewName,
                                        final int year,
                                        final int month) {
        System.out.println("이번 달 " + crewName + "의 출석 기록입니다.");

        LocalDate currentDate = LocalDate.of(year, month, 1);
        LocalDate lastDayOfMonth = currentDate.withDayOfMonth(currentDate.lengthOfMonth());

        while (!currentDate.isAfter(lastDayOfMonth)) {
            if (currentDate.getDayOfWeek().equals(DayOfWeek.SUNDAY) || currentDate.getDayOfWeek()
                    .equals(DayOfWeek.SATURDAY)) {
                currentDate = currentDate.plusDays(1);
                continue;
            }

            Attendance attendance = monthlyAttendances.get(currentDate);

            if (attendance != null) {
                String status = attendance.checkStatus().getValue();
                String time = String.format("%02d:%02d",
                        attendance.getAttendanceTime().getHour(),
                        attendance.getAttendanceTime().getMinute());

                System.out.printf("%02d월 %02d일 %s %s (%s)%n",
                        currentDate.getMonthValue(),
                        currentDate.getDayOfMonth(),
                        currentDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN),
                        time,
                        status);
                currentDate = currentDate.plusDays(1);
                continue;
            }
            if (LocalDate.now().isAfter(currentDate)) {
                System.out.printf("%02d월 %02d일 %s --:-- (결석)%n",
                        currentDate.getMonthValue(),
                        currentDate.getDayOfMonth(),
                        currentDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN));

            }
            currentDate = currentDate.plusDays(1);

        }
    }

    public void printAcademicStatusResult(final AcademicStatusResultDTO attendanceCountAndAcademicStatusDTO) {
        System.out.println("출석: " + attendanceCountAndAcademicStatusDTO.attend() + "회");
        System.out.println("지각: " + attendanceCountAndAcademicStatusDTO.late() + "회");
        System.out.println("결석: " + attendanceCountAndAcademicStatusDTO.absent() + "회");

        if (attendanceCountAndAcademicStatusDTO.academicStatus().equals(NOT)) {
            System.out.println("대상자가 아닙니다.");
            return;
        }
        System.out.println(attendanceCountAndAcademicStatusDTO.academicStatus().getValue() + " 대상자입니다.");
    }

    public void printCrewsAtRiskOfExpulsion(final List<AcademicStatusResultDTO> crewNameAndAcademicStatusDTOList) {
        for (AcademicStatusResultDTO crewNameAndAcademicStatusDTO : crewNameAndAcademicStatusDTOList) {
            System.out.print("- " + crewNameAndAcademicStatusDTO.crewName() + ": ");
            System.out.print("결석: " + crewNameAndAcademicStatusDTO.absent() + "회, ");
            System.out.print("지각: " + crewNameAndAcademicStatusDTO.late() + "회 ");
            System.out.println("(" + crewNameAndAcademicStatusDTO.academicStatus().getValue() + ")");
        }
    }

    public void printRiskOfExpulsionIntro() {
        System.out.println("제적 위험자 조회 결과");
    }
}
