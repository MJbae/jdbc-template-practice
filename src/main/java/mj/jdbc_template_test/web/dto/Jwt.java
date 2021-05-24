package mj.jdbc_template_test.web.dto;

public class Jwt {
    private final String jwt;

    public Jwt(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}
