package com.backend.capitole.adapter.inbound.rest.controller;

import com.backend.capitole.CapitoleApplication;
import com.backend.capitole.core.application.port.inbound.PriceInPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = CapitoleApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-test.properties")
class PriceControllerTest {

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @Autowired
    PriceInPort priceInPort;
    @Autowired
    private MockMvc mockMvc;
    private String anHourAgo;

    @BeforeEach
    public void setUp() {
        anHourAgo = LocalDateTime.now().minusHours(1).format(dateTimeFormatter);
    }

    //Test 1: petición a las 10:00 del día 14 del producto 35455   para la brand 1 (ZARA)
    @Test
    void successfullyRetrievePriceForDatetime20200614T100000() throws Exception {
        String datetimeRequested = LocalDateTime.parse("2020-06-14 10:00:00", dateTimeFormatter).format(dateTimeFormatter);

        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/capitole/prices/brands/{brandId}/products/{productId}/date/{date}",
                                1, 35455, datetimeRequested)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        result
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productId").value("35455"))
                .andExpect(jsonPath("$.brandId").value("1"))
                .andExpect(jsonPath("$.priceList").value("1"))
                .andExpect(jsonPath("$.startDate").value("2020-06-14T00:00"))
                .andExpect(jsonPath("$.endDate").value("2020-12-31T23:59:59"))
                .andExpect(jsonPath("$.currency").value("EUR"))
                .andExpect(jsonPath("$.price").value(36.0))
        ;
    }

    //Test 2: petición a las 16:00 del día 14 del producto 35455   para la brand 1 (ZARA)
    @Test
    void successfullyRetrievePriceForDatetime20200614T160000() throws Exception {
        String datetimeRequested = LocalDateTime.parse("2020-06-14 16:00:00", dateTimeFormatter).format(dateTimeFormatter);

        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/capitole/prices/brands/{brandId}/products/{productId}/date/{date}",
                                1, 35455, datetimeRequested)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        result
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productId").value("35455"))
                .andExpect(jsonPath("$.brandId").value("1"))
                .andExpect(jsonPath("$.priceList").value("2"))
                .andExpect(jsonPath("$.startDate").value("2020-06-14T15:00"))
                .andExpect(jsonPath("$.endDate").value("2020-06-14T18:30"))
                .andExpect(jsonPath("$.currency").value("EUR"))
                .andExpect(jsonPath("$.price").value(25.0))
        ;
    }

    //Test 3: petición a las 21:00 del día 14 del producto 35455   para la brand 1 (ZARA)
    @Test
    void successfullyRetrievePriceForDatetime20200614T210000() throws Exception {
        String datetimeRequested = LocalDateTime.parse("2020-06-14 21:00:00", dateTimeFormatter).format(dateTimeFormatter);

        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/capitole/prices/brands/{brandId}/products/{productId}/date/{date}",
                                1, 35455, datetimeRequested)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        result
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productId").value("35455"))
                .andExpect(jsonPath("$.brandId").value("1"))
                .andExpect(jsonPath("$.priceList").value("1"))
                .andExpect(jsonPath("$.startDate").value("2020-06-14T00:00"))
                .andExpect(jsonPath("$.endDate").value("2020-12-31T23:59:59"))
                .andExpect(jsonPath("$.currency").value("EUR"))
                .andExpect(jsonPath("$.price").value(36.0))
        ;
    }

    //Test 4: petición a las 10:00 del día 15 del producto 35455   para la brand 1 (ZARA)
    @Test
    void successfullyRetrievePriceForDatetime20200615T100000() throws Exception {
        String datetimeRequested = LocalDateTime.parse("2020-06-15 10:00:00", dateTimeFormatter).format(dateTimeFormatter);

        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/capitole/prices/brands/{brandId}/products/{productId}/date/{date}",
                                1, 35455, datetimeRequested)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        result
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productId").value("35455"))
                .andExpect(jsonPath("$.brandId").value("1"))
                .andExpect(jsonPath("$.priceList").value("3"))
                .andExpect(jsonPath("$.startDate").value("2020-06-15T00:00"))
                .andExpect(jsonPath("$.endDate").value("2020-06-15T11:00"))
                .andExpect(jsonPath("$.currency").value("EUR"))
                .andExpect(jsonPath("$.price").value(31.0))
        ;
    }

    //Test 5: petición a las 21:00 del día 16 del producto 35455   para la brand 1 (ZARA)
    @Test
    void successfullyRetrievePriceForDatetime20200616T210000() throws Exception {
        String datetimeRequested = LocalDateTime.parse("2020-06-16 21:00:00", dateTimeFormatter).format(dateTimeFormatter);

        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/capitole/prices/brands/{brandId}/products/{productId}/date/{date}",
                                1, 35455, datetimeRequested)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        result
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productId").value("35455"))
                .andExpect(jsonPath("$.brandId").value("1"))
                .andExpect(jsonPath("$.priceList").value("4"))
                .andExpect(jsonPath("$.startDate").value("2020-06-15T16:00"))
                .andExpect(jsonPath("$.endDate").value("2020-12-31T23:59:59"))
                .andExpect(jsonPath("$.currency").value("EUR"))
                .andExpect(jsonPath("$.price").value(40.0))
        ;
    }

    @Test
    void whenThereIsNoPriceReturnNotFoundStatus() throws Exception {
        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/capitole/prices/brands/{brandId}/products/{productId}/date/{date}",
                                1, 2, anHourAgo)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        result
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("404"))
                .andExpect(jsonPath("$.error").value("Price not found."))
        ;
    }

    @Test
    void whenThereIsAnInvalidBrandReturnBadRequestStatus() throws Exception {
        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/capitole/prices/brands/{brandId}/products/{productId}/date/{date}",
                                -1, 35455, anHourAgo)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        result
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$.[0].status").value("400"))
                .andExpect(jsonPath("$.[0].error").value("Field brand_id is invalid. Only positive integer values allowed."))
        ;
    }

    @Test
    void whenThereIsAnInvalidProductReturnBadRequestStatus() throws Exception {
        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/capitole/prices/brands/{brandId}/products/{productId}/date/{date}",
                                1, -35455, anHourAgo)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        result
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$.[0].status").value("400"))
                .andExpect(jsonPath("$.[0].error").value("Field product_id is invalid. Only positive integer values allowed."))
        ;
    }

    @Test
    void whenThereIsAnInvalidDateReturnBadRequestStatus() throws Exception {
        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/capitole/prices/brands/{brandId}/products/{productId}/date/{date}",
                                1, 35455, "wrong-datetime")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        result
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$.[0].status").value("400"))
                .andExpect(jsonPath("$.[0].error").value("Field date has wrong datetime format. It should be yyyy-MM-dd HH:mm:ss"))
        ;
    }

    @Test
    void whenThereIsAnExceptionReturnBadRequestStatus() throws Exception {
        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/capitole/prices/brands/{brandId}/products/{productId}/date/{date}",
                                "aaa", 2, anHourAgo)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        result
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("400"))
                .andExpect(jsonPath("$.error").value("Failed to convert value of type 'java.lang.String' to required type 'int'; nested exception is java.lang.NumberFormatException: For input string: \"aaa\""))
        ;
    }

}