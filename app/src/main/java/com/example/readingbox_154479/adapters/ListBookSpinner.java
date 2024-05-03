package com.example.readingbox_154479.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.readingbox_154479.R;
import com.example.readingbox_154479.database.ListBook;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ListBookSpinner  extends ArrayAdapter<ListBook> {


    LayoutInflater layoutInflater;
    public ListBookSpinner(@NonNull Context context, int resource, @NonNull List<ListBook> objects) {
        super(context, resource, objects);
        layoutInflater=LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable
    View convertView, @NonNull ViewGroup parent){
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable
    View convertView, @NonNull ViewGroup parent)
    {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView,
                          ViewGroup parent)
    {
        // It is used to set our custom view.
        if(convertView==null) convertView=layoutInflater.inflate(R.layout.custom_spinner,parent,false);


        ListBook listBook=getItem(position);
        TextView titleTextView=(TextView)convertView.findViewById(R.id.spinnerTitle);           //sindeo ta stoixeia apo to custom spinner view
        TextView authorTextView=(TextView) convertView.findViewById(R.id.spinnerAuthor);
        ImageView spinnerImage=(ImageView) convertView.findViewById(R.id.spinnerImage);

        //ta dino times
        titleTextView.setText(listBook.getListTitle());
        authorTextView.setText(listBook.getListAuthor());

        Picasso.get().load(listBook.getListCover()).into(spinnerImage);
        return convertView;
    }


}
