package domain.attendance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

import static domain.attendance.TimeTable.*;
import static java.time.DayOfWeek.*;
import static org.assertj.core.api.Assertions.*;

class TimeTableTest {
    @Nested
    class OperatingTimeTest{
        @DisplayName("캠퍼스 운영 시간이 아닌 경우 False 반환.")
        @ParameterizedTest
        @ValueSource(ints = {0,1,2,3,4,5,6,7,23})
        void isNotCampusOperatingTime(int hour){
            LocalTime localTime = LocalTime.of(hour,1);
            assertThat(isOnCampusOperatingTime(localTime)).isFalse();
        }

        @DisplayName("캠퍼스 운영 시간인 경우 True 반환.")
        @ParameterizedTest
        @ValueSource(ints = {8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23})
        void isCampusOperatingTime(int hour){
            LocalTime localTime = LocalTime.of(hour,0);
            assertThat(isOnCampusOperatingTime(localTime)).isTrue();
        }
    }

    @Nested
    class BeforeOverTardyAbsenceTest{
        @Nested
        class BeforeTardyTest{
            @DisplayName("월요일 출석 시간인 경우 True 반환")
            @ParameterizedTest
            @ValueSource(ints = {0,1,2,3,4,5,6,7,8,9,10,11,12,13})
            void isMondayBeforeTardy(int hour){
                LocalTime localTime = LocalTime.of(hour,5);
                assertThat(isBeforeTardyTimeLimit(MONDAY,localTime)).isTrue();
            }

            @DisplayName("월요일 출석 시간이 아닌 경우 False 반환")
            @ParameterizedTest
            @ValueSource(ints = {13,14,15,16,17,18,19,20,21,22,23})
            void isMondayAfterTardy(int hour){
                LocalTime localTime = LocalTime.of(hour,6);
                assertThat(isBeforeTardyTimeLimit(MONDAY,localTime)).isFalse();
            }

            @DisplayName("월요일 제외 출석 시간인 경우 True 반환")
            @ParameterizedTest
            @ValueSource(ints = {0,1,2,3,4,5,6,7,8,9,10})
            void exceptMondayBeforeTardy(int hour){
                LocalTime localTime = LocalTime.of(hour,5);
                Arrays.stream(DayOfWeek.values()).filter(dayOfWeek -> dayOfWeek != MONDAY && dayOfWeek != SATURDAY && dayOfWeek != SUNDAY)
                        .forEach(dayOfWeek -> assertThat(isBeforeTardyTimeLimit(dayOfWeek,localTime)).isTrue()
                );
            }

            @DisplayName("월요일 제외 출석 시간이 아닌 경우 False 반환")
            @ParameterizedTest
            @ValueSource(ints = {10,11,12,13,14,15,17,18,19,20,21,22,23})
            void exceptMondayAfterTardy(int hour){
                LocalTime localTime = LocalTime.of(hour,6);
                Arrays.stream(DayOfWeek.values()).filter(dayOfWeek -> dayOfWeek != MONDAY).forEach(
                        dayOfWeek -> assertThat(isBeforeTardyTimeLimit(dayOfWeek,localTime)).isFalse()
                );
            }
        }

        @Nested
        class OverTardyTest{
            @DisplayName("월요일 출석 시간을 지난 경우 True 반환")
            @ParameterizedTest
            @ValueSource(ints = {13,14,15,16,17,18,19,20,21,22,23})
            void isMondayBeforeTardy(int hour){
                LocalTime localTime = LocalTime.of(hour,6);
                assertThat(isOverTardyTimeLimit(MONDAY,localTime)).isTrue();
            }

            @DisplayName("월요일 출석 시간 전인 경우 False 반환")
            @ParameterizedTest
            @ValueSource(ints = {0,1,2,3,4,5,6,7,8,9,10,11,12,13})
            void isMondayAfterTardy(int hour){
                LocalTime localTime = LocalTime.of(hour,5);
                assertThat(isOverTardyTimeLimit(MONDAY,localTime)).isFalse();
            }

