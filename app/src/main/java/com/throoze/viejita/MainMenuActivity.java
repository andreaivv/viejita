package com.throoze.viejita;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainMenuActivity extends Activity {

    private static final int TIC_TAC_TOE = 0;
    private Button vs_computer;
    private Button vs_human;
    private Button instructions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main_menu);

        TextView text = (TextView) this.findViewById(R.id.welcome);

        text.setText(getResources().getText(R.string.welcome));
        text.setTextSize(30.0f);
        text.setGravity(Gravity.CENTER_HORIZONTAL);

        this.vs_computer = (Button) this.findViewById(R.id.vs_computer);
        this.vs_human = (Button) this.findViewById(R.id.vs_human);
        this.instructions = (Button) this.findViewById(R.id.instructions);

    }

    @Override
    protected void onStart(){
        super.onStart();
        if (this.vs_computer != null) {
            this.vs_computer.setOnClickListener(new OnClickListener(){

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainMenuActivity.this, TicTacToeActivity.class);
                    intent.putExtra(TicTacToeActivity.KEY_VS, TicTacToeActivity.VS_COMPUTER);
                    startActivityForResult(intent,TIC_TAC_TOE);
                }
            });
        }
        if (this.vs_human != null) {
            this.vs_human.setOnClickListener(new OnClickListener(){

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainMenuActivity.this, TicTacToeActivity.class);
                    intent.putExtra(TicTacToeActivity.KEY_VS, TicTacToeActivity.VS_HUMAN);
                    startActivityForResult(intent,TIC_TAC_TOE);
                }
            });
        }
        if (this.instructions != null) {
            this.instructions.setOnClickListener(new OnClickListener(){

                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(),getResources().getText(R.string.msg_not_implemented),Toast.LENGTH_LONG).show();
                }
            });
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Toast.makeText(getApplicationContext(),getResources().getText(R.string.msg_not_implemented),Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (TIC_TAC_TOE) : {
                if (resultCode == Activity.RESULT_OK) {
                    int score_p1 = data.getIntExtra(TicTacToeActivity.KEY_SCORE_P1,0);
                    int score_p2 = data.getIntExtra(TicTacToeActivity.KEY_SCORE_P2,0);
                    Toast.makeText(
                            getApplicationContext(),
                            getResources().getText(R.string.msg_game_over)+
                                    " " + score_p1 + " - " + score_p2 +".",
                            Toast.LENGTH_LONG
                    ).show();
                }
                break;
            }
        }
    }
}
