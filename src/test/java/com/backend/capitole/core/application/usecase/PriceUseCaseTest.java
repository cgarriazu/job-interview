package com.backend.capitole.core.application.usecase;

import com.backend.capitole.core.application.port.outbound.PriceOutPort;
import com.backend.capitole.core.domain.model.Price;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceUseCaseTest {

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Mock
    PriceOutPort priceOutPort;
    @InjectMocks
    PriceUseCase priceUseCase;
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
    void successfullyGetPriceWhenOnlyOneReturned() {
        when(priceOutPort.getPricesByDate(any(LocalDateTime.class), anyInt(), anyInt()))
                .thenReturn(aStreamOfPrices());

        Price result = priceUseCase.getPriceByDate(anHourAgo, productId, brandId);

        assertEquals(1, result.getProductId());
        assertEquals(2, result.getBrandId());
        assertEquals(3, result.getPriceList());
        assertEquals(0, result.getPriority());
        assertEquals(fromDate, result.getStartDate());
        assertEquals(toDate, result.getEndDate());
        assertEquals(12.34, result.getPriceValue());
        assertEquals("pesos", result.getCurrency());
    }

    @Test
    void successfullyGetPrioritizedPriceWhenMoreThanOneReturned() {
        when(priceOutPort.getPricesByDate(any(LocalDateTime.class), anyInt(), anyInt()))
                .thenReturn(aStreamOfPrices(2));

        Price result = priceUseCase.getPriceByDate(anHourAgo, productId, brandId);

        assertEquals(1, result.getProductId());
        assertEquals(2, result.getBrandId());
        assertEquals(3, result.getPriceList());
        assertEquals(1, result.getPriority());
        assertEquals(fromDate, result.getStartDate());
        assertEquals(toDate, result.getEndDate());
        assertEquals(12.34, result.getPriceValue());
        assertEquals("pesos", result.getCurrency());
    }

    private Stream<Price> aStreamOfPrices() {
        return aStreamOfPrices(1);
    }

    private Stream<Price> aStreamOfPrices(int amountOfPrices) {
        return somePrices(amountOfPrices).stream();
    }

    private List<Price> somePrices(int amountOfPrices) {
        List<Price> prices = new ArrayList<Price>();
        for (int i = 0; i < amountOfPrices; i++) {
            prices.add(aPrice(i));
        }
        return prices;
    }

    private Price aPrice(int priority) {
        return Price.builder()
                .productId(1)
                .brandId(2)
                .priceList(3)
                .startDate(fromDate)
                .endDate(toDate)
                .priority(priority)
                .priceValue(12.34)
                .currency("pesos")
                .build();
    }

    private LocalDateTime yesterday() {
        return LocalDateTime.now().minusDays(1);
    }

}