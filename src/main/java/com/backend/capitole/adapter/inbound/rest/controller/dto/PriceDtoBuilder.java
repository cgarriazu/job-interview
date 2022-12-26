package com.backend.capitole.adapter.inbound.rest.controller.dto;

import com.backend.capitole.core.domain.model.Price;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PriceDtoBuilder {
    public static PriceDto build(Price price) {
        return new PriceDto(
                price.getProductId().toString(),
                price.getBrandId().toString(),
                price.getPriceList().toString(),
                price.getStartDate().toString(),
                price.getEndDate().toString(),
                price.getCurrency(),
                price.getPriceValue()
        );
    }
}
