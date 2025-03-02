package domain;

public record AttendanceStatistics(int present, int late, int absent, AlertCode alertCode) {
}
