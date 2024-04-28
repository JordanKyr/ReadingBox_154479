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

import com.squareup.picasso.Picasso;



import java.util.ArrayList;

public class ListBookSearch_Adapter extends RecyclerView.Adapter<ListBookSearch_Adapter.booksViewHolder> {


    Context context;
    ArrayList<ListBook> booksArrayList;
    private OnClickListener onClickListener;


    public ListBookSearch_Adapter(Context context, ArrayList<ListBook> booksArrayList) {
        this.context = context;
        this.booksArrayList = booksArrayList;

    }
    @Override
    public void onBindViewHolder(@NonNull booksViewHolder holder, int position) {
        //assigning values to rows based on the recycler view position


        ListBook books= booksArrayList.get(position);
        holder.author.setText(books.getListAuthor());
        holder.title.setText(books.getListTitle());

        Picasso.get().load(books.getListCover()).into(holder.cover);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    onClickListener.onClick(position, books);
                }
            }
        });

    }





    @NonNull            //συνδεση κλασης με το view που θα εμφανισει τα στοιχεια
    @Override
    public ListBookSearch_Adapter.booksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){

        View v=LayoutInflater.from(context).inflate(R.layout.recyclerview_row,parent,false);

        return new booksViewHolder(v);
    }




    @Override
    public int getItemCount() {
        return booksArrayList.size();
    }


    // public static class MySearchViewHolder extends RecyclerView.ViewHolder{
//giving vews from reycler view row layout file


    public static class booksViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView author,title;
        ImageView cover;
        String coverURL;
        public booksViewHolder(@NonNull View itemView){         //αναφορες στα αντικειμενα του view
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
        void onClick(int position, ListBook books);
    }


}
