package domain;

import static constant.AttendanceStatus.ABSENT;
import static constant.AttendanceStatus.LATE;
import static constant.Warning.COUNSELING;
import static constant.Warning.EXPEL;
import static constant.Warning.WARNING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import constant.AttendanceStatus;
import constant.Warning;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


public class CrewTest {

    @Nested
    @DisplayName("정상 출석 시도 테스트")
    class AttendanceTest {
        @DisplayName("정상 출석 상황")
        @Test
        void test1() {
            // given
            Crew crew = new Crew("띠용");
            LocalDateTime attendedTime = LocalDateTime.of(2024, 12, 5, 9, 59);

            // when
            Attendance attendanceResult = crew.addAttendance(attendedTime);

            //then
            assertThat(attendanceResult).isNotNull();   // 출석 결과 반환 테스트
            assertThat(attendanceResult.getDateAndTime()).isEqualTo(attendedTime);  // 저장된 출석 시간 테스트
            assertThat(attendanceResult.getState()).isEqualTo(AttendanceStatus.ATTENDED.getStatus());   // 저장된 출석 상태 테스트
        }

        @DisplayName("지각 상황")
        @Test
        void test2() {
            // given
            Crew crew = new Crew("띠용");
            LocalDateTime lateAttendedTime = LocalDateTime.of(2024, 12, 5, 10, 6);

            // when
            Attendance attendanceResult = crew.addAttendance(lateAttendedTime);

            // then
            assertThat(attendanceResult).isNotNull();
            assertThat(attendanceResult.getDateAndTime()).isEqualTo(lateAttendedTime);
            assertThat(attendanceResult.getState()).isEqualTo(LATE.getStatus());
        }

        @DisplayName("결석 상황")
        @Test
        void test3() {
            // given
            Crew crew = new Crew("띠용");
            LocalDateTime absentAttendedTime = LocalDateTime.of(2024, 12, 5, 10, 31);

            // when
            Attendance attendanceResult = crew.addAttendance(absentAttendedTime);

            // then
            assertThat(attendanceResult).isNotNull();
            assertThat(attendanceResult.getDateAndTime()).isEqualTo(absentAttendedTime);
            assertThat(attendanceResult.getState()).isEqualTo(AttendanceStatus.ABSENT.getStatus());
        }
    }

    @Nested
    @DisplayName("비정상 출석 시도 테스트")
    class abnormalAttendanceTest {

        @DisplayName("중복 출석 시도 상황")
        @Test
        void test1() {
            // given
            Crew crew = new Crew("띠용");
            Attendance oldAttendance = crew.addAttendance(LocalDateTime.of(2024, 12, 5, 8, 59));
            assertThat(oldAttendance.getDateAndTime()).isEqualTo(LocalDateTime.of(2024, 12, 5, 8, 59));
            assertThat(oldAttendance.getState()).isEqualTo(AttendanceStatus.ATTENDED.getStatus());

            // when & then
            assertThatThrownBy(() -> crew.addAttendance(LocalDateTime.of(2024, 12, 5, 9, 59)))
                    .hasMessage("이미 출석 되었습니다.");
        }

        @DisplayName("휴무일 출석 시도 상황")
        @Test
        void test2() {
            // given
            Crew crew = new Crew("띠용");

            // when & then
            assertThatThrownBy(() -> crew.addAttendance(LocalDateTime.of(2024, 12, 7, 9, 59)))
                    .hasMessage("주말 또는 공휴일은 캠퍼스 휴장");
        }
    }

    @Nested
    @DisplayName("출석 수정 시도 테스트")
    class modifyAttendanceTest {

        @DisplayName("정상 출석 수정 상황")
        @Test
        void test1() {
            // given
            Crew crew = new Crew("띠용");
            Attendance oldAttendance = crew.addAttendance(LocalDateTime.of(2024, 12, 5, 8, 59));

            // when & then
            List<Attendance> oldAndNewAttendance = crew.update(LocalDateTime.of(2024, 12, 5, 10, 6));
            assertThat(oldAndNewAttendance.get(0)).isEqualTo(oldAttendance);
            assertThat(oldAndNewAttendance.get(1).getDateAndTime()).isEqualTo(LocalDateTime.of(2024, 12, 5, 10, 6));
            assertThat(oldAndNewAttendance.get(1).getState()).isEqualTo(LATE.getStatus());
        }

