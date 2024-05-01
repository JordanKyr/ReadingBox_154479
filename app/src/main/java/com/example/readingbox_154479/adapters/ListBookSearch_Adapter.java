package com.example.readingbox_154479.adapters;


import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readingbox_154479.MainActivity;
import com.example.readingbox_154479.R;
import com.example.readingbox_154479.database.ListBook;

import com.squareup.picasso.Picasso;



import java.util.ArrayList;
import java.util.List;

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

        int pst=position;
        ListBook books= booksArrayList.get(pst);
        holder.author.setText(books.getListAuthor());
        holder.title.setText(books.getListTitle());
        holder.removeBook.setVisibility(View.VISIBLE);  //emfanisi koumpioy gia diagrafi apo lista
        Picasso.get().load(books.getListCover()).into(holder.cover);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    onClickListener.onClick(pst, books);

                }
            }
        });



        holder.removeBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(holder.removeBook.getContext(), "Removed "+holder.title.getText(), Toast.LENGTH_LONG).show();
                MainActivity.listDatabase.rbDao().deleteListBook(books);

                ArrayList<ListBook> toRead= (ArrayList<ListBook>) MainActivity.listDatabase.rbDao().getBooksToRead(MainActivity.global_userID);
                setData(toRead);
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

        ImageButton removeBook;
        public booksViewHolder(@NonNull View itemView){         //αναφορες στα αντικειμενα του view
            super(itemView);
            cover=itemView.findViewById(R.id.recyclerImage);
            author=itemView.findViewById(R.id.recyclerAuthor);
            title=itemView.findViewById(R.id.recyclerTitle);
            cardView=itemView.findViewById(R.id.cardHolder);
            removeBook=itemView.findViewById(R.id.btnRemoveBook);




        }
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onClick(int position, ListBook books);
    }


    public void setData(ArrayList<ListBook> newList){
        this.booksArrayList.clear();
        booksArrayList.addAll(newList);
        notifyDataSetChanged();


    }


}
