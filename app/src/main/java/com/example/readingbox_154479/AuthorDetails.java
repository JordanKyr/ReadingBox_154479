package com.example.readingbox_154479;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Filter;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AuthorDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AuthorDetails extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AuthorDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AuthorDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static AuthorDetails newInstance(String param1, String param2) {
        AuthorDetails fragment = new AuthorDetails();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    ImageView photo;
    TextView authorDetails;

    CollectionReference collectionReference;


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
        View view =inflater.inflate(R.layout.fragment_author_details, container, false);
        authorDetails=view.findViewById(R.id.textAuthorDet);  //συνδεση στοιχειων του layout
        photo=view.findViewById(R.id.imageAuthorPhoto);

        Bundle bundle =getArguments();                      //παιρνω απο το bundle το String
        String searchAuthor=bundle.getString("firstname");



        collectionReference=MainActivity.db.collection("Authors");
        Query query=collectionReference.where(
                Filter.equalTo("FirstName", searchAuthor)                    //φιλτραρισμα δεδομενων απο το query στη βαση
        );
        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                String result="";
                String imgURL="";
                for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                    Authors authors=documentSnapshot.toObject(Authors.class);
                    String firstname= authors.getFirstName();
                    Integer byear= authors.getBirthYear();
                    String lastname=authors.getSurname();
                    imgURL=authors.getPhoto();
                    String country=authors.getCountry();


                    result+=" Author: "+firstname+" "+lastname+"\n Birth Year: "+byear+"\n Country: "+country +"\n";
                }
                authorDetails.setText(result);
                Picasso.get().load(imgURL).into(photo);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(),"query operation failed.",Toast.LENGTH_LONG).show();
            }});

        return view;
    }
}