        @DisplayName("존재하지 않는 출석일 수정 시도 상황")
        @Test
        void test2() {
            //
            Crew crew = new Crew("띠용");
            assertThatThrownBy(() -> crew.update(LocalDateTime.of(2024, 12, 5, 10, 6)))
                    .hasMessage("해당 날짜에 출석 기록이 없습니다.");
        }
    }

    @Nested
    @DisplayName("출석 기록 확인 테스트")
    class AttendanceHistoryTest {
        @DisplayName("특정 크루 출석 기록 확인")
        @Test
        void test1() {
            // given
            Crew crew = new Crew("미미");
            LocalDateTime firstDateTime = LocalDateTime.of(2024, 12, 2, 13, 0);
            LocalDateTime secondDateTime = LocalDateTime.of(2024, 12, 3, 10, 7);

            // when
            crew.addAttendance(firstDateTime);
            crew.addAttendance(secondDateTime);

            // then
            assertThat(crew.getAttendanceInfo()).extracting(Attendance::getDateAndTime)
                    .containsExactlyInAnyOrder(firstDateTime, secondDateTime);
        }

        @DisplayName("출석 하지 않은 날을 결석 처리 하여 저장")
        @Test
        void test2() {
            // given
            Crew crew = new Crew("미미");
            LocalDateTime firstDateTime = LocalDateTime.of(2024, 12, 2, 13, 0);
            LocalDateTime secondDateTime = LocalDateTime.of(2024, 12, 3, 10, 7);
            LocalDateTime thirdDateTime = LocalDateTime.of(2024, 12, 5, 10, 7);
            crew.addAttendance(firstDateTime);
            crew.addAttendance(secondDateTime);
            crew.addAttendance(thirdDateTime);

            // when
            crew.updateAbsentUntil(LocalDate.of(2024, 12, 5));

            // then
            assertThat(crew.getAttendanceInfo()).extracting(Attendance::getDateAndTime)
                    .contains(LocalDateTime.of(2024, 12, 4, 15, 0));
        }

        @DisplayName("출석 하지 않은 날을 결석 처리 하여 저장 2")
        @Test
        void test3() {
            // given
            Crew crew = new Crew("미미");
            crew.addAttendance(LocalDateTime.of(2024, 12, 2, 13, 0));   // 출석
            crew.addAttendance(LocalDateTime.of(2024, 12, 3, 9, 58));   // 출석
            crew.addAttendance(LocalDateTime.of(2024, 12, 4, 10, 2));   // 출석
            crew.addAttendance(LocalDateTime.of(2024, 12, 5, 10, 6));   // 지각
            crew.addAttendance(LocalDateTime.of(2024, 12, 6, 10, 1));   // 출석
            crew.addAttendance(LocalDateTime.of(2024, 12, 10, 10, 8));  // 지각

            // when
            crew.updateAbsentUntil(LocalDate.of(2024, 12, 12));

            // then
            // 12월 9, 11, 12 결석 처리
            assertThat(crew.getAttendanceInfo())
                    .extracting(Attendance::getDateAndTime)
                    .contains(
                            LocalDateTime.of(2024, 12, 9, 15, 0),
                            LocalDateTime.of(2024, 12, 11, 15, 0),
                            LocalDateTime.of(2024, 12, 12, 15, 0)
                    );
        }

        @DisplayName("출석 상태별 카운팅 테스트")
        @Test
        public void test4() {
            // given
            Crew crew = new Crew("미미");
            crew.addAttendance(LocalDateTime.of(2024, 12, 2, 13,0));    // 출석
            crew.addAttendance(LocalDateTime.of(2024, 12, 3, 9,58));    // 출석
            crew.addAttendance(LocalDateTime.of(2024, 12, 4, 10,2));    // 출석
            crew.addAttendance(LocalDateTime.of(2024, 12, 5, 10,6));    // 지각
            crew.addAttendance(LocalDateTime.of(2024, 12, 6, 10,1));    // 출석
            crew.addAttendance(LocalDateTime.of(2024, 12, 10, 10,8));   // 지각

            // when
            crew.updateAbsentUntil(LocalDate.of(2024, 12, 12));

            // then
            // 12월 9, 11, 12일 결석
            assertThat(crew.getAttendanceCount()).isEqualTo(4);
            assertThat(crew.getLateCount()).isEqualTo(2);
            assertThat(crew.getAbsentCount()).isEqualTo(3);
        }
    }

