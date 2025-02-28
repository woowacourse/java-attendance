package service;

import controller.dto.ModifyAttendanceRequest;
import controller.dto.MonthAttendanceStatisticsRequest;
import controller.dto.RiskCrewsRequest;
import controller.dto.SaveAttendanceRequest;
import domain.AttendanceRecord;
import domain.AttendanceRecords;
import domain.AttendanceStatus;
import domain.Crew;
import domain.Crews;
import domain.RiskRank;
import fixture.AttendanceRecordsGenerator;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import service.dto.ModifyAttendanceRecordResponse;
import service.dto.MonthAttendanceStatisticsResponse;
import service.dto.RiskCrewsResponse;
import service.dto.SaveAttendanceRecordResponse;

class AttendanceServiceTest {
    @Nested
    @DisplayName("예외가 발생하지 않는 테스트")
    class Success {

        @Test
        @DisplayName("닉네임과 등교시간을 받아 출석 기록을 저장한다")
        void saveAttendanceRecord_test() {
            // given
            String nickname = "시소";
            Crew crew = new Crew(nickname);
            LocalDate monday = LocalDate.of(2025, 2, 3);
            LocalTime time = LocalTime.of(13, 30);
            Crews crews = new Crews(List.of(crew));
            AttendanceRecords attendanceRecords = new AttendanceRecords();
            AttendanceService attendanceService = new AttendanceService(crews, attendanceRecords);
            SaveAttendanceRequest request = new SaveAttendanceRequest(nickname, monday, time);

            // when
            SaveAttendanceRecordResponse response = attendanceService.saveAttendanceRecord(request);

            // then
            SoftAssertions.assertSoftly(softAssertions -> {
                softAssertions.assertThat(response.dateTime())
                        .isEqualTo(LocalDateTime.of(monday, time));
                softAssertions.assertThat(response.status())
                        .isEqualTo(AttendanceStatus.LATE);
            });
        }

        @Test
        @DisplayName("닉네임, 수정일, 수정 시간을 입력 받아 출석 기록을 수정한다")
        void modifyAttendanceRecord_test() {
            // given
            String nickname = "수양";
            Crew crew = new Crew(nickname);
            Crews crews = new Crews(List.of(crew));

            LocalDate monday = LocalDate.of(2025, 2, 3);
            int dayToModify = 3;
            LocalTime beforeTime = LocalTime.of(13, 5);
            String timeToModify = "13:30";
            LocalTime afterTime = LocalTime.of(13, 30);

            AttendanceRecords attendanceRecords = new AttendanceRecords(
                    List.of(AttendanceRecord.of(crew, monday, beforeTime)));
            AttendanceService attendanceService = new AttendanceService(crews, attendanceRecords);
            ModifyAttendanceRequest request = ModifyAttendanceRequest.of(nickname, monday, dayToModify, timeToModify);

            // when
            ModifyAttendanceRecordResponse response = attendanceService.modifyAttendanceRecord(request);

            // then
            SoftAssertions.assertSoftly(softAssertions -> {
                softAssertions.assertThat(response.before() instanceof AttendanceRecord).isTrue();
                AttendanceRecord beforeRecord = (AttendanceRecord) response.before();
                softAssertions.assertThat(beforeRecord.getTime()).isEqualTo(beforeTime);
                softAssertions.assertThat(response.after().getTime()).isEqualTo(afterTime);
            });
        }

        @Test
        @DisplayName("특정 크루의 월단위 출석 통계를 조회한다")
        void bringMonthAttendanceStatistics_test() {
            // given
            String nickname = "시소";
            Crew crew = new Crew(nickname);
            LocalDate today = LocalDate.of(2025, 2, 11);
            Crews crews = new Crews(List.of(crew));
            AttendanceRecords attendanceRecords = new AttendanceRecords();
            // 지각
            attendanceRecords.add(AttendanceRecord.of(crew, LocalDate.of(2025, 2, 3), LocalTime.of(13, 6)));
            // 출석
            attendanceRecords.add(AttendanceRecord.of(crew, LocalDate.of(2025, 2, 4), LocalTime.of(10, 5)));
            // 결석
            attendanceRecords.add(AttendanceRecord.of(crew, LocalDate.of(2025, 2, 5), LocalTime.of(10, 31)));
            // 지각
            attendanceRecords.add(AttendanceRecord.of(crew, LocalDate.of(2025, 2, 6), LocalTime.of(10, 30)));
            // 2/7, 2/10: 출석 기록 없는 결석
            // 총 출석 1, 지각 2, 결석 3

            AttendanceService attendanceService = new AttendanceService(crews, attendanceRecords);
            MonthAttendanceStatisticsRequest request = new MonthAttendanceStatisticsRequest(nickname, today);
            MonthAttendanceStatisticsResponse response = attendanceService.getMonthAttendanceStatistics(request);

            // when
            SoftAssertions.assertSoftly(softAssertions -> {
                softAssertions.assertThat(response.attendanceStatusCount().attendanceCount())
                        .isEqualTo(1);
                softAssertions.assertThat(response.attendanceStatusCount().lateCount()).isEqualTo(2);
                softAssertions.assertThat(response.attendanceStatusCount().absentCount()).isEqualTo(3);
                softAssertions.assertThat(response.riskRank()).isEqualTo(RiskRank.INTERVIEW);
            });
        }

        @Test
        @DisplayName("정렬된 제적 위험자 목록을 조회한다.")
        void bringRiskCrews_test() {
            // given
            Crew miso = new Crew("미소");
            Crew boogie = new Crew("부기");
            Crew wooga = new Crew("우가");
            Crew posty = new Crew("포스티");
            Crews crews = new Crews(List.of(miso, boogie, wooga, posty));

            LocalDate from = LocalDate.of(2025, 2, 3);
            LocalDate to = LocalDate.of(2025, 2, 25);
            List<AttendanceRecord> misoRecords = AttendanceRecordsGenerator.generate(from, to,
                    miso, 6, 4);
            List<AttendanceRecord> boogieRecords = AttendanceRecordsGenerator.generate(from, to,
                    boogie, 7, 4);
            List<AttendanceRecord> woogaRecords = AttendanceRecordsGenerator.generate(from, to,
                    wooga, 1, 5);
            List<AttendanceRecord> postyRecords = AttendanceRecordsGenerator.generate(from, to,
                    posty, 1, 5);
            List<AttendanceRecord> allRecords = new ArrayList<>();
            allRecords.addAll(misoRecords);
            allRecords.addAll(boogieRecords);
            allRecords.addAll(woogaRecords);
            allRecords.addAll(postyRecords);

            AttendanceService attendanceService = new AttendanceService(crews, new AttendanceRecords(allRecords));
            RiskCrewsRequest request = new RiskCrewsRequest(to.plusDays(1));

            // 제적 위험자 순서: 부기, 미소, 우가, 포스티
            // when & then
            RiskCrewsResponse response = attendanceService.getRiskCrews(request);
            SoftAssertions.assertSoftly(softAssertions -> {
                softAssertions.assertThat(response.riskCrews().get(0).nickname())
                        .isEqualTo("부기");
                softAssertions.assertThat(response.riskCrews().get(1).nickname())
                        .isEqualTo("미소");
                softAssertions.assertThat(response.riskCrews().get(2).nickname())
                        .isEqualTo("우가");
                softAssertions.assertThat(response.riskCrews().get(3).nickname())
                        .isEqualTo("포스티");
            });
        }
    }

    @Nested
    @DisplayName("예외 테스트")
    class Fail {

    }
}