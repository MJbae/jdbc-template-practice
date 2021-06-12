package mj.jdbc_template_test.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import mj.jdbc_template_test.domain.user.User;

public class UserResponseDto {

    @JsonProperty("first_name")
    private final String firstName;

    @JsonProperty("last_name")
    private final String lastName;

    @JsonProperty("yearly_income")
    private final long yearlyIncome;

    public UserResponseDto(User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.yearlyIncome = user.getYearlyIncome();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public long getYearlyIncome() {
        return yearlyIncome;
    }
}
