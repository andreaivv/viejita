package com.throoze.viejita;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.throoze.viejita.adapters.ViejaAdapter;


public class TicTacToeActivity extends Activity {

    public static final String KEY_VS = "vs_who";
    public static final boolean VS_COMPUTER = true;
    public static final boolean VS_HUMAN = false;
    public static final boolean PLAYER_1 = true;
    public static final boolean PLAYER_2 = false;
    private boolean playing;
    private TextView who_called_me;
    private byte[] board = new byte[9];
    private GridView boardView;
    private ViejaAdapter mViejaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vieja);

        boolean who = this.getIntent().getExtras().getBoolean(KEY_VS);

        this.who_called_me = (TextView) this.findViewById(R.id.who_called_me);
        this.boardView= (GridView) this.findViewById(R.id.board);

        for (int i = 0; i < this.board.length; i++)
            this.board[i] = 0;

        if (who == VS_COMPUTER) {
            this.who_called_me.setText(getResources().getText(R.string.msg_vs_computer));
        } else if (who == VS_HUMAN) {
            this.who_called_me.setText(getResources().getText(R.string.msg_vs_human));
        }
        this.mViejaAdapter = new ViejaAdapter(this,board);
        this.boardView.setAdapter(this.mViejaAdapter);

        boardView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                if (playing == PLAYER_1) {
                    board[position] = ViejaAdapter.VALUE_X;
                    Log.d()
                } else if (playing == PLAYER_2) {
                    board[position] = ViejaAdapter.VALUE_O;
                }
                mViejaAdapter.notifyDataSetChanged();
                //Toast.makeText(TicTacToeActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });
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
            Toast.makeText(getApplicationContext(), getResources().getText(R.string.msg_not_implemented), Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
