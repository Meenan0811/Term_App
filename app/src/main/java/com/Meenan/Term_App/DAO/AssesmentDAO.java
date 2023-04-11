package com.Meenan.Term_App.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.Meenan.Term_App.Entities.Assesment;

import java.util.List;

@Dao
public interface AssesmentDAO {

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    void insert(Assesment assesment);

    @Update
    void update(Assesment assesment);

    @Delete
    void delete(Assesment assesment);

    @Query("SELECT * FROM Assesments" )
    List<Assesment> getAllAssesments();

    @Query("SELECT * FROM Assesments where assesmentId = :assesmentId")
    List<Assesment> getAssosicatedAssesment(int assesmentId);
}
