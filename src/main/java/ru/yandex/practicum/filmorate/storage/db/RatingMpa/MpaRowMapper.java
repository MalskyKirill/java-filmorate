package ru.yandex.practicum.filmorate.storage.db.RatingMpa;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;


public class MpaRowMapper implements RowMapper<Mpa> {

    @Override
    public Mpa mapRow(ResultSet rs, int rowNum) throws SQLException {
        Mpa ratingMpa = new Mpa();
        ratingMpa.setId(rs.getInt("mpa_id"));
        ratingMpa.setRating(rs.getString("rating"));
        return ratingMpa;
    }
}
