package com.throoze.viejita;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.throoze.viejita.adapters.TicTacToeAdapter;

import java.util.ArrayList;


public class TicTacToeActivity extends Activity {

    public static final String TAG = "TicTacToeActivity";
    public static final String KEY_VS = "vs_who";
    public static final String KEY_SCORE_P1 = "score_p1";
    public static final String KEY_SCORE_P2 = "score_p2";
    public static final boolean VS_COMPUTER = true;
    public static final boolean VS_HUMAN = false;
    public static final boolean PLAYER_1 = true;
    public static final boolean PLAYER_2 = false;
    public static final int BOARD_SIZE = 9;
    private boolean playing;
    private int score_p1 = 0;
    private int score_p2 = 0;
    private TextView who_called_me;
    private TextView txt_score_p1;
    private TextView txt_score_p2;
    private GridView boardView;
    private ArrayList<Integer> board = new ArrayList<Integer>(BOARD_SIZE);
    private TicTacToeAdapter mTicTacToeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe);

        boolean who = this.getIntent().getExtras().getBoolean(KEY_VS);

        this.who_called_me = (TextView) this.findViewById(R.id.who_called_me);
        this.boardView= (GridView) this.findViewById(R.id.board);
        this.txt_score_p1 = (TextView) this.findViewById(R.id.score_p1);
        this.txt_score_p2 = (TextView) this.findViewById(R.id.score_p2);

        for (int i = 0; i < BOARD_SIZE; i++)
            this.board.add(i, TicTacToeAdapter.VALUE_EMPTY);

        Log.d(TAG, "Empty Board: "+this.board.toString());

        if (who == VS_COMPUTER) {
            this.who_called_me.setText(getResources().getText(R.string.msg_vs_computer));
        } else if (who == VS_HUMAN) {
            this.who_called_me.setText(getResources().getText(R.string.msg_vs_human));
        }
        this.mTicTacToeAdapter = new TicTacToeAdapter(this,board);
        this.boardView.setAdapter(this.mTicTacToeAdapter);
        this.boardView.setNumColumns(3);
        this.boardView.setVerticalSpacing(getResources().getDimensionPixelSize(R.dimen.box_v_padding));
        this.boardView.setHorizontalSpacing(getResources().getDimensionPixelSize(R.dimen.box_h_padding));
        this.boardView.setPadding(
                getResources().getDimensionPixelSize(R.dimen.box_h_padding),
                getResources().getDimensionPixelSize(R.dimen.box_v_padding),
                getResources().getDimensionPixelSize(R.dimen.box_h_padding),
                getResources().getDimensionPixelSize(R.dimen.box_v_padding)
        );
        this.playing = PLAYER_1;

        boardView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                if (board.get(position) == TicTacToeAdapter.VALUE_EMPTY) {
                    if (playing == PLAYER_1) {
                        board.set(position, TicTacToeAdapter.VALUE_X);
                        mTicTacToeAdapter.updateBoard(position, TicTacToeAdapter.VALUE_X);
                        playing = PLAYER_2;
                        Log.d(TAG, "Player 1 clicked in position "+position+".");
                    } else if (playing == PLAYER_2) {
                        board.set(position, TicTacToeAdapter.VALUE_O);
                        mTicTacToeAdapter.updateBoard(position, TicTacToeAdapter.VALUE_O);
                        playing = PLAYER_1;
                        Log.d(TAG, "Player 2 clicked in position "+position+".");
                    }
                    mTicTacToeAdapter.notifyDataSetChanged();
                    boardView.invalidateViews();
                    boardView.setAdapter(mTicTacToeAdapter);
                    checkForWinner();
                    checkIfGameOver();
                }
                Log.d(TAG,"Board: "+board.toString());
            }
        });
    }

    private void checkIfGameOver() {
        boolean game_over = true;
        for (int i = 0; i < board.size(); i++)
            if (board.get(i) == TicTacToeAdapter.VALUE_EMPTY)
                game_over = false;
        if (game_over)
            dialogRestartGame();
    }

    private void checkForWinner() {
        int[] line;
        // Chequeo de columnas
        for (int i = 0; i < 3; i++) {
            if (this.board.get(i) != TicTacToeAdapter.VALUE_EMPTY &&
                    this.board.get(i) == this.board.get(i+3) && this.board.get(i+3) == this.board.get(i+6)){
                line = new int[]{i,i+3,i+6};
                winnerInColumn(i,line);
                return;
            }
        }
        // Chequeo de filas
        int rowCounter = 0;
        for (int i = 0; i < 9; i = i+3) {
            if (this.board.get(i) != TicTacToeAdapter.VALUE_EMPTY &&
                    this.board.get(i) == this.board.get(i+1) && this.board.get(i+1) == this.board.get(i+2)){
                line = new int[]{i,i+1,i+2};
                winnerInRow(rowCounter,line);
                return;
            }
            rowCounter++;
        }
        // Chequeo de diagonal 1
        if (this.board.get(0) != TicTacToeAdapter.VALUE_EMPTY &&
                this.board.get(0) == this.board.get(4) && this.board.get(4) == this.board.get(8)) {
            line = new int[]{0,4,8};
            winnerInDiagonal(0,line);
            return;
        }
        if (this.board.get(2) != TicTacToeAdapter.VALUE_EMPTY &&
                this.board.get(2) == this.board.get(4) && this.board.get(4) == this.board.get(6)) {
            line = new int[]{2,4,6};
            winnerInDiagonal(1,line);
            return;
        }
    }

    private void winnerInDiagonal(int i, int[] line) {
        winner(line);
        Toast.makeText(this.getApplicationContext(),"Winner in Diagonal "+i,Toast.LENGTH_SHORT).show();
    }

    private void winnerInRow(int i, int[] line) {
        winner(line);
        Toast.makeText(this.getApplicationContext(),"Winner in Row "+i,Toast.LENGTH_SHORT).show();

    }

    private void winnerInColumn(int i, int[] line) {
        winner(line);
        Toast.makeText(this.getApplicationContext(),"Winner in Column "+i,Toast.LENGTH_SHORT).show();

    }

    private void winner(int[] line){
        if (board.get(line[0]) == TicTacToeAdapter.VALUE_X) {
            board.set(line[0], TicTacToeAdapter.VALUE_X_H);
            board.set(line[1], TicTacToeAdapter.VALUE_X_H);
            board.set(line[2], TicTacToeAdapter.VALUE_X_H);
            mTicTacToeAdapter.updateBoard(line[0], TicTacToeAdapter.VALUE_X_H);
            mTicTacToeAdapter.updateBoard(line[1], TicTacToeAdapter.VALUE_X_H);
            mTicTacToeAdapter.updateBoard(line[2], TicTacToeAdapter.VALUE_X_H);
            playing = PLAYER_2;
        } else if (board.get(line[0]) == TicTacToeAdapter.VALUE_O) {
            board.set(line[0], TicTacToeAdapter.VALUE_O_H);
            board.set(line[1], TicTacToeAdapter.VALUE_O_H);
            board.set(line[2], TicTacToeAdapter.VALUE_O_H);
            mTicTacToeAdapter.updateBoard(line[0], TicTacToeAdapter.VALUE_O_H);
            mTicTacToeAdapter.updateBoard(line[1], TicTacToeAdapter.VALUE_O_H);
            mTicTacToeAdapter.updateBoard(line[2], TicTacToeAdapter.VALUE_O_H);
            playing = PLAYER_1;
        }
        mTicTacToeAdapter.notifyDataSetChanged();
        boardView.invalidateViews();
        boardView.setAdapter(mTicTacToeAdapter);
        if(board.get(line[0]) == TicTacToeAdapter.VALUE_X_H) {
            this.score_p1++;
            this.txt_score_p1.setText(String.valueOf(this.score_p1));
        } else if (board.get(line[0]) == TicTacToeAdapter.VALUE_O_H) {
            this.score_p2++;
            this.txt_score_p2.setText(String.valueOf(this.score_p2));
        }
        dialogRestartGame();
    }

    private void resetBoard(){
        for (int position = 0; position < this.board.size(); position++)
            this.board.set(position, TicTacToeAdapter.VALUE_EMPTY);
    }

    private void restartGame() {
        this.resetBoard();
        mTicTacToeAdapter.resetBoard();
        mTicTacToeAdapter.notifyDataSetChanged();
        boardView.invalidateViews();
        boardView.setAdapter(mTicTacToeAdapter);
    }

    private void dialogRestartGame() throws Resources.NotFoundException {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.msg_game_over))
                .setMessage(R.string.msg_ask_restart)
                .setPositiveButton(getResources().getString(R.string.yes),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                restartGame();
                                dialog.dismiss();
                            }
                        })
                .setNegativeButton(getResources().getString(R.string.no),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                                prepareResult();
                                finish();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void prepareResult(){
        Intent resultIntent = new Intent();
        resultIntent.putExtra(KEY_SCORE_P1, score_p1);
        resultIntent.putExtra(KEY_SCORE_P2, score_p2);
        setResult(Activity.RESULT_OK, resultIntent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tic_tac_toe, menu);
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

    @Override
    public void onBackPressed() {
        prepareResult();
        super.onBackPressed();
    }
}
