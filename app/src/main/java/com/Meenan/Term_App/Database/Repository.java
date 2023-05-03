package com.Meenan.Term_App.Database;

import android.app.Application;

import com.Meenan.Term_App.DAO.AssesmentDAO;
import com.Meenan.Term_App.DAO.CourseDAO;
import com.Meenan.Term_App.DAO.TermDAO;
import com.Meenan.Term_App.Entities.Assesment;
import com.Meenan.Term_App.Entities.Course;
import com.Meenan.Term_App.Entities.Term;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Repository {

    private final CourseDAO mCourseDAO;
    private final TermDAO mTermDAO;
    private final AssesmentDAO mAssesmentDAO;

    private List<Course> mAllCourses;
    private List<Term> mAllTerms;
    private List<Assesment> mAllAssesments;
    private Term mTerm;

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService dbExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application app) {
        TermCourseDataBase termDb = TermCourseDataBase.getDataBase(app);
        mCourseDAO = termDb.courseDAO();
        mTermDAO = termDb.termDAO();
        mAssesmentDAO = termDb.assesmentDAO();
    }

    public List<Term> getAllTerms() throws InterruptedException {
        dbExecutor.execute(() -> {
            mAllTerms = mTermDAO.getAllTerms();
        });
        Thread.sleep(1000);
        return mAllTerms;
    }

    public void insert(Term term) throws InterruptedException {
        dbExecutor.execute(() -> {
            mTermDAO.insert(term);
        });
        Thread.sleep(1000);
    }

    public void update(Term term) throws InterruptedException {
        dbExecutor.execute(() -> {
            mTermDAO.update(term);
        });
        Thread.sleep(1000);
    }

    public void delete(Term term) throws InterruptedException {
        dbExecutor.execute(() -> {
            mTermDAO.delete(term);
        });
        Thread.sleep(1000);
    }

    public List<Course> getAllCourses() throws InterruptedException {
        dbExecutor.execute(() -> {
            mAllCourses = mCourseDAO.getAllCourses();
        });
        Thread.sleep(1000);
        return mAllCourses;
    }

    public List<Course> getAllTermCourses(int termID) throws InterruptedException {
        dbExecutor.execute(() -> {
            mAllCourses = mCourseDAO.getAllTermCourses(termID);
        });
        return mAllCourses;
    }

    public void insert(Course course) throws InterruptedException {
        dbExecutor.execute(() -> {
            mCourseDAO.insert(course);
        });
        Thread.sleep(1000);
    }

    public void update(Course course) throws InterruptedException {
        dbExecutor.execute(() -> {
            mCourseDAO.update(course);
        });
        Thread.sleep(1000);
    }

    public void delete(Course course) throws InterruptedException {
        dbExecutor.execute(() -> {
            mCourseDAO.delete(course);
        });
        Thread.sleep(1000);
    }

    public List<Assesment> getAllAssesments() throws InterruptedException {
        dbExecutor.execute(() -> {
            mAllAssesments = mAssesmentDAO.getAllAssesments();
        });
        Thread.sleep(1000);
        return mAllAssesments;
    }

    public List<Assesment> getAssesmentById(int courseId) throws InterruptedException {
        dbExecutor.execute(() -> {
            mAllAssesments = mAssesmentDAO.getAssosicatedAssesment(courseId);
        });
        return mAllAssesments;
    }

    public void insert(Assesment assesment) throws InterruptedException {
        dbExecutor.execute(() -> {
            mAssesmentDAO.insert(assesment);
        });
        Thread.sleep(1000);
    }

    public void update(Assesment assesment) throws InterruptedException {
        dbExecutor.execute(() -> {
            mAssesmentDAO.update(assesment);
        });
        Thread.sleep(1000);
    }

    public void delete(Assesment assesment) throws InterruptedException {
        dbExecutor.execute(() -> {
            mAssesmentDAO.delete(assesment);
        });
        Thread.sleep(1000);
    }


}
