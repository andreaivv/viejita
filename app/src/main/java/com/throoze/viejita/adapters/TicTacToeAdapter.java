package com.throoze.viejita.adapters;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;

import com.throoze.viejita.R;

import java.util.ArrayList;

/**
 * Created by Victor De Ponte on 8/28/2014.
 */
public class TicTacToeAdapter extends BaseAdapter {

    public static final int VALUE_EMPTY = 0;
    public static final int VALUE_X = 1;
    public static final int VALUE_O = 2;
    public static final int VALUE_X_H = 3;
    public static final int VALUE_O_H = 4;
    private ArrayList<Integer> board;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public TicTacToeAdapter(Context context, ArrayList<Integer> board){
        this.mContext = context;
        this.board = board;
        this.mLayoutInflater = (LayoutInflater) this.mContext.getSystemService(this.mContext.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return this.board.size();
    }

    @Override
    public Object getItem(int position) {
        return board.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = null;
        int layoutId = -1;

        if (convertView == null) {
            switch (this.board.get(position)){
                case VALUE_EMPTY:
                    layoutId = R.layout.empty_box_layout;
                    break;
                case VALUE_X:
                    layoutId = R.layout.x_box_layout;
                    break;
                case VALUE_O:
                    layoutId = R.layout.o_box_layout;
                    break;
                case VALUE_X_H:
                    layoutId = R.layout.x_h_box_layout;
                    break;
                case VALUE_O_H:
                    layoutId = R.layout.o_h_box_layout;
                    break;
            }

            imageView = (ImageView) this.mLayoutInflater.inflate(layoutId,null);
            DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
            int width = metrics.widthPixels / 3;
            //int width = mContext.getResources().getDimensionPixelSize(R.dimen.box_size);
            int height = width;
            GridView.LayoutParams params = new GridView.LayoutParams(width, height);
            imageView.setLayoutParams(params);
        } else {
            imageView = (ImageView) convertView;
        }
        return imageView;
    }

    public void updateBoard(int position, Integer value) {
        this.board.set(position, value);
    }

    public void resetBoard() {
        for (int position = 0; position < this.board.size(); position++)
            this.board.set(position, VALUE_EMPTY);
    }

    public ArrayList<Integer> getBoard(){
        return this.board;
    }
}
