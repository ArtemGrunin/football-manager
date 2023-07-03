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
    private LocalDate careerStartDate;
    private int experienceMonths;
    private int age;
    private Long teamId;
}
