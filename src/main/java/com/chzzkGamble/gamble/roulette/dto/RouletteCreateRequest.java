package com.chzzkGamble.gamble.roulette.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record RouletteCreateRequest(
        @NotBlank(message = "channelName is Blank")
        String channelName,

        @Positive(message = "rouletteUnit must be positive")
        int rouletteUnit
) {

}
