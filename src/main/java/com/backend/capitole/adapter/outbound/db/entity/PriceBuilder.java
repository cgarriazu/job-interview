package com.backend.capitole.adapter.outbound.db.entity;

import com.backend.capitole.core.domain.model.Price;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PriceBuilder {

    public static Price build(PriceEntity priceEntity) {
        return new Price(
                priceEntity.getPriceList(),
                priceEntity.getBrandId(),
                priceEntity.getStartDate(),
                priceEntity.getEndDate(),
                priceEntity.getProductId(),
                priceEntity.getPriority(),
                priceEntity.getPrice(),
                priceEntity.getCurrency()
        );
    }
}
