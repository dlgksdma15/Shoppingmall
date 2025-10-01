package com.nhnacademy.shoppingmall.common.util;


import org.apache.commons.dbcp2.BasicDataSource;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.time.Duration;

public class DbUtils {
    public DbUtils(){
        throw new IllegalStateException("Utility class");
    }

    private static final DataSource DATASOURCE;

    static {
        BasicDataSource basicDataSource = new BasicDataSource();

        try {
            basicDataSource.setDriver(new com.mysql.cj.jdbc.Driver());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //todo#1-1 {ip},{database},{username},{password} 설정
        //todo#1-2 initialSize, maxTotal, maxIdle, minIdle 은 모두 5로 설정합니다.
        //todo#1-3 Validation Query를 설정하세요
        basicDataSource.setUrl("jdbc:mysql://10.116.64.14:13306/nhn_academy_32");
//        basicDataSource.setUrl("jdbc:mysql://s4.java21.net:13306/nhn_academy_32");
        basicDataSource.setUsername("nhn_academy_32");
        basicDataSource.setPassword("EJdrW!(bf]HjT9a5");
        basicDataSource.setInitialSize(5); // 최초 connection pool 시작될 때 초기 Connection 개수
        basicDataSource.setMaxTotal(5); // 최대로 사용할 수 있는 Connection 개수
        basicDataSource.setMaxIdle(5); // Connection pool에 반납할 때 최대 유지될 수 있는 Connection 수
        basicDataSource.setMinIdle(5); // 최소한으로 유지될 Connection 수

        basicDataSource.setMaxWait(Duration.ofSeconds(2));// 풀이 예외를 발생시키기 전에 연결이 반활될 때까지 대기하는 시간(밀리초 단위)
        basicDataSource.setValidationQuery("select 1"); // Connection pool에 Connection을 반환하기 전에 해당 풀의 연결여부를 확인
        basicDataSource.setTestOnBorrow(true); // pool에서 Connection을 사용하기 위해서(Connection pool에서 Connection pool에서 Connection 얻어올 때) 유효성 검사 여부

        //todo#1-4 적절히 변경하세요
        DATASOURCE = basicDataSource;

    }

    public static DataSource getDataSource(){
        return DATASOURCE;
    }

}
