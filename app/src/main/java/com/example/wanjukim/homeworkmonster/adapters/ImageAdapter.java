package com.example.wanjukim.homeworkmonster.adapters;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.wanjukim.homeworkmonster.R;
import com.example.wanjukim.homeworkmonster.models.Image;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Wanju Kim on 2018-02-20.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder>  {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<Image> images=new ArrayList<>();
    private ImageClickListener onClickListener;

    public ImageAdapter(Context context) {
        this.context = context;
        this.layoutInflater=LayoutInflater.from(context);
    }

    public void addImages(List<Image> images){
        this.images.addAll(images);
    }

    public void setOnClickListener(ImageClickListener imageClickListener){
        onClickListener=imageClickListener;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageViewHolder(layoutInflater.inflate(R.layout.image_item,parent,false));
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        Image image=images.get(position);
        holder.bind(image);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    class ImageViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.image_layout)
        ConstraintLayout choiceIndicator;
        @BindView(R.id.iv_photo)
        ImageView ivImage;
        @BindColor(R.color.colorLightGray)
        int colorGray;
        @BindColor(R.color.colorDefault)
        int colorDefault;

        private Image image;

        public ImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        private void bind(Image image){
            this.image=image;
            Glide.with(context).load(image.getPath()).into(ivImage);

            if(image.isFlag()){
                choiceIndicator.setBackgroundColor(colorGray);
            } else {
                choiceIndicator.setBackgroundColor(colorDefault);
            }
        }

        @OnClick(R.id.iv_photo)
        public void onClick(){
            onClickListener.onClickImage(image);

            for(Image image : images)
                image.setFlag(false);

            image.setFlag(true);
            notifyDataSetChanged();
        }

    }
    public interface ImageClickListener{
        void onClickImage(Image image);
    }
}
