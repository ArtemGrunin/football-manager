package ua.com.footballmanagerapp.dto.request;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerRequestDto {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private LocalDate careerStartDate;
    private int experienceMonths;
    private Long teamId;
}
