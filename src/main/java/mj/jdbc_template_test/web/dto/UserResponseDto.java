package mj.jdbc_template_test.web.dto;

import mj.jdbc_template_test.domain.user.User;

public class UserResponseDto {

    private String firstName;
    private String lastName;
    private long yearlyIncome;

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
