package com.example.readingbox_154479;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Filter;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserHome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserHome extends Fragment {

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
    TextView searchResult;
    ImageButton button ;
    EditText searchString;
    CollectionReference collectionReference;

    String title;
String search;

ConstraintLayout resultLayout;

    OnBookSendListener bookSendListener;
    public interface OnBookSendListener{
        public void onBookSend(String message);
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_user_home, container, false);
welcome=view.findViewById(R.id.TextUserWelcome);
        Bundle bundle =getArguments();
        String message=bundle.getString("username");
        welcome.setText("Welcome "+message);

        searchResult=view.findViewById(R.id.textSearchRes);
        button=view.findViewById(R.id.imageButtonSearch);
        searchString=view.findViewById(R.id.editSearchBook);
        resultLayout=view.findViewById(R.id.resultLayout);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                search=searchString.getText().toString();
                collectionReference=MainActivity.db.collection("Books");
                Query query=collectionReference.where(Filter.or(
                        Filter.equalTo("Author",search),
                        Filter.equalTo("Title", search)
                ));
                query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String result="";
                        for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                            Books books=documentSnapshot.toObject(Books.class);
                            String author=books.getAuthor();
                            title=books.getTitle();
      //test                      TextView textViewResult=new TextView(getContext());
     //                       textViewResult.setText(" Author: "+author+"\n Title: "+title+"\n"+"\n");

                            result+=" Author: "+author+"\n Title: "+title+"\n"+"\n";
                        }
                        searchResult.setText(result);

                        searchResult.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                bookSendListener.onBookSend(title);

                            }
                        });

                    }
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

    @Override
    public void onResume(){
        super.onResume();
        searchString.setText("");

    }
}