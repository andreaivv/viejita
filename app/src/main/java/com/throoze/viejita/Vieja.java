package com.throoze.viejita;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class Vieja extends Activity {

    public static final String KEY_VS = "vs_who";
    public static final boolean VS_COMPUTER = true;
    public static final boolean VS_HUMAN = false;
    private TextView who_called_me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vieja);

        this.who_called_me = (TextView) this.findViewById(R.id.who_called_me);

        boolean who = this.getIntent().getExtras().getBoolean(KEY_VS);

        if (who == VS_COMPUTER) {
            this.who_called_me.setText(getResources().getText(R.string.msg_vs_computer));
        } else if (who == VS_HUMAN) {
            this.who_called_me.setText(getResources().getText(R.string.msg_vs_human));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.vieja, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
