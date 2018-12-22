package com.example.root.ariel_trivia_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.root.ariel_trivia_app.base.Comment;
import com.example.root.ariel_trivia_app.R;

import java.util.List;

public class CommentAdapter extends ArrayAdapter<Comment>{

    private List<Comment> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView user;
        TextView message;
    }

    public CommentAdapter(List<Comment> data, Context context) {
        super(context, R.layout.comment_row, data);
        this.dataSet = data;
        this.mContext=context;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Comment comment = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        viewHolder = new ViewHolder();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.comment_row, parent, false);
        viewHolder.user = (TextView) convertView.findViewById(R.id.user);
        viewHolder.message = (TextView) convertView.findViewById(R.id.message);
        viewHolder.user.setText(comment.getUsername());
        viewHolder.message.setText(comment.getMessage());

            convertView.setTag(viewHolder);

        return convertView;
    }
}