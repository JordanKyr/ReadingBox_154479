package com.example.readingbox_154479;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Filter;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AuthorsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AuthorsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AuthorsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AuthorsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AuthorsFragment newInstance(String param1, String param2) {
        AuthorsFragment fragment = new AuthorsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    TextView welcome;
    ImageButton button ;
    EditText searchString;
    CollectionReference collectionReference;
    String firstName, lastName, search;
    RecyclerView recyclerView;
    AuthorSearch_Adapter adapter;
    ArrayList<Authors> authorsArrayList;

    LinearLayoutCompat resultLayout;  //φτιαχνω ενα αντικειμενο για το Linear Layout ωστε να το γεμισω με textview για τα αποτελεσματα
   OnAuthorSendListener authorSendListener;

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
        View view =inflater.inflate(R.layout.fragment_authors, container, false);
        welcome=view.findViewById(R.id.TextUserWelcome);
        Bundle bundle =getArguments();
        //String message=bundle.getString("username");
        welcome.setText("Welcome "+MainActivity.username);

        button=view.findViewById(R.id.imageButtonSearch);
        searchString=view.findViewById(R.id.editSearch);
        resultLayout=view.findViewById(R.id.resultLayout);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){





                    search = searchString.getText().toString().trim();

                 String[]   splitSearch = search.split(" ");

                    collectionReference = MainActivity.db.collection("Authors");                //αναζητηση και φιλτραρισμα στη βαση
                Query query;

                if(splitSearch.length>1){
                     query = collectionReference.where(Filter.or(
                            Filter.equalTo("FirstName", splitSearch[0]),
                            Filter.equalTo("Surname", splitSearch[1]),
                            Filter.equalTo("Surname", splitSearch[0]),
                            Filter.equalTo("Firstname", splitSearch[1])
                    ));}
                else{
                    query = collectionReference.where(Filter.or(
                            Filter.equalTo("FirstName", splitSearch[0]),

                            Filter.equalTo("Surname", splitSearch[0])
                          ));

                }


                    query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {


                            recyclerView = view.findViewById(R.id.recycler_search);
                            // To display the Recycler view linearly
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                            authorsArrayList = new ArrayList<Authors>();
                            adapter = new AuthorSearch_Adapter(getContext(), authorsArrayList);
                            recyclerView.setAdapter(adapter);

                            recyclerView.setAdapter(adapter);

                            adapter.setOnClickListener(new AuthorSearch_Adapter.OnClickListener() {
                                @Override
                                public void onClick(int position, Authors authors) {
                                    lastName = authors.getSurname();
                                    authorSendListener.onAuthorSend(lastName);
                                }
                            });

                            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {        //για καθε αντικειμενο που μου δινει το query
                                authorsArrayList.add(documentSnapshot.toObject(Authors.class));          //προσθεση και εμφανιση στοιχειων στο recycler

                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "query operation failed.", Toast.LENGTH_LONG).show();
                        }
                    });



            }
        }      );


        return view;

    }


    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        Activity activity=(Activity) context;
        try{
            authorSendListener=(OnAuthorSendListener) activity;
        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString()+" must implement onAuthorSend ");
        }
    }

    public interface OnAuthorSendListener{
        public void onAuthorSend(String message);
    }


    @Override
    public void onResume(){
        super.onResume();


    }

    @Override
    public void onStart(){
        super.onStart();


    }
}