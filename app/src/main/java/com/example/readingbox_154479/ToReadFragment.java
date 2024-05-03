package com.example.readingbox_154479;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.helper.widget.Carousel;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.readingbox_154479.adapters.ListBookSearch_Adapter;
import com.example.readingbox_154479.adapters.WantRead_Adapter;
import com.example.readingbox_154479.database.ListBook;
import com.example.readingbox_154479.database.WantToRead;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ToReadFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ToReadFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ToReadFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ToReadFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ToReadFragment newInstance(String param1, String param2) {
        ToReadFragment fragment = new ToReadFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    WantRead_Adapter adapter;
    ListBookSearch_Adapter adapterBook;

    RecyclerView recyclerView;
    LinearLayoutCompat resultLayout;
    TextView welcome;
String title;
OnBookSendListener bookSendListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_to_read, container, false);

        resultLayout=view.findViewById(R.id.resultLayout);
        welcome=view.findViewById(R.id.TextUserWelcome);
        welcome.setText("Welcome "+MainActivity.username);

        ArrayList<ListBook> toRead= (ArrayList<ListBook>) MainActivity.listDatabase.rbDao().getBooksToRead(MainActivity.global_userID);

        recyclerView = view.findViewById(R.id.recycler_search);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        adapterBook = new ListBookSearch_Adapter(getContext(), toRead);
        recyclerView.setAdapter(adapterBook);


        adapterBook.setOnClickListener(new ListBookSearch_Adapter.OnClickListener() {
            @Override
            public void onClick(int position, ListBook books) {
                title=books.getListTitle();
                bookSendListener.onBookSend(title);
            }
        });



        return view;
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        Activity activity=(Activity) context;
        try{
            bookSendListener=(OnBookSendListener) activity;
        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString()+" must implement onBookSend ");
        }
    }


    public interface OnBookSendListener{
        public void onBookSend(String message);
    }

}