package ua.com.footballmanagerapp.dto.response;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class TeamResponseDto {
    private Long id;
    private String name;
    private String city;
    private String country;
    private BigDecimal budget;
    private BigDecimal transferCommission;
}
