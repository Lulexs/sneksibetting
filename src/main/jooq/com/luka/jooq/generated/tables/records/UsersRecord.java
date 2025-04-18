/*
 * This file is generated by jOOQ.
 */
package com.luka.jooq.generated.tables.records;


import com.luka.jooq.generated.tables.Users;

import java.math.BigDecimal;
import java.util.UUID;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class UsersRecord extends UpdatableRecordImpl<UsersRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.users.id</code>.
     */
    public void setId(UUID value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.users.id</code>.
     */
    public UUID getId() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>public.users.username</code>.
     */
    public void setUsername(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.users.username</code>.
     */
    public String getUsername() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.users.password</code>.
     */
    public void setPassword(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.users.password</code>.
     */
    public String getPassword() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.users.wins</code>.
     */
    public void setWins(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.users.wins</code>.
     */
    public Integer getWins() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>public.users.draws</code>.
     */
    public void setDraws(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.users.draws</code>.
     */
    public Integer getDraws() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>public.users.losses</code>.
     */
    public void setLosses(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.users.losses</code>.
     */
    public Integer getLosses() {
        return (Integer) get(5);
    }

    /**
     * Setter for <code>public.users.bets_won</code>.
     */
    public void setBetsWon(Integer value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.users.bets_won</code>.
     */
    public Integer getBetsWon() {
        return (Integer) get(6);
    }

    /**
     * Setter for <code>public.users.bets_lost</code>.
     */
    public void setBetsLost(Integer value) {
        set(7, value);
    }

    /**
     * Getter for <code>public.users.bets_lost</code>.
     */
    public Integer getBetsLost() {
        return (Integer) get(7);
    }

    /**
     * Setter for <code>public.users.elo</code>.
     */
    public void setElo(BigDecimal value) {
        set(8, value);
    }

    /**
     * Getter for <code>public.users.elo</code>.
     */
    public BigDecimal getElo() {
        return (BigDecimal) get(8);
    }

    /**
     * Setter for <code>public.users.coins</code>.
     */
    public void setCoins(BigDecimal value) {
        set(9, value);
    }

    /**
     * Getter for <code>public.users.coins</code>.
     */
    public BigDecimal getCoins() {
        return (BigDecimal) get(9);
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
     * Create a detached UsersRecord
     */
    public UsersRecord() {
        super(Users.USERS);
    }

    /**
     * Create a detached, initialised UsersRecord
     */
    public UsersRecord(UUID id, String username, String password, Integer wins, Integer draws, Integer losses, Integer betsWon, Integer betsLost, BigDecimal elo, BigDecimal coins) {
        super(Users.USERS);

        setId(id);
        setUsername(username);
        setPassword(password);
        setWins(wins);
        setDraws(draws);
        setLosses(losses);
        setBetsWon(betsWon);
        setBetsLost(betsLost);
        setElo(elo);
        setCoins(coins);
        resetChangedOnNotNull();
    }
}
