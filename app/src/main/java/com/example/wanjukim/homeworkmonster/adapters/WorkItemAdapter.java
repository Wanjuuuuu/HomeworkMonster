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
import com.example.wanjukim.homeworkmonster.activities.MainActivity;
import com.example.wanjukim.homeworkmonster.models.WorkItem;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

/**
 * Created by Wanju Kim on 2018-01-06.
 */

public class WorkItemAdapter extends RecyclerSwipeAdapter<WorkItemAdapter.WorkItemHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<WorkItem> workItems;
    private WorkItemHolder workItemHolder;

    public WorkItemAdapter(Context context,List<WorkItem> workItems){
        this.context=context;
        inflater=LayoutInflater.from(context);
        this.workItems=workItems;

        setMode(Attributes.Mode.Single);
    }

    @Override
    public WorkItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        workItemHolder= new WorkItemHolder(inflater.inflate(R.layout.swipe_item_view,parent,false));
        return workItemHolder;
    }

    @Override
    public void onBindViewHolder(WorkItemHolder viewHolder, int position) {
        WorkItem workItem=workItems.get(position);
        viewHolder.bind(workItem);
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe_layout; //
    }

    @Override
    public int getItemCount() {
        return workItems.size();
    }

    public void closeItems(){
        for(int id:getOpenItems()){
            closeItem(id);
        }
    }

    class WorkItemHolder extends RecyclerView.ViewHolder implements SwipeLayout.SwipeListener {
        @BindView(R.id.swipe_layout) SwipeLayout swipeLayout;
        @BindView(R.id.main_work) TextView work;
        @BindView(R.id.main_subject) TextView subject;
        @BindView(R.id.main_dday) TextView dDay;
        @BindView(R.id.main_deadline) TextView deadline;
        @BindView(R.id.surface_swipe_layout) ConstraintLayout surfaceLayout;
        @BindView(R.id.bottom_swipe_layout) LinearLayout bottomLayout;
        @BindView(R.id.swipe_option1) ConstraintLayout option1;
        @BindView(R.id.swipe_option2) ConstraintLayout option2;

        private WorkItem workItem;

        WorkItemHolder(View view){
            super(view);
            ButterKnife.bind(this, view);

            // move a bottom layer following by swiping
            swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
            swipeLayout.addSwipeListener(this);
        }

        private void bind(WorkItem workItem){
            this.workItem = workItem;

            SimpleDateFormat formatter = new SimpleDateFormat("MMM d h:mm a", Locale.ENGLISH);

            work.setText(workItem.getWork());
            subject.setText(workItem.getId()+" ");// 이후 subject로 변경해주기
            dDay.setText(workItem.getdDay());
            deadline.setText(formatter.format(workItem.getDeadline()));
        }

        @OnClick(R.id.surface_swipe_layout)
        public void onClickModify() {
            // TODO : make new intent activity
        }

        @OnClick(R.id.swipe_option1)
        public void onClickGiveUp() {
            Realm realm=Realm.getDefaultInstance();

            realm.beginTransaction();
            workItem.setState(WorkItem.GIVEUP);
            realm.commitTransaction();

            notifyDataSetChanged();
        }

        @OnClick(R.id.swipe_option2)
        public void onClickSubmit() {
            Realm realm=Realm.getDefaultInstance();

            realm.beginTransaction();
            workItem.setState(WorkItem.SUBMIT);
            realm.commitTransaction();

            notifyDataSetChanged();
        }

        @Override
        public void onStartOpen(SwipeLayout layout) {

        }

        @Override
        public void onOpen(SwipeLayout layout) {
            workItem.setSwipeState(true);
        }

        @Override
        public void onStartClose(SwipeLayout layout) {

        }

        @Override
        public void onClose(SwipeLayout layout) {
            workItem.setSwipeState(false);
        }

        @Override
        public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

        }

        @Override
        public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

        }
    }
}
