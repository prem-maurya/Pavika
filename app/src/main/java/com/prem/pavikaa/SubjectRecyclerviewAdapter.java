package com.prem.pavikaa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prem.pavikaa.Ads.Admob;
import com.prem.pavikaa.Ads.OnDismiss;

import java.util.List;

public class SubjectRecyclerviewAdapter extends RecyclerView.Adapter<SubjectRecyclerviewAdapter.ViewHolder> {

    Context context;
    List<SubjectModel> subjectModelList;

    public SubjectRecyclerviewAdapter(Context context, List<SubjectModel> subjectModelList) {
        this.context = context;
        this.subjectModelList = subjectModelList;
    }

    @NonNull
    @Override
    public SubjectRecyclerviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.subject_content_row_name, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectRecyclerviewAdapter.ViewHolder holder, int position) {
        SubjectModel subjectModel = subjectModelList.get(position);
        holder.subjectImage.setImageResource(subjectModel.subjectImage);
        holder.subjectName.setText(subjectModel.getSubjectName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PDFActivity.class);
//                new Admob(new OnDismiss() {
//                    @Override
//                    public void onDismiss() {
//
//                    }
//                }).showInterstitialsAds((Activity) context, true);
                intent.putExtra("pdf", subjectModel.getSubjectPdf());
                intent.putExtra("subName", subjectModel.getSubjectName());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return subjectModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView subjectImage;
        TextView subjectName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subjectImage = itemView.findViewById(R.id.subjectImage);
            subjectName = itemView.findViewById(R.id.subjectName);
        }
    }
}
