package com.rykuno.newstestapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rykuno.newstestapp.Objects.NewsArticle;
import com.rykuno.newstestapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by rykuno on 8/8/16.
 */
public class ArticleAdapter extends ArrayAdapter<NewsArticle> {
    private Context mContext;

    public ArticleAdapter(Context context, List<NewsArticle> objects) {
        super(context, 0, objects);
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        NewsArticle currentNewsArticle = getItem(position);
        MyViewHolder holder=null;

        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.news_list_item, parent, false);
            holder = new MyViewHolder((listItemView));
            listItemView.setTag(holder);
        }else{
            holder = (MyViewHolder) listItemView.getTag();
        }


        holder.title.setText(currentNewsArticle.getTitle());

        if (currentNewsArticle.getAuthor() == null){
            holder.author.setText(R.string.author_not_available);
        }else {
            holder.author.setText(currentNewsArticle.getAuthor());
        }

        if (currentNewsArticle.getThumbnail() != null) {
            Picasso.with(mContext).load(currentNewsArticle.getThumbnail()).into(holder.thumbnail);
        }else{
            holder.thumbnail.setImageResource(R.drawable.ic_image_not_available);
        }
        return listItemView;
    }

    private class MyViewHolder{
        TextView title;
        TextView author;
        ImageView thumbnail;
            public MyViewHolder(View v){
                title = (TextView) v.findViewById(R.id.title);
                author = (TextView) v.findViewById(R.id.author);
                thumbnail = (ImageView) v.findViewById(R.id.thumbnail);
            }
    }

}
