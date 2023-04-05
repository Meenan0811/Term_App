package com.Meenan.Term_App.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Meenan.Term_App.Entities.Term;
import com.Meenan.Term_App.R;

import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder> {

    private List<Term> mTerms;
    private final Context context;

    class TermViewHolder extends RecyclerView.ViewHolder {

        private final TextView termItemView;

        private TermViewHolder(View itemView) {
            super(itemView);
            termItemView = itemView.findViewById(R.id.termlist);
            itemView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Term curTerm = mTerms.get(position);
                    Intent intent = new Intent(context, ModifyTerm.class);
                    intent.putExtra("termName", curTerm.getTermName());
                    intent.putExtra("startDate", curTerm.getStartDate());
                    intent.putExtra("endDate", curTerm.getEndDate());
                    intent.putExtra("termID",curTerm.getTermID());
                    context.startActivity(intent);
                }
            });

        }

    }

    private final LayoutInflater mInflater;



    public TermAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }
    @NonNull
    @Override
    public TermAdapter.TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.term_list_item, parent, false);
        return new TermViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TermAdapter.TermViewHolder holder, int position) {
        if (mTerms != null) {
            Term currTerm = mTerms.get(position);
            String termName = currTerm.getTermName();
            holder.termItemView.setText(termName);
        }

        else {
            holder.termItemView.setText("No Terms have been Added. Please Add a Term to get Started");
        }

    }


    @Override
    public int getItemCount() {
        return mTerms.size();
    }

    public void setTerms(List<Term> term) {
        mTerms = term;
        notifyDataSetChanged();
    }
}
