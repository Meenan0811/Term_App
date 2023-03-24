package com.Meenan.Term_App.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.Meenan.Term_App.Entities.Mentor;

import java.util.List;

@Dao
public interface MentorDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Mentor mentor);

    @Update
    void update(Mentor mentor);

    @Delete
    void delete(Mentor mentor);

    @Query("SELECT * FROM MENTORS")
    List<Mentor> getAllMentors();
}
