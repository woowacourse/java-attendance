package attendance.domain;

public record ExpulsionCandidate(String name,
                                 int absent,
                                 int late,
                                 AcademicStatus status) {
}
