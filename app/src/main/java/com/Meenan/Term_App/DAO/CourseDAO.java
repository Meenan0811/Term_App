package com.Meenan.Term_App.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.Meenan.Term_App.Entities.Course;

import java.util.List;

@Dao
public interface CourseDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Course course);

    @Update
    void update(Course course);

    @Delete
    void delete(Course course);

    @Query("SELECT * FROM Courses")
    List<Course> getAllCourses();

    @Query("SELECT * FROM Courses WHERE termID_FK = :termID")
    List<Course> getAllTermCourses(int termID);

    @Query("SELECT MAX(courseID) FROM Courses")
    int getMaxCourseId();

}
