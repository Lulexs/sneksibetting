package com.luka.sneksibetting.models.user;

import java.math.BigDecimal;
import java.util.UUID;

public record User(UUID uuid, String username, String password, Integer wins, Integer draws, Integer losses,
                   Integer bets_won, Integer bets_lost, BigDecimal elo, BigDecimal coins) {
}