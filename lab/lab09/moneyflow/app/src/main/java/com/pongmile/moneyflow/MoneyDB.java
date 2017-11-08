package com.pongmile.moneyflow;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Money.class}, version = 1)
public abstract class MoneyDB extends RoomDatabase {
    public abstract MoneyDAO moneyDAO();
}