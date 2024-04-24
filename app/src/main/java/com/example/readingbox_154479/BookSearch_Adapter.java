package com.example.readingbox_154479;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

import org.checkerframework.checker.units.qual.N;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class BookSearch_Adapter extends RecyclerView.Adapter<BookSearch_Adapter.booksViewHolder> {


    Context context;
    ArrayList<Books> booksArrayList;
    private OnClickListener onClickListener;


    public BookSearch_Adapter(Context context, ArrayList<Books> booksArrayList) {
        this.context = context;
        this.booksArrayList = booksArrayList;

    }
    @Override
   public void onBindViewHolder(@NonNull booksViewHolder holder, int position) {
        //assigning values to rows based on the recycler view position


        Books books= booksArrayList.get(position);
        holder.author.setText(books.getAuthor());
        holder.title.setText(books.getTitle());

        Picasso.get().load(books.getCover()).into(holder.cover);

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
    public BookSearch_Adapter.booksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){

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
        void onClick(int position, Books books);
    }


}
