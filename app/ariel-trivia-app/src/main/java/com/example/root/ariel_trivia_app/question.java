package com.example.root.ariel_trivia_app;

public class question implements java.io.Serializable{
    public String _question, _ans1, _ans2, _ans3, _ans4;
    public int _trueAns;

    public question(String question, String ans1, String ans2, String ans3, String ans4, int trueAns, int difficulty) {
        // TODO Auto-generated constructor stub
        _question = question;
        _ans1 = ans1;
        _ans2 = ans2;
        _ans3 = ans3;
        _ans4 = ans4;
        _trueAns = trueAns;
    }

    public static question  getQuestion(int difficulty){
        question ans = new question("we will succeed?", "no", "mabe", "yes", "who cares", 3, difficulty);
        return ans;
    }
}