    @Nested
    @DisplayName("제적 위험 상태 테스트")
    class WarningStatusTest {

        @DisplayName("특정 크루 경고 대상자 여부 판별")
        @Test
        public void test1() {
            // given
            Crew crew = new Crew("미미");
            crew.addAttendance(LocalDateTime.of(2024, 12, 2, 13,0));    // 출석
            crew.addAttendance(LocalDateTime.of(2024, 12, 3, 9,58));    // 출석
            crew.addAttendance(LocalDateTime.of(2024, 12, 4, 10,2));    // 출석
            crew.addAttendance(LocalDateTime.of(2024, 12, 5, 10,6));    // 지각
            crew.addAttendance(LocalDateTime.of(2024, 12, 6, 10,1));    // 출석
            crew.addAttendance(LocalDateTime.of(2024, 12, 9, 9,8));   // 지각
            crew.addAttendance(LocalDateTime.of(2024, 12, 10, 10,8));   // 지각

            // when
            crew.updateAbsentUntil(LocalDate.of(2024, 12, 12));

            // then
            // 11일 결석
            assertThat(crew.calculateWarningStatus()).isEqualTo(WARNING.getPenalty());
        }

        @DisplayName("특정 크루 면담 대상자 여부 판별")
        @Test
        public void test2() {
            // given
            Crew crew = new Crew("미미");
            crew.addAttendance(LocalDateTime.of(2024, 12, 2, 13,0));    // 출석
            crew.addAttendance(LocalDateTime.of(2024, 12, 3, 9,58));    // 출석
            crew.addAttendance(LocalDateTime.of(2024, 12, 4, 10,2));    // 출석
            crew.addAttendance(LocalDateTime.of(2024, 12, 5, 10,6));    // 지각
            crew.addAttendance(LocalDateTime.of(2024, 12, 6, 10,1));    // 출석
            crew.addAttendance(LocalDateTime.of(2024, 12, 10, 10,8));   // 지각

            // when
            crew.updateAbsentUntil(LocalDate.of(2024, 12, 12));

            // then
            // 7, 10, 11일 결석
            assertThat(crew.calculateWarningStatus()).isEqualTo(COUNSELING.getPenalty());
        }

        @DisplayName("특정 크루 제적 대상자 여부 판별")
        @Test
        public void test3() {
            // given
            Crew crew = new Crew("미미");
            crew.addAttendance(LocalDateTime.of(2024, 12, 2, 13,0));    // 출석
            crew.addAttendance(LocalDateTime.of(2024, 12, 3, 9,58));    // 출석
            crew.addAttendance(LocalDateTime.of(2024, 12, 4, 10,2));    // 출석
            crew.addAttendance(LocalDateTime.of(2024, 12, 5, 10,6));    // 지각
            crew.addAttendance(LocalDateTime.of(2024, 12, 6, 10,1));    // 출석
            crew.addAttendance(LocalDateTime.of(2024, 12, 10, 10,8));   // 지각

            // when
            crew.updateAbsentUntil(LocalDate.of(2024, 12, 17));

            // then
            // 7, 10, 11, 12, 13, 17일 결석
            assertThat(crew.calculateWarningStatus()).isEqualTo(EXPEL.getPenalty());
        }

        @DisplayName("제적 위험 비대상자 판별 테스트")
        @Test
        public void test4() {
            // given
            Crew crew = new Crew("미미");
            crew.addAttendance(LocalDateTime.of(2024, 12, 2, 13,0));    // 출석
            crew.addAttendance(LocalDateTime.of(2024, 12, 3, 9,58));    // 출석
            crew.addAttendance(LocalDateTime.of(2024, 12, 4, 10,2));    // 출석
            crew.addAttendance(LocalDateTime.of(2024, 12, 5, 10,6));    // 지각
            crew.addAttendance(LocalDateTime.of(2024, 12, 6, 10,1));    // 출석
            crew.addAttendance(LocalDateTime.of(2024, 12, 10, 10,8));   // 지각

            // when
            crew.updateAbsentUntil(LocalDate.of(2024, 12, 10));

            // then
            assertThat(crew.calculateWarningStatus()).isEmpty();
        }

    }
}
