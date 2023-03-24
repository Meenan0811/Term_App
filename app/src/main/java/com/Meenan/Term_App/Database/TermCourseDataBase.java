package com.Meenan.Term_App.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.Meenan.Term_App.DAO.CourseDAO;
import com.Meenan.Term_App.DAO.MentorDAO;
import com.Meenan.Term_App.DAO.TermDAO;
import com.Meenan.Term_App.Entities.Course;
import com.Meenan.Term_App.Entities.Mentor;
import com.Meenan.Term_App.Entities.Term;

@Database(entities = {Course.class, Term.class, Mentor.class}, version = 1, exportSchema = false)
public abstract class TermCourseDataBase extends RoomDatabase {

    public abstract CourseDAO courseDAO();
    public abstract TermDAO termDAO();
    public abstract MentorDAO mentorDAO();

    public static volatile TermCourseDataBase INSTANCE;

    static TermCourseDataBase getDataBase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TermCourseDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TermCourseDataBase.class, "Term_Course.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
