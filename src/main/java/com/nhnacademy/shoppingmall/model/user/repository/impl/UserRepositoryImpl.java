package com.nhnacademy.shoppingmall.model.user.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.model.user.domain.User;
import com.nhnacademy.shoppingmall.model.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class UserRepositoryImpl implements UserRepository {

    @Override
    public Optional<User> findByUserIdAndUserPassword(String userId, String userPassword) {
        /*todo#3-1 회원의 아이디와 비밀번호를 이용해서 조회하는 코드 입니다.(로그인)
          해당 코드는 SQL Injection이 발생합니다. SQL Injection이 발생하지 않도록 수정하세요.
         */
        Connection connection = DbConnectionThreadLocal.getConnection();  // threadLocal이기 떄문에 재사용해야해서
        String sql = """
                        select user_id, user_name, user_password, user_birth,
                        user_auth, user_point, created_at, latest_login_at from Users
                        where user_id = ? and user_password = ?
                """;

        log.debug("sql:{}",sql);

        try(PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, userId);
            psmt.setString(2, userPassword);

            ResultSet rs = psmt.executeQuery();
            if (rs.next()) {
                User user = new User(
                        rs.getString("user_id"),
                        rs.getString("user_name"),
                        rs.getString("user_password"),
                        rs.getString("user_birth"),
                        User.Auth.valueOf(rs.getString("user_auth")),
                        rs.getInt("user_point"),
                        Objects.nonNull(rs.getTimestamp("created_at")) ? rs.getTimestamp("created_at").toLocalDateTime() : null,
                        Objects.nonNull(rs.getTimestamp("latest_login_at")) ? rs.getTimestamp("latest_login_at").toLocalDateTime() : null
                );
                return Optional.of(user);
            }
        } catch (SQLException e) {
            log.error("Error finding user by id and password: {}", e.getMessage(), e);
            // 트랜잭션 롤백을 위해 에러 상태 기록
            DbConnectionThreadLocal.setSqlError(true);
            throw new RuntimeException("데이터베이스 조회 중 오류 발생", e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<User> findById(String userId) {
        //todo#3-2 회원조회
        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = """
                        select user_id, user_name, user_password, user_birth,
                        user_auth, user_point, created_at, latest_login_at from Users
                        where user_id = ?
                    """;
        log.debug("SQL: {}", sql);

        try(PreparedStatement psmt = connection.prepareStatement(sql)) {

            // 2. 파라미터 설정 (쿼리 실행 전에 반드시!)
            psmt.setString(1, userId);

            // 3. 쿼리 실행 (executeQuery()) 결과를 내부 try()에 넣어 ResultSet을 안전하게 관리합니다.
            try(ResultSet rs = psmt.executeQuery()){

                if(rs.next()){
                    // ... User 객체 생성 및 반환 로직
                    User user = new User(
                            rs.getString("user_id"),
                            rs.getString("user_name"),
                            rs.getString("user_password"),
                            rs.getString("user_birth"),
                            User.Auth.valueOf(rs.getString("user_auth")),
                            rs.getInt("user_point"),
                            Objects.nonNull(rs.getTimestamp("created_at")) ? rs.getTimestamp("created_at").toLocalDateTime() : null,
                            Objects.nonNull(rs.getTimestamp("latest_login_at")) ? rs.getTimestamp("latest_login_at").toLocalDateTime() : null
                    );
                    return Optional.of(user);
                }
            }
        } catch (SQLException e) {
            log.error("Error finding user by id: {}", e.getMessage(), e);
            throw new RuntimeException("데이터베이스 조회 중 오류 발생", e);
        }
        return Optional.empty();
    }

    @Override
    public int save(User user) {
        //todo#3-3 회원등록, executeUpdate()을 반환합니다.

        Connection connection = DbConnectionThreadLocal.getConnection();
        int result = 0;
        String sql = """
                insert into Users(user_id, user_name, user_password, user_birth,
                user_auth, user_point, created_at) values (?,?,?,?,?,?,?)
                """;
        log.debug("SQL: {}", sql);

        try(PreparedStatement psmt = connection.prepareStatement(sql)) {

            psmt.setString(1, user.getUserId());
            psmt.setString(2, user.getUserName());
            psmt.setString(3, user.getUserPassword());
            psmt.setString(4, user.getUserBirth());
            psmt.setString(5, user.getUserAuth().toString()); // Enum을 문자열로 저장
            psmt.setInt(6, user.getUserPoint());

            psmt.setTimestamp(7, Timestamp.valueOf(user.getCreatedAt()));

            result = psmt.executeUpdate();
        } catch (SQLException e) {
            log.error("Error saving user: {}", e.getMessage(), e);
            DbConnectionThreadLocal.setSqlError(true);
            // 예외 메시지에 실제 SQL 예외의 클래스 이름을 포함
            throw new RuntimeException("회원 등록 중 오류 발생: " + e.getClass().getName(), e);
        }
        return result;
    }

    @Override
    public int deleteByUserId(String userId) {
        //todo#3-4 회원삭제, executeUpdate()을 반환합니다.
        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "delete from Users where user_id = ?";
        log.debug("SQL: {}", sql);

        int result = 0;
        try(PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1,userId);

            result = psmt.executeUpdate();

        } catch (SQLException e) {
            log.error("Error deleting user with id {}: {}", userId, e.getMessage(), e);
            // 예외 발생 시 트랜잭션 롤백을 위해 에러 상태 기록
            DbConnectionThreadLocal.setSqlError(true);
            throw new RuntimeException("회원 삭제 중 오류 발생", e);
        }
        return result;
    }

    @Override
    public int update(User user) {
        //todo#3-5 회원수정, executeUpdate()을 반환합니다.
        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "update Users set user_name = ?, user_password = ?, user_birth = ?, " +
                "user_auth = ?, user_point = ? " +
                "where user_id = ?";
        log.debug("SQL: {}", sql);

        int result = 0;
        try(PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1,user.getUserName());
            psmt.setString(2,user.getUserPassword());
            psmt.setString(3,user.getUserBirth());
            psmt.setString(4,user.getUserAuth().toString());
            psmt.setInt(5,user.getUserPoint());

            psmt.setString(6, user.getUserId());

            result = psmt.executeUpdate();

        } catch (SQLException e) {
            log.error("Error updating user with id {}: {}", user.getUserId(), e.getMessage(), e);
            DbConnectionThreadLocal.setSqlError(true);
            throw new RuntimeException("회원 정보 수정 중 오류 발생", e);
        }

        return result;
    }

    @Override
    public int updateLatestLoginAtByUserId(String userId, LocalDateTime latestLoginAt) {
        // todo#3-6, 마지막 로그인 시간 업데이트, executeUpdate()을 반환합니다.
        Connection connection = DbConnectionThreadLocal.getConnection();
        int result = 0;

        String sql = "UPDATE Users SET latest_login_at = ? WHERE user_id = ?";

        log.debug("SQL: {}", sql);

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {

            // latest_login_at 바인딩 (LocalDateTime -> Timestamp)
            psmt.setTimestamp(1, Timestamp.valueOf(latestLoginAt));

            psmt.setString(2, userId);

            result = psmt.executeUpdate();

        } catch (SQLException e) {
            log.error("Error updating latest login time for user {}: {}", userId, e.getMessage(), e);
            DbConnectionThreadLocal.setSqlError(true);
            throw new RuntimeException("마지막 로그인 시간 업데이트 중 오류 발생", e);
        }

        return result;
    }

    @Override
    public int countByUserId(String userId) { // 회원 가입시 중복 확인
        // todo#3-7 userId와 일치하는 회원의 count를 반환합니다.
        Connection connection = DbConnectionThreadLocal.getConnection();
        int count = 0;

        String sql = "SELECT COUNT(*) FROM Users WHERE user_id = ?";

        log.debug("SQL: {}", sql);

        try (PreparedStatement psmt = connection.prepareStatement(sql)) {

            // 1. userId 바인딩
            psmt.setString(1, userId);

            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) {
                    // 2. COUNT 함수의 결과(첫 번째 컬럼)를 정수형으로 추출
                    count = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            log.error("Error counting user with id {}: {}", userId, e.getMessage(), e);
            throw new RuntimeException("회원 수 카운트 중 오류 발생", e);
        }

        return count;
    }
}