            @DisplayName("월요일 제외 시간을 지난 경우 True 반환")
            @ParameterizedTest
            @ValueSource(ints = {10,11,12,13,14,15,17,18,19,20,21,22,23})
            void exceptMondayBeforeTardy(int hour){
                LocalTime localTime = LocalTime.of(hour,6);
                Arrays.stream(DayOfWeek.values()).filter(dayOfWeek -> dayOfWeek != MONDAY && dayOfWeek != SATURDAY && dayOfWeek != SUNDAY).forEach(
                        dayOfWeek -> assertThat(isOverTardyTimeLimit(dayOfWeek,localTime)).isTrue()
                );
            }

            @DisplayName("월요일 제외 출석 시간 전인 경우 False 반환")
            @ParameterizedTest
            @ValueSource(ints = {0,1,2,3,4,5,6,7,8,9,10})
            void exceptMondayAfterTardy(int hour){
                LocalTime localTime = LocalTime.of(hour,5);
                Arrays.stream(DayOfWeek.values()).filter(dayOfWeek -> dayOfWeek != MONDAY).forEach(
                        dayOfWeek -> assertThat(isOverTardyTimeLimit(dayOfWeek,localTime)).isFalse()
                );
            }
        }

        @Nested
        class OverAbsenceTest{
            @DisplayName("월요일 결석 시간을 지난 경우 True 반환")
            @ParameterizedTest
            @ValueSource(ints = {13,14,15,16,17,18,19,20,21,22,23})
            void isMondayBeforeAbsence(int hour){
                LocalTime localTime = LocalTime.of(hour,31);
                assertThat(isOverAbsenceTimeLimit(MONDAY,localTime)).isTrue();
            }

            @DisplayName("월요일 결석 시간 전인 경우 False 반환")
            @ParameterizedTest
            @ValueSource(ints = {0,1,2,3,4,5,6,7,8,9,10,11,12,13})
            void isMondayAfterAbsence(int hour){
                LocalTime localTime = LocalTime.of(hour,30);
                assertThat(isOverAbsenceTimeLimit(MONDAY,localTime)).isFalse();
            }

            @DisplayName("월요일 제외 결석 시간을 지난 경우 True 반환")
            @ParameterizedTest
            @ValueSource(ints = {10,11,12,13,14,15,17,18,19,20,21,22,23})
            void exceptMondayBeforeAbsence(int hour){
                LocalTime localTime = LocalTime.of(hour,31);
                Arrays.stream(DayOfWeek.values()).filter(dayOfWeek -> dayOfWeek != MONDAY && dayOfWeek != SATURDAY && dayOfWeek != SUNDAY).forEach(
                        dayOfWeek -> assertThat(isOverAbsenceTimeLimit(dayOfWeek,localTime)).isTrue()
                );
            }

            @DisplayName("월요일 제외 결석 시간 전인 경우 False 반환")
            @ParameterizedTest
            @ValueSource(ints = {0,1,2,3,4,5,6,7,8,9,10})
            void exceptMondayAfterAbsence(int hour){
                LocalTime localTime = LocalTime.of(hour,30);
                Arrays.stream(DayOfWeek.values()).filter(dayOfWeek -> dayOfWeek != MONDAY && dayOfWeek != SATURDAY && dayOfWeek != SUNDAY).forEach(
                        dayOfWeek -> assertThat(isOverAbsenceTimeLimit(dayOfWeek,localTime)).isFalse()
                );
            }
        }
    }

    @Nested
    class AttendanceDayTest{
        @DisplayName("주말인 경우 false, 평일인 경우 true를 반환한다.")
        @Test
        void attendanceDayTest(){
            for(int i=1; i<=28; i++){ // 2월 1일은 토요일
                if(i % 7 == 1 || i % 7 == 2){
                    assertThat(isAttendanceDay(LocalDate.of(2025,2,i))).isFalse();
                } else {
                    assertThat(isAttendanceDay(LocalDate.of(2025,2,i))).isTrue();
                }
            }
        }

        @DisplayName("공휴일인 경우 false를 반환한다.")
        @Test
        void isHoliday(){
            assertThat(isAttendanceDay(LocalDate.of(2024,12,25))).isFalse();
        }
    }
}