package com.audibene.covidalert.client;

import com.audibene.covidalert.model.Timeline;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CoronaApiResponse {
    private InternalData data;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class InternalData {
        private String name;
        private Long population;
        private List<Timeline> timeline;
    }
}
