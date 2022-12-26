package com.backend.capitole.core.application.port.outbound;

import com.backend.capitole.core.domain.model.Price;

import java.time.LocalDateTime;
import java.util.stream.Stream;

public interface PriceOutPort {

    Stream<Price> getPricesByDate(LocalDateTime date, Integer productId, Integer brandId);
}
