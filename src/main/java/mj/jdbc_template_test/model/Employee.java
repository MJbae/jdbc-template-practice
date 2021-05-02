package mj.jdbc_template_test.model;
import java.util.HashMap;
import java.util.Map;

public class Employee {

    private long id;
    private String firstName;
    private String lastName;
    private long yearlyIncome;

    public Employee() {
    }

    public Employee(String firstName, String lastName, long yearlyIncome) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.yearlyIncome = yearlyIncome;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> values = new HashMap<>();
        values.put("first_name", firstName);
        values.put("last_name", lastName);
        values.put("yearly_income", yearlyIncome);

        return values;
    }

    public long getId() {
        return id;
    }

    public long getYearlyIncome() {
        return yearlyIncome;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setYearlyIncome(long yearlyIncome) {
        this.yearlyIncome = yearlyIncome;
    }
}
