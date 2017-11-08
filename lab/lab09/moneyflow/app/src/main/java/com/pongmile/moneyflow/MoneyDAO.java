package com.pongmile.moneyflow;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
interface MoneyDAO {

    @Insert
    void Insert(Money money);

    @Query("SELECT * FROM MONEY")
    List<Money> allItem();

    @Query("DELETE FROM MONEY WHERE id = :id")
    int delete(int id);

    @Query("UPDATE MONEY SET Type = :type,Item = :item ,Amount = :amount WHERE id =:ids")
    int UpdateColumn(String type, String item, int amount, int ids);
}
