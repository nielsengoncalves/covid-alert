package com.audibene.covidalert.service;

import com.audibene.covidalert.model.AlertLevel;
import com.audibene.covidalert.model.Timeline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlertLevelCalculatorService {

    private static final Logger log = LoggerFactory.getLogger(AlertLevelCalculatorService.class);

    private static final double RED_ALERT_THRESHOLD = 0.002;
    private static final double YELLOW_ALERT_THRESHOLD = 0.0015;
    private static final int EVALUATION_PERIOD_DAYS = 5;

    public AlertLevel calculateAlertLevel(LocalDate date, Long population, List<Timeline> timelines) {
        final var activeCasesPerPopulation = calculateACPPForEvaluationInterval(date, population, timelines);
        final var totalAboveRed = filterACPPAboveThreshold(activeCasesPerPopulation, RED_ALERT_THRESHOLD).size();
        if (totalAboveRed >= EVALUATION_PERIOD_DAYS) {
            return AlertLevel.RED;
        }

        final var totalAboveYellow = filterACPPAboveThreshold(activeCasesPerPopulation, YELLOW_ALERT_THRESHOLD).size();
        if (totalAboveYellow >= EVALUATION_PERIOD_DAYS) {
            return AlertLevel.YELLOW;
        }

        return AlertLevel.GREEN;
    }

    private List<Double> filterACPPAboveThreshold(List<Double> activeCasesPerDay, Double threshold) {
        return activeCasesPerDay.stream()
                .filter(activeCases -> activeCases > threshold)
                .collect(Collectors.toList());
    }

    private List<Double> calculateACPPForEvaluationInterval(
            LocalDate referenceDate,
            Long population,
            List<Timeline> timelines
    ) {
        return timelines.stream()
                .filter(timeline -> isInEvaluationInterval(referenceDate, timeline))
                .map(timeline -> calculateACPP(population, timeline))
                .collect(Collectors.toList());
    }

    private Boolean isInEvaluationInterval(LocalDate referenceDate, Timeline timeline) {
        final var evaluationDate = referenceDate.minusDays(EVALUATION_PERIOD_DAYS);
        final var timelineDate = LocalDate.parse(timeline.getDate());

        return timelineDate.isAfter(evaluationDate) && timelineDate.isBefore(referenceDate.plusDays(1));
    }

    private Double calculateACPP(Long population, Timeline timeline) {
        final var activeCasesPerPopulation = timeline.getActive().doubleValue() / population.doubleValue();
        log.info("Active cases per population on day {} is equal to {}.", timeline.getDate(), activeCasesPerPopulation);

        return activeCasesPerPopulation;
    }
}

