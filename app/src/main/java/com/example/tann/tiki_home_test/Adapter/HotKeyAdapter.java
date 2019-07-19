package com.example.tann.tiki_home_test.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tann.tiki_home_test.Model.KeyWord;
import com.example.tann.tiki_home_test.R;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Tann on 7/19/2019.
 */

public class HotKeyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private ArrayList<KeyWord> lsKeyword;
    private Context context;
    public HotKeyAdapter(Context context, ArrayList<KeyWord> lsKeyword) {
        this.context = context;
        this.lsKeyword = lsKeyword;
        }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View item = inflater.inflate(R.layout.layout_keywords, viewGroup, false);
        MyViewHolder holder = new MyViewHolder(item);
        return holder;
    }
    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int i) {
        KeyWord keyWord = lsKeyword.get(i);
        viewHolder.txtKeyword.setText(getFinalKeyword(keyWord.getWords()));
        viewHolder.cvKeyword.setCardBackgroundColor(getRandomColor());
    }
    private String getFinalKeyword(String words) {
        String result = words;
        String[] arrKeyword = words.split(" ");
        if (arrKeyword.length==1){
            return words;
        }
        int minWidth=words.length();
        int index=0;
        for(int i=0;i<arrKeyword.length;i++)
        {
            int maxLength;
            String head = strConcat(0,i,arrKeyword);
            String tail = strConcat(i+1,arrKeyword.length-1,arrKeyword);
            maxLength=(head.length()<tail.length())?tail.length():head.length();
            if (maxLength<minWidth){
                minWidth = maxLength;
                index = i;
            }
        }
        String head = strConcat(0,index,arrKeyword);
        String tail = strConcat(index+1,arrKeyword.length-1,arrKeyword);
        return head+"\n"+tail;
    }
    private String strConcat(int start, int end, String[] arrKeyword) {
        String result = "";
        for(int i = start; i < end;i++){
            result += arrKeyword[i]+" ";
        }
        result += arrKeyword[end];
        return result;
    }
    private int getRandomColor() {
        int[] arrColor = context.getResources().getIntArray(R.array.arr_colors);
        return arrColor[new Random().nextInt(arrColor.length)];
    }
    @Override
    public int getItemCount() {
        return lsKeyword.size();
    }
}
class MyViewHolder extends RecyclerView.ViewHolder {
    TextView txtKeyword;
    CardView cvKeyword;

    MyViewHolder(View itemView) {
        super(itemView);
        cvKeyword = itemView.findViewById(R.id.cvKeyword);
        txtKeyword = itemView.findViewById(R.id.txtKeyword);
    }
}
