package com.example.readingbox_154479;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Filter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.EventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserHome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserHome extends Fragment{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserHome() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserHome.
     */
    // TODO: Rename and change types and number of parameters
    public static UserHome newInstance(String param1, String param2) {
        UserHome fragment = new UserHome();
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
    TextView welcome;
    ImageButton button ;
    EditText searchString;
    CollectionReference collectionReference;
    String title, search;
    RecyclerView recyclerView;
    BookSearch_Adapter adapter;
    ArrayList<Books> booksArrayList;

    LinearLayoutCompat resultLayout;  //φτιαχνω ενα αντικειμενο για το Linear Layout ωστε να το γεμισω με textview για τα αποτελεσματα
    OnBookSendListener bookSendListener;







    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_user_home, container, false);
        welcome=view.findViewById(R.id.TextUserWelcome);
        Bundle bundle =getArguments();
        String message=bundle.getString("username");
        welcome.setText("Welcome "+message);

                               //συνδεω μεταβλητες με τις οντοτητες του layout
        button=view.findViewById(R.id.imageButtonSearch);
        searchString=view.findViewById(R.id.editSearchBook);
        resultLayout=view.findViewById(R.id.resultLayout);




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                search=searchString.getText().toString().trim();


                collectionReference=MainActivity.db.collection("Books");                //αναζητηση και φιλτραρισμα στη βαση

                Query query=collectionReference.where(Filter.or(
                        Filter.equalTo("Author",search),
                        Filter.equalTo("Title", search),
                        Filter.arrayContains("Genre",search)
                ));


                query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {



                        recyclerView=view.findViewById(R.id.recycler_search);
                        // To display the Recycler view linearly
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                        booksArrayList =new ArrayList<Books>();
                        adapter=new BookSearch_Adapter(getContext(),booksArrayList);
                        recyclerView.setAdapter(adapter);

                        recyclerView.setAdapter(adapter);

                        adapter.setOnClickListener(new BookSearch_Adapter.OnClickListener() {
                            @Override
                            public void onClick(int position, Books books) {
                                title=books.getTitle();
                                bookSendListener.onBookSend(title);
                            }
                        });

                    for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){        //για καθε αντικειμενο που μου δινει το query
                        booksArrayList.add(documentSnapshot.toObject(Books.class));          //προσθεση και εμφανιση στοιχειων στο recycler

                    }}
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(),"query operation failed.",Toast.LENGTH_LONG).show();
                    }});

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
            throw new ClassCastException(activity.toString()+" must implement onMessageSend ");
        }
    }


    public interface OnBookSendListener{
        public void onBookSend(String message);
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