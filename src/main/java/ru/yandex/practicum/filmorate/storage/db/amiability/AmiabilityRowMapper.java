package ru.yandex.practicum.filmorate.storage.db.amiability;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.Amiability;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AmiabilityRowMapper implements RowMapper<Amiability> {
    @Override
    public Amiability mapRow(ResultSet rs, int rowNum) throws SQLException {
        Amiability amiability = new Amiability();
        amiability.setUserId(rs.getLong("user_id"));
        amiability.setFriendId(rs.getLong("friend_id"));
        amiability.setStatus(rs.getBoolean("status"));

        return amiability;
    }
}
