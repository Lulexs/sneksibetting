/*
 * This file is generated by jOOQ.
 */
package com.luka.jooq.generated.tables.records;


import com.luka.jooq.generated.tables.Games;

import java.math.BigDecimal;
import java.util.UUID;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class GamesRecord extends UpdatableRecordImpl<GamesRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.games.id</code>.
     */
    public void setId(UUID value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.games.id</code>.
     */
    public UUID getId() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>public.games.player1_id</code>.
     */
    public void setPlayer1Id(UUID value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.games.player1_id</code>.
     */
    public UUID getPlayer1Id() {
        return (UUID) get(1);
    }

    /**
     * Setter for <code>public.games.player2_id</code>.
     */
    public void setPlayer2Id(UUID value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.games.player2_id</code>.
     */
    public UUID getPlayer2Id() {
        return (UUID) get(2);
    }

    /**
     * Setter for <code>public.games.player1_elo</code>.
     */
    public void setPlayer1Elo(BigDecimal value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.games.player1_elo</code>.
     */
    public BigDecimal getPlayer1Elo() {
        return (BigDecimal) get(3);
    }

    /**
     * Setter for <code>public.games.player2_elo</code>.
     */
    public void setPlayer2Elo(BigDecimal value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.games.player2_elo</code>.
     */
    public BigDecimal getPlayer2Elo() {
        return (BigDecimal) get(4);
    }

    /**
     * Setter for <code>public.games.winner_id</code>.
     */
    public void setWinnerId(UUID value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.games.winner_id</code>.
     */
    public UUID getWinnerId() {
        return (UUID) get(5);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<UUID> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached GamesRecord
     */
    public GamesRecord() {
        super(Games.GAMES);
    }

    /**
     * Create a detached, initialised GamesRecord
     */
    public GamesRecord(UUID id, UUID player1Id, UUID player2Id, BigDecimal player1Elo, BigDecimal player2Elo, UUID winnerId) {
        super(Games.GAMES);

        setId(id);
        setPlayer1Id(player1Id);
        setPlayer2Id(player2Id);
        setPlayer1Elo(player1Elo);
        setPlayer2Elo(player2Elo);
        setWinnerId(winnerId);
        resetChangedOnNotNull();
    }
}
