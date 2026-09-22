package vn.iotstar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vn.iotstar.entity.Role;

/** Repository JDBC truy van truc tiep SQL Server. */
@Repository
public class RoleRepository {

    private final JdbcTemplate jdbcTemplate;

    public RoleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Role> findByName(String name) {
        String sql = "SELECT id, name FROM dbo.roles WHERE name = ?";
        List<Role> roles = jdbcTemplate.query(sql,
                (rs, rowNum) -> new Role(rs.getLong("id"), rs.getString("name")),
                name);
        return roles.stream().findFirst();
    }
}
