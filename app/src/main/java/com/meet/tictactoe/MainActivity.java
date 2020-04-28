package com.meet.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons=new Button[3][3];
    private boolean player1turn=true;
    private int roundcount;
    private int player1point;
    private int player2point;

    private TextView player1textview;
    private TextView player2textview;

    SharedPreferences sp;
    public static final String mypreference = "mypreference";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player1textview=findViewById(R.id.t1);
        player2textview=findViewById(R.id.t2);
        sp = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);


         if(sp.contains("p1")){
             player1point=Integer.parseInt(sp.getString("p1",""));

             player1textview.setText("Player 1 : "+player1point);

         }
        if(sp.contains("p2")){
            player2point=Integer.parseInt(sp.getString("p2",""));

            player2textview.setText("Player 2 : "+player2point);


        }
        for(int i=0;i<3;i++){

            for(int j=0;j<3;j++){

                String buttonID="b"+i+j;
                int resID=getResources().getIdentifier(buttonID,"id",getPackageName());
                buttons[i][j]=findViewById(resID);
                buttons[i][j].setOnClickListener(this);

            }
        }

        Button reset=findViewById(R.id.reset);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                player1point=0;
                player2point=0;
                updatepoint();
                resetboard();

                SharedPreferences.Editor editor=sp.edit();
                editor.putString("p1","0");
                editor.putString("p2","0");

                editor.commit();


            }
        });



    }

    @Override
    public void onClick(View V) {
        if(!((Button) V).getText().toString().equals("")){
            return;
        }
        if(player1turn){

            ((Button) V).setText("X");
        }else {
            ((Button) V).setText("O");

        }

        roundcount++;
        if(checkforwin()){

            if(player1turn){
                player1win();
            }else {
                player2win();

            }


        }
        else if(roundcount==9){

            draw();

        }else {
            player1turn=!player1turn;
        }

    }

    private boolean checkforwin(){

        String[][] field=new String[3][3];
        for(int i=0;i<3;i++){

            for(int j=0;j<3;j++){
                field[i][j]=buttons[i][j].getText().toString();

            }


        }

        for(int i=0;i<3;i++){
            if(field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][2].equals("") ){
                return true;
            }

        }
        for(int i=0;i<3;i++){
            if(field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[2][i].equals("") ){
                return true;
            }

        }

        if(field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[2][2].equals("") ){
            return true;
        }

        if(field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[2][0].equals("") ){
            return true;
        }
        return false;
    }
    private void player1win(){

        player1point++;


        Toast.makeText( MainActivity.this, "Player 1 win!", Toast.LENGTH_SHORT).show();
     updatepoint();
     resetboard();

    }
    private void player2win(){
        player2point++;
      Toast.makeText( MainActivity.this, "Player 2 win!", Toast.LENGTH_SHORT).show();


        updatepoint();
        resetboard();

    }
    private void draw(){
resetboard();
    }

    private void updatepoint(){

        player1textview.setText("Player 1 : "+player1point);
        player2textview.setText("Player 2 : "+player2point);

        SharedPreferences.Editor editor=sp.edit();
        editor.putString("p1",player1point+"");
        editor.putString("p2",player2point+"");

        editor.commit();

        Toast.makeText(this, "p1 point"+player1point , Toast.LENGTH_SHORT).show();



    }private void resetboard(){

        for (int i=0;i<3;i++){
            for(int j=0;j<3;j++){

                buttons[i][j].setText("");

            }
            roundcount=0;
            player1turn=true;

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


    }
}
