package com.nhnacademy.shoppingmall.common.mvc.transaction;

import com.nhnacademy.shoppingmall.common.util.DbUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

// Connection 할당 및 트랜잭션 생명주기 관리를 위해 쓰는 DbConnectionThreadLocal
@Slf4j
public class DbConnectionThreadLocal {
    private static final ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<>(); // 웹 요청 단위로 DB 연결과 트랜잭션을 관리하기 위함.
    private static final ThreadLocal<Boolean> sqlErrorThreadLocal = ThreadLocal.withInitial(() -> false);

    public static void initialize() throws SQLException {

        //todo#2-1 - connection pool에서 connectionThreadLocal에 connection을 할당합니다.
        Connection connection = DbUtils.getDataSource().getConnection(); // 커넥션풀에서 데이터베이스 연결 하나 가져오기.
        connectionThreadLocal.set(connection) ; // 연결을 현재 스레드에 할당

        //todo#2-2 connectiond의 Isolation level을 READ_COMMITED를 설정 합니다.
        connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

        //todo#2-3 auto commit을 false로 설정합니다.
        connection.setAutoCommit(false);
    }

    public static Connection getConnection(){
        return connectionThreadLocal.get();
    }

    public static void setSqlError(boolean sqlError){
        sqlErrorThreadLocal.set(sqlError);
    }

    public static boolean getSqlError(){
        return sqlErrorThreadLocal.get();
    }

    public static void reset(){
        Connection connection = connectionThreadLocal.get();
        if(connection == null){
            return;
        }
        //todo#2-5 getSqlError() 에러가 존재하면 rollback 합니다.
        try{
            if(getSqlError()){
                log.info("Trancsaction Rollback: SQL 문법 에러");
            } else{
                //todo#2-6 getSqlError() 에러가 존재하지 않다면 commit 합니다.
                connection.commit();
            }
            //todo#2-4 사용이 완료된 connection은 close를 호출하여 connection pool에 반환합니다.
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            //todo#2-7 현제 사용하고 있는 connection을 재사용할 수 없도록 connectionThreadLocal을 초기화 합니다.
            connectionThreadLocal.remove();
            sqlErrorThreadLocal.remove();
        }

    }
}
