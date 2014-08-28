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

    private Button vs_computer;
    private Button vs_human;
    private Button instructions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_menu_principal);

        TextView text = (TextView) this.findViewById(R.id.welcome);

        text.setText("Bienvenido a la tic_tac_toe");
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
                    startActivity(intent);
                }
            });
        }
        if (this.vs_human != null) {
            this.vs_human.setOnClickListener(new OnClickListener(){

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainMenuActivity.this, TicTacToeActivity.class);
                    intent.putExtra(TicTacToeActivity.KEY_VS, TicTacToeActivity.VS_HUMAN);
                    startActivity(intent);
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
        getMenuInflater().inflate(R.menu.menu_principal, menu);
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
}
