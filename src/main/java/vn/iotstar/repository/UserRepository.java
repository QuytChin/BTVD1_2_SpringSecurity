package vn.iotstar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vn.iotstar.entity.Role;
import vn.iotstar.entity.User;

/**
 * Repository cua VD2, query truc tiep SQL Server bang JdbcTemplate.
 * Bao gom cac ham tuong ung voi tai lieu: findByUsername, findByEmail,
 * findByUsernameOrEmail va findById.
 */
@Repository
public class UserRepository {

    private static final String SELECT_USER = """
            SELECT u.id, u.username, u.email, u.password, u.full_name,
                   u.images, u.enabled, r.id AS role_id, r.name AS role_name
            FROM dbo.users u
            INNER JOIN dbo.roles r ON r.id = u.role_id
            """;

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<User> findByUsername(String username) {
        return queryOne(SELECT_USER + " WHERE LOWER(u.username) = LOWER(?)", username);
    }

    public Optional<User> findByEmail(String email) {
        return queryOne(SELECT_USER + " WHERE LOWER(u.email) = LOWER(?)", email);
    }

    public Optional<User> findByUsernameOrEmail(String username, String email) {
        return queryOne(
                SELECT_USER + " WHERE LOWER(u.username) = LOWER(?) OR LOWER(u.email) = LOWER(?)",
                username, email);
    }

    public Optional<User> findById(Long id) {
        return queryOne(SELECT_USER + " WHERE u.id = ?", id);
    }

    private Optional<User> queryOne(String sql, Object... args) {
        List<User> users = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Role role = new Role(rs.getLong("role_id"), rs.getString("role_name"));
            return new User(
                    rs.getLong("id"),
                    rs.getString("username"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("full_name"),
                    rs.getString("images"),
                    rs.getBoolean("enabled"),
                    role);
        }, args);

        return users.stream().findFirst();
    }
}
