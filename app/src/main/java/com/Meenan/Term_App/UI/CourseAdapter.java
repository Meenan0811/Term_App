package com.Meenan.Term_App.UI;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.Meenan.Term_App.Entities.Course;
import com.Meenan.Term_App.R;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder>  {

    private List<Course> mCourses;
    private Context context;


    class CourseViewHolder extends RecyclerView.ViewHolder {

        public final TextView courseItemView;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            courseItemView = itemView.findViewById(R.id.courselist);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Course curCourse = mCourses.get(position);
                    Intent intent = new Intent(context, ViewCourses.class);
                    intent.putExtra("name", curCourse.getCourseName());
                    intent.putExtra("status", curCourse.getCourseStatus());
                    intent.putExtra("start date", curCourse.getStartDate());
                    intent.putExtra("end date", curCourse.getEndDate());
                    context.startActivity(intent);
                }
            });
        }
    }

    private LayoutInflater mInflater;

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
            holder.courseItemView.setText(courseName + "   " + courseStatus);
        }
        else {
            holder.courseItemView.setText("No Courses added, please add course");
        }
    }

    @Override
    public int getItemCount() {
        return mCourses.size();
    }

    public void setTerms(List<Course> course) {
        mCourses = course;
        notifyDataSetChanged();
    }
}
