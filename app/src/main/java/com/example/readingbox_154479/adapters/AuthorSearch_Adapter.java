package com.example.readingbox_154479.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readingbox_154479.database.Authors;
import com.example.readingbox_154479.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AuthorSearch_Adapter extends RecyclerView.Adapter<AuthorSearch_Adapter.authorsViewHolder>{



    Context context;
    ArrayList<Authors> authorsArrayList;
    private AuthorSearch_Adapter.OnClickListener onClickListener;


    public AuthorSearch_Adapter(Context context, ArrayList<Authors> authorsArrayList) {
        this.context = context;
        this.authorsArrayList = authorsArrayList;

    }
    @Override
    public void onBindViewHolder(@NonNull AuthorSearch_Adapter.authorsViewHolder holder, int position) {
        //assigning values to rows based on the recycler view position

        int pst=position;
        Authors authors= authorsArrayList.get(pst);
        holder.firstname.setText(authors.getFirstName());
        holder.surname.setText(authors.getSurname());

        Picasso.get().load(authors.getPhoto()).into(holder.photo);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    onClickListener.onClick(pst,authors);
                }
            }
        });

    }





    @NonNull            //συνδεση κλασης με το view που θα εμφανισει τα στοιχεια
    @Override
    public AuthorSearch_Adapter.authorsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){

        View v= LayoutInflater.from(context).inflate(R.layout.recyclerview_row,parent,false);

        return new authorsViewHolder(v);
    }




    @Override
    public int getItemCount() {
        return authorsArrayList.size();
    }


    // public static class MySearchViewHolder extends RecyclerView.ViewHolder{
//giving vews from reycler view row layout file


    public static class authorsViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView firstname,surname;
        ImageView photo;
        String photoURL;
        public authorsViewHolder(@NonNull View itemView){         //αναφορες στα αντικειμενα του view
            super(itemView);
           photo=itemView.findViewById(R.id.spinnerImage);
         surname=itemView.findViewById(R.id.spinnerAuthor);
            firstname=itemView.findViewById(R.id.spinnerTitle);
            cardView=itemView.findViewById(R.id.cardHolder);



        }
    }

    public void setOnClickListener(AuthorSearch_Adapter.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onClick(int position, Authors authors);
    }


}
