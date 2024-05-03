package com.example.readingbox_154479;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.readingbox_154479.adapters.SavedQuotes_Adapter;
import com.example.readingbox_154479.database.ListBook;
import com.example.readingbox_154479.database.Saved_Quotes;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SavedQuotesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SavedQuotesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView welcome;

    public SavedQuotesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SavedQuotesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SavedQuotesFragment newInstance(String param1, String param2) {
        SavedQuotesFragment fragment = new SavedQuotesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    SavedQuotes_Adapter savedQuotesAdapter;
    LinearLayoutCompat resultLayout;
    RecyclerView recyclerView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_saved_quotes, container, false);
        resultLayout=view.findViewById(R.id.resultLayout);
        welcome=view.findViewById(R.id.TextUserWelcome);
        welcome.setText("Welcome "+MainActivity.username);          //pairno ta apothikeumena quotes toy xristi

        ArrayList<Saved_Quotes> arraySavedQuotes= (ArrayList<Saved_Quotes>) MainActivity.listDatabase.rbDao().getSavedQuotes(MainActivity.global_userID);

        recyclerView = view.findViewById(R.id.recycler_search);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        savedQuotesAdapter=new SavedQuotes_Adapter(getContext(),arraySavedQuotes);

        recyclerView.setAdapter(savedQuotesAdapter);

        return view;
    }
}