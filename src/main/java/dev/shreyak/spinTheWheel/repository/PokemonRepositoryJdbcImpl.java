package dev.shreyak.spinTheWheel.repository;

import dev.shreyak.spinTheWheel.model.Pokemon;
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
public class PokemonRepositoryJdbcImpl implements PokemonRepository{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PokemonRepositoryJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static Pokemon mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Pokemon(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("url")
        );
    }

    @Override
    public void addPokemon(Pokemon pokemon) {
        String sql = "INSERT INTO Pokemon(id, name, url) values (?, ?, ?)";
        jdbcTemplate.update(sql, pokemon.id(), pokemon.name(), pokemon.url());
    }

    @Override
    public List<Pokemon> getAllPokemon() {
        String sql = "SELECT * FROM Pokemon";
        return jdbcTemplate.query(sql, PokemonRepositoryJdbcImpl::mapRow);
    }

    @Override
    public Optional<Pokemon> getPokemonById(Integer id) {
        String sql = "SELECT * FROM Pokemon WHERE id=?";
        List<Pokemon> pokemons = jdbcTemplate.query(sql, PokemonRepositoryJdbcImpl::mapRow, id);
        if(pokemons != null && !pokemons.isEmpty()) {
            return Optional.of(pokemons.get(0));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void updatePokemon(Pokemon pokemon) {
        String sql = "UPDATE Pokemon set id=?, name=?, url=? where id=?";
        jdbcTemplate.update(sql, pokemon.id(), pokemon.name(), pokemon.url(), pokemon.id());
    }

    @Override
    public void deletePokemonById(Integer id) {
        String sql = "DELETE FROM Pokemon WHERE id=?";
        jdbcTemplate.update(sql, id);
    }
}
