package mj.jdbc_template_test.web.dto;

public class GithubUserInfoDto {

    public GithubUserInfoDto() {
    }

    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "GithubUserInfoDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
