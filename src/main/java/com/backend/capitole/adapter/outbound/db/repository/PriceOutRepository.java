package com.backend.capitole.adapter.outbound.db.repository;

import com.backend.capitole.adapter.outbound.db.entity.PriceBuilder;
import com.backend.capitole.adapter.outbound.db.entity.PriceEntity;
import com.backend.capitole.core.application.port.outbound.PriceOutPort;
import com.backend.capitole.core.domain.model.Price;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.stream.Stream;

@Repository
public class PriceOutRepository implements PriceOutPort {

    private static final String QUERY_PRICES_BY_DATE = "select * from prices " +
            "where brand_id = :brandId " +
            "and product_id = :productId " +
            "and start_date <= :date " +
            "and end_date >= :date";
    private static final Logger logger = LoggerFactory.getLogger(PriceOutRepository.class);
    private final NamedParameterJdbcOperations jdbcTemplate;

    public PriceOutRepository(@Autowired NamedParameterJdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Stream<Price> getPricesByDate(LocalDateTime date, Integer productId, Integer brandId) {
        logger.info("Start execution of query {} with values brandId = {}, productId = {} and date = {}",
                QUERY_PRICES_BY_DATE, brandId, productId, date);
        return jdbcTemplate.query(
                        QUERY_PRICES_BY_DATE,
                        new MapSqlParameterSource()
                                .addValue("brandId", brandId)
                                .addValue("productId", productId)
                                .addValue("date", date.toString()),
                        new PriceRowMapper()).stream()
                .map(PriceBuilder::build);
    }

    private static class PriceRowMapper implements RowMapper<PriceEntity> {
        @Override
        public PriceEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new PriceEntity(
                    rs.getInt("price_list"),
                    rs.getInt("brand_id"),
                    rs.getObject("start_date", LocalDateTime.class),
                    rs.getObject("end_date", LocalDateTime.class),
                    rs.getInt("product_id"),
                    rs.getInt("priority"),
                    rs.getDouble("price"),
                    rs.getString("curr")
            );
        }
    }
}
