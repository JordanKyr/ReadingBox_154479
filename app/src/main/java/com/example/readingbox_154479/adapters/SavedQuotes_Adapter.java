package com.example.readingbox_154479.adapters;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readingbox_154479.MainActivity;
import com.example.readingbox_154479.R;
import com.example.readingbox_154479.database.ListBook;

import com.example.readingbox_154479.database.Quotes;
import com.example.readingbox_154479.database.Saved_Quotes;
import com.squareup.picasso.Picasso;



import java.util.ArrayList;

public class SavedQuotes_Adapter extends RecyclerView.Adapter<SavedQuotes_Adapter.quotesViewHolder> {


    Context context;
    ArrayList<Saved_Quotes> savedQuotesArrayList;
    private OnClickListener onClickListener;
    int qidRef;
    String isbnRef;
    ListBook listBookRef;

    public SavedQuotes_Adapter(Context context, ArrayList<Saved_Quotes> savedQuotesArrayList) {
        this.context = context;
        this.savedQuotesArrayList = savedQuotesArrayList;
    }
    @Override
    public void onBindViewHolder(@NonNull quotesViewHolder holder, int position) {
        //assigning values to rows based on the recycler view position

        int pst=position;

        if (position%2 == 0) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context,R.color.lighter_blue_gray));
        } else {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context,R.color.light_blue_gray));
        }

        Saved_Quotes savedQuotes= savedQuotesArrayList.get(pst);
        qidRef=savedQuotes.getQuotesQID();  //quote id tou vivlioy tis thesis
        isbnRef=savedQuotes.getQuotesISBN();

        String txt_Quote= (String) MainActivity.listDatabase.rbDao().getTextQuote(MainActivity.global_userID,qidRef);

        listBookRef=MainActivity.listDatabase.rbDao().getQuotedBook(isbnRef,MainActivity.global_userID,qidRef);

        holder.text_quote.setText(txt_Quote);    //pernao to keimeno tou list   N ALLAKSO SQL

        holder.author.setText(listBookRef.getListAuthor());
        holder.title.setText(listBookRef.getListTitle());
        holder.removeQuote.setVisibility(View.VISIBLE);  //emfanisi koumpioy gia diagrafi apo lista
        Picasso.get().load(listBookRef.getListCover()).into(holder.cover);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    onClickListener.onClick(pst, savedQuotes);

                }
            }
        });

        holder.removeQuote.setOnClickListener(new View.OnClickListener() {
            @Override                                   //diagrafi quote
            public void onClick(View v) {
                Toast.makeText(holder.removeQuote.getContext(), "Removed Quote", Toast.LENGTH_LONG).show();
                MainActivity.listDatabase.rbDao().deleteQuote(savedQuotes);

                ArrayList<Saved_Quotes> arraySavedQuotes= (ArrayList<Saved_Quotes>) MainActivity.listDatabase.rbDao().getSavedQuotes(MainActivity.global_userID);
                setData(arraySavedQuotes);
            }
        });

    }





    @NonNull            //συνδεση κλασης με το view που θα εμφανισει τα στοιχεια
    @Override
    public SavedQuotes_Adapter.quotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){

        View v=LayoutInflater.from(context).inflate(R.layout.quote_recycler,parent,false);

            return new quotesViewHolder(v);
    }




    @Override
    public int getItemCount() {
        return savedQuotesArrayList.size();
    }


    // public static class MySearchViewHolder extends RecyclerView.ViewHolder{
//giving vews from reycler view row layout file


    public static class quotesViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView author,title, text_quote;
        ImageView cover;
        String coverURL;

        ImageButton removeQuote;
        public quotesViewHolder(@NonNull View itemView){         //αναφορες στα αντικειμενα του view
            super(itemView);
            cover=itemView.findViewById(R.id.quoteImage);
            author=itemView.findViewById(R.id.quoteAuthor);
            title=itemView.findViewById(R.id.quoteTitle);
            cardView=itemView.findViewById(R.id.cardHolder);
            removeQuote=itemView.findViewById(R.id.btnRemoveQuote);
            text_quote=itemView.findViewById(R.id.txtQuoteHere);



        }
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onClick(int position, Saved_Quotes quotes);
    }


    public void setData(ArrayList<Saved_Quotes> newList){
        this.savedQuotesArrayList.clear();
        savedQuotesArrayList.addAll(newList);
        notifyDataSetChanged();


    }


}
