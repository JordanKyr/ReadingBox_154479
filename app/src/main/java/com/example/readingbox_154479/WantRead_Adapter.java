package com.example.readingbox_154479;


import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readingbox_154479.database.ListBook;

import com.example.readingbox_154479.database.WantToRead;
import com.squareup.picasso.Picasso;



import java.util.ArrayList;

public class WantRead_Adapter extends RecyclerView.Adapter<WantRead_Adapter.wantViewHolder> {


    Context context;
    ArrayList<WantToRead> wantArrayList;
    private OnClickListener onClickListener;


    public WantRead_Adapter(Context context,  ArrayList<WantToRead> wantArrayList) {
        this.context = context;
        this.wantArrayList = wantArrayList;

    }
    @Override
    public void onBindViewHolder(@NonNull wantViewHolder holder, int position) {
        //assigning values to rows based on the recycler view position


        WantToRead want= wantArrayList.get(position);
        holder.author.setText(want.getTr_ISBN());
        holder.title.setText(want.getTr_UserID());

        //Picasso.get().load(want.getListCover()).into(holder.cover);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    onClickListener.onClick(position, want);
                }
            }
        });

    }





    @NonNull            //συνδεση κλασης με το view που θα εμφανισει τα στοιχεια
    @Override
    public WantRead_Adapter.wantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){

        View v=LayoutInflater.from(context).inflate(R.layout.recyclerview_row,parent,false);

        return new wantViewHolder(v);
    }




    @Override
    public int getItemCount() {
        return wantArrayList.size();
    }


    // public static class MySearchViewHolder extends RecyclerView.ViewHolder{
//giving vews from reycler view row layout file


    public static class wantViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView author,title;
        ImageView cover;
        String coverURL;
        public wantViewHolder(@NonNull View itemView){         //αναφορες στα αντικειμενα του view
            super(itemView);
            cover=itemView.findViewById(R.id.recyclerImage);
            author=itemView.findViewById(R.id.recyclerAuthor);
            title=itemView.findViewById(R.id.recyclerTitle);
            cardView=itemView.findViewById(R.id.cardHolder);



        }
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onClick(int position, WantToRead want);
    }


}
