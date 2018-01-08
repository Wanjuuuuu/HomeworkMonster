package com.example.wanjukim.homeworkmonster.adapters;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.daimajia.swipe.util.Attributes;
import com.example.wanjukim.homeworkmonster.R;
import com.example.wanjukim.homeworkmonster.WorkItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Wanju Kim on 2018-01-06.
 */

public class WorkItemAdapter extends RecyclerSwipeAdapter<WorkItemAdapter.WorkItemHolder> {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<WorkItem> workItems;

    public WorkItemAdapter(Context context,ArrayList<WorkItem> workItems){
        this.context=context;
        inflater=LayoutInflater.from(context);
        this.workItems=workItems;
    }

    @Override
    public WorkItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new WorkItemHolder(inflater.inflate(R.layout.swipe_item_view,parent,false));
    }

    @Override
    public void onBindViewHolder(WorkItemHolder viewHolder, int position) {
        WorkItem workItem=workItems.get(position);

        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut); // move a bottom layer following by swiping

        viewHolder.bind(workItem);

        viewHolder.option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 수정
            }
        });

        viewHolder.option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 포기
            }
        });

        viewHolder.option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 제출
            }
        });
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe_layout; //
    }

    @Override
    public int getItemCount() {
        return workItems.size();
    }

    class WorkItemHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.swipe_layout) SwipeLayout swipeLayout;
        @BindView(R.id.main_work) TextView work;
        @BindView(R.id.main_subject) TextView subject;
        @BindView(R.id.main_dday) TextView dDay;
        @BindView(R.id.main_deadline) TextView deadline;
        @BindView(R.id.bottom_swipe_layout) LinearLayout bottomLayout;
        @BindView(R.id.swipe_option1) ConstraintLayout option1;
        @BindView(R.id.swipe_option2) ConstraintLayout option2;
        @BindView(R.id.swipe_option3) ConstraintLayout option3;

        WorkItemHolder(View view){
            super(view);
            ButterKnife.bind(this, view);

//            swipeLayout.addDrag(SwipeLayout.DragEdge.Left,bottomLayout);
        }

        private void bind(WorkItem workItem){
            work.setText(workItem.getWork());
            subject.setText(workItem.getSubject());
            dDay.setText(workItem.getdDay());
            deadline.setText(workItem.getDeadline());

            Log.d("Debugging_","bind: "+workItem.getWork());
        }
    }
}
