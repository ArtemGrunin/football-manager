package ua.com.footballmanagerapp.dto.request;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class TeamRequestDto {
    private String name;
    private String city;
    private String country;
    private BigDecimal budget;
    private BigDecimal transferCommission;
}
