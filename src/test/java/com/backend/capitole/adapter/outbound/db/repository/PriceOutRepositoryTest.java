package com.backend.capitole.adapter.outbound.db.repository;

import com.backend.capitole.adapter.outbound.db.entity.PriceEntity;
import com.backend.capitole.core.domain.model.Price;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceOutRepositoryTest {

    @Mock
    NamedParameterJdbcTemplate jdbcTemplate;
    @InjectMocks
    PriceOutRepository priceOutRepository;
    private LocalDateTime anHourAgo;
    private Integer productId;
    private Integer brandId;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;

    @BeforeEach
    public void setUp() {
        anHourAgo = LocalDateTime.now().minusHours(1);
        productId = 1;
        brandId = 2;
        fromDate = yesterday();
        toDate = LocalDateTime.now();
    }

    @Test
    void successfullyReturnOnePriceItem() {
        when(jdbcTemplate.query(anyString(), any(MapSqlParameterSource.class), any(RowMapper.class)))
                .thenReturn(aPricesList());

        Stream<Price> streamResult = priceOutRepository.getPricesByDate(anHourAgo, productId, brandId);
        List<Price> result = streamResult.collect(Collectors.toList());

        assertEquals(1, result.size());
        Price firstPrice = result.get(0);
        assertEquals(1, firstPrice.getProductId());
        assertEquals(2, firstPrice.getBrandId());
        assertEquals(3, firstPrice.getPriceList());
        assertEquals(fromDate, firstPrice.getStartDate());
        assertEquals(toDate, firstPrice.getEndDate());
        assertEquals(0, firstPrice.getPriority());
        assertEquals(12.34, firstPrice.getPriceValue());
        assertEquals("pesos", firstPrice.getCurrency());
    }

    @Test
    void successfullyReturnTwoPriceItemsWhenBothShareFilters() {
        when(jdbcTemplate.query(anyString(), any(MapSqlParameterSource.class), any(RowMapper.class)))
                .thenReturn(aPricesList(2));

        Stream<Price> streamResult = priceOutRepository.getPricesByDate(anHourAgo, productId, brandId);
        List<Price> result = streamResult.collect(Collectors.toList());

        assertEquals(2, result.size());
        Price firstPrice = result.get(0);
        assertEquals(1, firstPrice.getProductId());
        assertEquals(2, firstPrice.getBrandId());
        assertEquals(3, firstPrice.getPriceList());
        assertEquals(fromDate, firstPrice.getStartDate());
        assertEquals(toDate, firstPrice.getEndDate());
        assertEquals(0, firstPrice.getPriority());
        assertEquals(12.34, firstPrice.getPriceValue());
        assertEquals("pesos", firstPrice.getCurrency());
        Price secondPrice = result.get(1);
        assertEquals(11, secondPrice.getProductId());
        assertEquals(12, secondPrice.getBrandId());
        assertEquals(13, secondPrice.getPriceList());
        assertEquals(fromDate, secondPrice.getStartDate());
        assertEquals(toDate, secondPrice.getEndDate());
        assertEquals(1, secondPrice.getPriority());
        assertEquals(22.34, secondPrice.getPriceValue());
        assertEquals("pesos", secondPrice.getCurrency());
    }

    @Test
    void successfullyReturnZeroPriceWhenFilteredAllOut() {
        when(jdbcTemplate.query(anyString(), any(MapSqlParameterSource.class), any(RowMapper.class)))
                .thenReturn(Collections.emptyList());

        Stream<Price> result = priceOutRepository.getPricesByDate(anHourAgo, productId, brandId);

        assertEquals(0, result.count());
    }

    private List<PriceEntity> aPricesList() {
        return aPricesList(1);
    }

    private List<PriceEntity> aPricesList(int amountOfPrices) {
        List<PriceEntity> priceEntities = new ArrayList<>();
        for (int i = 0; i < amountOfPrices; i++) {
            priceEntities.add(aPriceEntity(i));
        }
        return priceEntities;
    }

    private PriceEntity aPriceEntity(int priority) {
        int decimal = priority * 10;
        return PriceEntity.builder()
                .productId(decimal + 1)
                .brandId(decimal + 2)
                .priceList(decimal + 3)
                .priority(priority)
                .startDate(fromDate)
                .endDate(toDate)
                .price(decimal + 12.34)
                .currency("pesos")
                .build();
    }

    private LocalDateTime yesterday() {
        return LocalDateTime.now().minusDays(1);
    }
}