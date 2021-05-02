package mj.jdbc_template_test.domain.user;
import java.util.HashMap;
import java.util.Map;

public class User {

    private long id;
    private String firstName;
    private String lastName;
    private String githubId;
    private long yearlyIncome;

    public User() {
    }

    public User(String firstName, String lastName, String githubId, long yearlyIncome) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.githubId = githubId;
        this.yearlyIncome = yearlyIncome;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> values = new HashMap<>();
        values.put("first_name", firstName);
        values.put("last_name", lastName);
        values.put("github_id", githubId);
        values.put("yearly_income", yearlyIncome);

        return values;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGithubId() {
        return githubId;
    }

    public long getYearlyIncome() {
        return yearlyIncome;
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

    public void setGithubId(String githubId) {
        this.githubId = githubId;
    }

    public void setYearlyIncome(long yearlyIncome) {
        this.yearlyIncome = yearlyIncome;
    }
}
