package com.example.root.ariel_trivia_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.root.ariel_trivia_app.R;
import com.example.root.ariel_trivia_app.base.Comment;
import com.example.root.ariel_trivia_app.base.Trivia;

import java.util.List;

public class TriviaAdapter extends ArrayAdapter<Trivia> {

    private List<Trivia> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView difficulty;
        TextView question;
        TextView likes;
    }

    public TriviaAdapter(List<Trivia> data, Context context) {
        super(context, R.layout.adapter_trivia, data);
        this.dataSet = data;
        this.mContext=context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Trivia trivia = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        TriviaAdapter.ViewHolder viewHolder; // view lookup cache stored in tag

        viewHolder = new TriviaAdapter.ViewHolder();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.adapter_trivia, parent, false);
        viewHolder.difficulty = (TextView) convertView.findViewById(R.id.trivia_difficulty);
        viewHolder.likes = (TextView) convertView.findViewById(R.id.trivia_likes);
        viewHolder.question = (TextView) convertView.findViewById(R.id.trivia_question);
        viewHolder.difficulty.setText(trivia.getQuestion().getDifficulty()+"");
        viewHolder.likes.setText(trivia.getQuestion().getLikes()+"");
        viewHolder.question.setText(trivia.getQuestion().getQuestion());


        convertView.setTag(viewHolder);

        return convertView;
    }
}
