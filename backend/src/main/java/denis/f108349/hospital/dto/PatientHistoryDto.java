package denis.f108349.hospital.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PatientHistoryDto {
    private String diagnosis;
    private String treatment;
    private int sickLeaveDays;
    private LocalDateTime visitDate;
}

