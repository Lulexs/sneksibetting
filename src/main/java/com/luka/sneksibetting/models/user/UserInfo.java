package com.luka.sneksibetting.models.user;

import java.math.BigDecimal;
import java.util.UUID;

public record UserInfo(UUID uuid, String username, Integer wins, Integer draws, Integer losses,
                       Integer bets_won, Integer bets_lost, BigDecimal elo, BigDecimal coins) {
    public static UserInfo from(User user) {
        return new UserInfo(
                user.uuid(),
                user.username(),
                user.wins(),
                user.draws(),
                user.losses(),
                user.bets_won(),
                user.bets_lost(),
                user.elo(),
                user.coins()
        );
    }
}
