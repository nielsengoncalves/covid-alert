package com.audibene.covidalert.service;

import com.audibene.covidalert.model.AlertLevel;
import com.audibene.covidalert.model.Timeline;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class AlertLevelCalculatorServiceTest {

    private final AlertLevelCalculatorService alertLevelCalculatorService = new AlertLevelCalculatorService();

    @Nested
    class CalculateAlertLevelTest {

        private static final long RED = 201L;
        private static final long YELLOW = 200L;
        private static final long GREEN = 150L;

        @Test
        void shouldReturnGreenWhenThereIsNoSufficientData() {
            var date = LocalDate.parse("2020-11-21");
            var timelineList = Lists.list(
                    new Timeline(RED, "2020-11-21"),
                    new Timeline(RED, "2020-11-20"),
                    new Timeline(RED, "2020-11-19"),
                    new Timeline(RED, "2020-11-18")
            );

            var actual = alertLevelCalculatorService.calculateAlertLevel(date, 100000L, timelineList);
            assertThat(actual).isEqualTo(AlertLevel.GREEN);
        }

        @Test
        void shouldReturnGreenWhenThereIsNoDataForAllPast5Days() {
            var date = LocalDate.parse("2020-11-21");
            var timelineList = Lists.list(
                    new Timeline(RED, "2020-11-21"),
                    new Timeline(RED, "2020-11-20"),
                    new Timeline(RED, "2020-11-17"),
                    new Timeline(RED, "2020-11-16"),
                    new Timeline(RED, "2020-11-15")
            );

            var actual = alertLevelCalculatorService.calculateAlertLevel(date, 100000L, timelineList);

            assertThat(actual).isEqualTo(AlertLevel.GREEN);
        }

        @Test
        void shouldReturnRedWhenAllPast5DaysAreRed() {
            var date = LocalDate.parse("2020-11-21");
            var timelineList = Lists.list(
                    new Timeline(RED, "2020-11-21"),
                    new Timeline(RED, "2020-11-20"),
                    new Timeline(RED, "2020-11-19"),
                    new Timeline(RED, "2020-11-18"),
                    new Timeline(RED, "2020-11-17")
            );

            var actual = alertLevelCalculatorService.calculateAlertLevel(date, 100000L, timelineList);

            assertThat(actual).isEqualTo(AlertLevel.RED);
        }

        @Test
        void shouldReturnYellowWhenAllPast5DaysAreYellowButNotRed() {
            var date = LocalDate.parse("2020-11-21");
            var timelineList = Lists.list(
                    new Timeline(RED, "2020-11-21"),
                    new Timeline(RED, "2020-11-20"),
                    new Timeline(RED, "2020-11-19"),
                    new Timeline(RED, "2020-11-18"),
                    new Timeline(YELLOW, "2020-11-17")
            );

            var actual = alertLevelCalculatorService.calculateAlertLevel(date, 100000L, timelineList);

            assertThat(actual).isEqualTo(AlertLevel.YELLOW);
        }

        @Test
        void shouldReturnGreenWhenAtLeastOneTimelineInPast5DaysIsGreen() {
            var date = LocalDate.parse("2020-11-21");
            var timelineList = Lists.list(
                    new Timeline(RED, "2020-11-21"),
                    new Timeline(RED, "2020-11-20"),
                    new Timeline(RED, "2020-11-19"),
                    new Timeline(YELLOW, "2020-11-18"),
                    new Timeline(GREEN, "2020-11-17")
            );

            var actual = alertLevelCalculatorService.calculateAlertLevel(date, 100000L, timelineList);

            assertThat(actual).isEqualTo(AlertLevel.GREEN);
        }

        @Test
        void shouldNotConsiderDatesAboveGivenDate() {
            var date = LocalDate.parse("2020-11-19");
            var timelineList = Lists.list(
                    new Timeline(YELLOW, "2020-11-20"),
                    new Timeline(RED, "2020-11-19"),
                    new Timeline(RED, "2020-11-18"),
                    new Timeline(RED, "2020-11-17"),
                    new Timeline(RED, "2020-11-16"),
                    new Timeline(RED, "2020-11-15"),
                    new Timeline(YELLOW, "2020-11-14")
            );

            var actual = alertLevelCalculatorService.calculateAlertLevel(date, 100000L, timelineList);

            assertThat(actual).isEqualTo(AlertLevel.RED);
        }
    }
}