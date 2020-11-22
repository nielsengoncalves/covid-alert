package com.audibene.covidalert.controller;

import com.audibene.covidalert.client.CoronaApi;
import com.audibene.covidalert.controller.exception.NotFoundException;
import com.audibene.covidalert.service.AlertLevelCalculatorService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@AllArgsConstructor
public class CoronaController {

    private static final Logger log = LoggerFactory.getLogger(CoronaController.class);

    private final CoronaApi coronaApi;
    private final AlertLevelCalculatorService alertLevelCalculatorService;

    @GetMapping(value = "/api/{countryCode}/{date}")
    @SneakyThrows
    AlertLevelResponse getAlertLevel(
            @PathVariable String countryCode,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        log.info("Calculating alert level for {} on {}", countryCode, date);

        final var coronaApiResponse = coronaApi.getByCountry(countryCode).execute();
        if (coronaApiResponse.body() == null || coronaApiResponse.body().getData() == null) {
            throw new NotFoundException("Country not found");
        }

        final var data = coronaApiResponse.body().getData();
        final var alertLevel = alertLevelCalculatorService.calculateAlertLevel(date, data.getPopulation(), data.getTimeline());

        log.info("Calculated alert level for {} on {} is {}", countryCode, date, alertLevel);
        return new AlertLevelResponse(data.getName(), alertLevel);
    }

}