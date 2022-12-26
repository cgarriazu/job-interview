package com.backend.capitole.adapter.inbound.rest.controller;

import com.backend.capitole.adapter.inbound.rest.configuration.annotation.ErrorHandler;
import com.backend.capitole.adapter.inbound.rest.controller.dto.PriceDto;
import com.backend.capitole.adapter.inbound.rest.controller.dto.PriceDtoBuilder;
import com.backend.capitole.core.application.port.inbound.PriceInPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@ErrorHandler
@Validated
@RequestMapping(value = "/capitole")
public class PriceController {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final Logger logger = LoggerFactory.getLogger(PriceController.class);
    private final PriceInPort priceInPort;

    public PriceController(@Autowired PriceInPort priceInPort) {
        this.priceInPort = priceInPort;
    }

    @GetMapping(
            value = "/prices/brands/{brand_id}/products/{product_id}/date/{date}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PriceDto> getPriceByDate(
            @Valid @PositiveOrZero(message = "Field brand_id is invalid. Only positive integer values allowed.")
            @PathVariable("brand_id")
            int brandId,
            @Valid @PositiveOrZero(message = "Field product_id is invalid. Only positive integer values allowed.")
            @PathVariable("product_id")
            int productId,
            @Valid @Pattern(regexp = "^((19|2[0-9])[0-9]{2})-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01]) (0[0-9]|1[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$",
                    message = "Field date has wrong datetime format. It should be yyyy-MM-dd HH:mm:ss")
            @PathVariable("date")
            String date
    ) {
        logger.info("Start execution of GET /capitole/prices/brands/{}/products/{}/date/{}",
                brandId, productId, date);
        return ResponseEntity.ok(
                PriceDtoBuilder.build(
                        priceInPort.getPriceByDate(
                                LocalDateTime.parse(date, DATE_TIME_FORMATTER),
                                productId,
                                brandId)
                )
        );
    }

}
