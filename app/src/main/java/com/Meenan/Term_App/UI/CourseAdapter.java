package com.Meenan.Term_App.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Meenan.Term_App.Entities.Course;
import com.Meenan.Term_App.R;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder>  {

    private List<Course> mCourses;
    private final Context context;


    class CourseViewHolder extends RecyclerView.ViewHolder {

        public final TextView courseItemView;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            courseItemView = itemView.findViewById(R.id.assignedcourselist);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Course curCourse = mCourses.get(position);
                    Intent intent = new Intent(context, ModifyCourses.class);
                    intent.putExtra("courseName", curCourse.getCourseName());
                    intent.putExtra("status", curCourse.getCourseStatus());
                    intent.putExtra("startDate", curCourse.getStartDate());
                    intent.putExtra("endDate", curCourse.getEndDate());
                    intent.putExtra("courseID", curCourse.getCourseID());
                    intent.putExtra("course_termID", curCourse.getTermID_FK());
                    intent.putExtra("courseNotes", curCourse.getNotes());
                    intent.putExtra("instName", curCourse.getInstName());
                    intent.putExtra("instEmail", curCourse.getInstEmail());
                    intent.putExtra("instPhone", curCourse.getInstPhone());
                    context.startActivity(intent);
                }
            });
        }
    }

    private final LayoutInflater mInflater;

    public CourseAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public CourseAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.course_list_item, parent, false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.CourseViewHolder holder, int position) {
        if (mCourses != null) {
            Course curCourse = mCourses.get(position);
            String courseName = curCourse.getCourseName();
            String courseStatus = curCourse.getCourseStatus();
            holder.courseItemView.setText(courseName);
        }
    }

    @Override
    public int getItemCount() {
        if (mCourses != null) {
            return mCourses.size();
        } else {
            return 0;
        }
    }

    public void setCourses(List<Course> course) {
        mCourses = course;
        notifyDataSetChanged();
    }
}
