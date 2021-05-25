package mj.jdbc_template_test.web.dto;

public class GitHubUser {

    private String login;
    private String name;

    public GitHubUser() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "GitHubUser{" +
                "login='" + login + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

