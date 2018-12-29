package com.example.root.ariel_trivia_app.base;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.root.ariel_trivia_app.AddTriviaQuestionActivity;
import com.example.root.ariel_trivia_app.LoginActivity;
import com.example.root.ariel_trivia_app.R;
import com.example.root.ariel_trivia_app.RegisterActivity;
import com.example.root.ariel_trivia_app.SelectTriviaActivity;
import com.example.root.ariel_trivia_app.SuccessActivity;

public class MenuClick extends AppCompatActivity {

    public boolean click(Context packageContext, MenuItem item) {
        Intent i;
        switch (item.getItemId()) {
            case R.id.login:
                i = new Intent(packageContext, LoginActivity.class);
                break;
            case R.id.register:
                i = new Intent(packageContext, RegisterActivity.class);
                break;
            case R.id.byID:
                i = new Intent(packageContext, SelectTriviaActivity.class);
                break;
            case R.id.nyRandom:
                i = new Intent(packageContext, SelectTriviaActivity.class);
                break;
            case R.id.byManualy:
                i = new Intent(packageContext, SelectTriviaActivity.class);
                break;
            case R.id.addquestion:
                i = new Intent(packageContext, AddTriviaQuestionActivity.class);
                break;
            case R.id.logout:
                i = new Intent(packageContext, SuccessActivity.class);
                break;
            default:
                i = new Intent(packageContext, SuccessActivity.class);
                break;
        }
                startActivity(i);
        return true;
    }
}
