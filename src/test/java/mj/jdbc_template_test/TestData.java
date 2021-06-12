package mj.jdbc_template_test;

import mj.jdbc_template_test.domain.user.User;

public class TestData {
    public final static String FIRST_NAME_MJ = "M";
    public final static String LAST_NAME_MJ = "J";
    public final static long INCOME_MJ = 10000000;
    public final static Long GITHUB_ID_MJ = 123L;

    public final static String FIRST_NAME_T = "T";
    public final static String LAST_NAME_T = "T";
    public final static long INCOME_T = 123456234;
    public final static Long GITHUB_ID_T = 9123L;

    public static final User userMJ =
            new User(FIRST_NAME_MJ, LAST_NAME_MJ, GITHUB_ID_MJ, INCOME_MJ);

    public static final User userTT =
            new User(FIRST_NAME_T, LAST_NAME_T, GITHUB_ID_T, INCOME_T);

}
