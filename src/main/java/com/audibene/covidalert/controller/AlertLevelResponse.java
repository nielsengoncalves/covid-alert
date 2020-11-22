package com.audibene.covidalert.controller;

import com.audibene.covidalert.model.AlertLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
class AlertLevelResponse {
    private String countryName;
    private AlertLevel alertLevel;
}
