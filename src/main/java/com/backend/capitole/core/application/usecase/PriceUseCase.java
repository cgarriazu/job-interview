package com.backend.capitole.core.application.usecase;

import com.backend.capitole.core.application.port.inbound.PriceInPort;
import com.backend.capitole.core.application.port.outbound.PriceOutPort;
import com.backend.capitole.core.domain.exception.PriceNotFoundException;
import com.backend.capitole.core.domain.model.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;

@Service
public class PriceUseCase implements PriceInPort {

    private final PriceOutPort priceOutPort;

    public PriceUseCase(@Autowired PriceOutPort priceOutPort) {
        this.priceOutPort = priceOutPort;
    }

    @Override
    public Price getPriceByDate(LocalDateTime date, Integer productId, Integer brandId) {
        return priceOutPort.getPricesByDate(date, productId, brandId)
                .sorted(Comparator.comparingInt(Price::getPriority).reversed())
                .findFirst()
                .orElseThrow(PriceNotFoundException::new);
    }
}
