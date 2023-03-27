package dev.shreyak.spinTheWheel.repository;

import dev.shreyak.spinTheWheel.model.SpinItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class SpinRepositoryJdbcImpl implements SpinRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SpinRepositoryJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static SpinItem mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new SpinItem(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("url")
        );
    }

    @Override
    public void addPokemon(SpinItem spinItem) {
        String sql = "INSERT INTO Pokemon(id, name, url) values (?, ?, ?)";
        jdbcTemplate.update(sql, spinItem.id(), spinItem.name(), spinItem.url());
    }

    @Override
    public List<SpinItem> getAllPokemon() {
        String sql = "SELECT * FROM Pokemon";
        return jdbcTemplate.query(sql, SpinRepositoryJdbcImpl::mapRow);
    }

    @Override
    public Optional<SpinItem> getPokemonById(Integer id) {
        String sql = "SELECT * FROM Pokemon WHERE id=?";
        List<SpinItem> spinItems = jdbcTemplate.query(sql, SpinRepositoryJdbcImpl::mapRow, id);
        if(spinItems != null && !spinItems.isEmpty()) {
            return Optional.of(spinItems.get(0));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void updatePokemon(SpinItem spinItem) {
        String sql = "UPDATE Pokemon set id=?, name=?, url=? where id=?";
        jdbcTemplate.update(sql, spinItem.id(), spinItem.name(), spinItem.url(), spinItem.id());
    }

    @Override
    public void deletePokemonById(Integer id) {
        String sql = "DELETE FROM Pokemon WHERE id=?";
        jdbcTemplate.update(sql, id);
    }
}
