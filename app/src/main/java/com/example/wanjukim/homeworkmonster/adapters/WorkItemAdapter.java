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
import com.example.wanjukim.homeworkmonster.utils.Utils;

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
    private List<WorkItem> workItems;

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
    public void onBindViewHolder(WorkItemHolder viewHolder, final int position) {
        final WorkItem workItem=workItems.get(position);
//        boolean prevState=workItem.getSwipeState();

        viewHolder.bind(workItem);

        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut); // move a bottom layer following by swiping

        viewHolder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {
                workItem.setSwipeState(true);
                Log.d("Debugging_",position+" open");
                for(int i=0;i<getItemCount();i++)
                    Log.d("Debugging_",i+": "+workItems.get(i).getSwipeState());
                for (int i=0;i<getItemCount();i++) {
                    if(i!=position&&workItems.get(i).getSwipeState()) {
                        workItems.get(i).setSwipeState(false);
                        notifyItemChanged(i);
                        Log.d("Debugging_",i+" forced to close");
                    }
                }
                for(int i=0;i<getItemCount();i++)
                    Log.d("Debugging_",i+": "+workItems.get(i).getSwipeState());
            }

            @Override
            public void onOpen(SwipeLayout layout) {

            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onClose(SwipeLayout layout) {
                workItem.setSwipeState(false);
                Log.d("Debugging_",position+" close");
                for(int i=0;i<getItemCount();i++)
                    Log.d("Debugging_",i+": "+workItems.get(i).getSwipeState());
//
//                for (int i=0;i<getItemCount();i++) {
//                    workItems.get(i).setSwipeState(false);
//                    if(i!=position)
//                        notifyItemChanged(i);
//                }
            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

            }
        });


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

    public boolean getIfSwipedItem(){
        for(WorkItem item:workItems)
            if(item.getSwipeState())
                return true;
        return false;
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
        }

        private void bind(WorkItem workItem){
            work.setText(workItem.getWork());
            subject.setText(workItem.getSubject());
            dDay.setText(workItem.getdDay());
            deadline.setText(workItem.getDeadline());

            if(getIfSwipedItem()){ // when one of item holders is swiped
                if(!workItem.getSwipeState()) {
//                    workItem.setSwipeState(false);
                    swipeLayout.close();
//                    SwipeLayout.SwipeListener.
                }
            }

//            Log.d("Debugging_","bind: "+workItem.getWork());
        }
    }
}
