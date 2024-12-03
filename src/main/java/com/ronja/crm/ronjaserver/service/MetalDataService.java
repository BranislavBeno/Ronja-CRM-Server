package com.ronja.crm.ronjaserver.service;

import com.ronja.crm.ronjaserver.client.api.MetalExchangeWebClient;
import com.ronja.crm.ronjaserver.client.domain.MetalExchange;
import com.ronja.crm.ronjaserver.dto.MetalDataMapper;
import com.ronja.crm.ronjaserver.entity.MetalData;
import com.ronja.crm.ronjaserver.repository.MetalDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

public final class MetalDataService {

    private static final Logger LOG = LoggerFactory.getLogger(MetalDataService.class);

    private final MetalDataRepository repository;
    private final MetalExchangeWebClient webClient;
    private final MetalDataMapper mapper;

    public MetalDataService(MetalDataRepository repository,
                            MetalExchangeWebClient webClient,
                            MetalDataMapper mapper) {
        this.repository = repository;
        this.webClient = webClient;
        this.mapper = mapper;
    }

    public Iterable<MetalData> findAll() {
        return repository.findAll();
    }

    @Scheduled(cron = "${client.metal.cron.expression:-}")
    public MetalData exchange() {
        MetalExchange exchange = webClient.fetchExchangeData();
        MetalData metalData = repository.save(mapper.toEntity(exchange));
        LOG.info("Metal data with id {} persisted on {} into database.", metalData.getId(), metalData.getFetched());

        return metalData;
    }
}
