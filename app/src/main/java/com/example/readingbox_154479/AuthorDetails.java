package com.example.readingbox_154479;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.readingbox_154479.adapters.BookSearch_Adapter;
import com.example.readingbox_154479.database.Authors;
import com.example.readingbox_154479.database.Books;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Filter;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

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
    ArrayList<Books> booksArrayList;
    RecyclerView recyclerView;
    CollectionReference collectionReference;

    String title;
    BookSearch_Adapter bookSearchAdapter;
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
        View view =inflater.inflate(R.layout.fragment_author_details, container, false);
        authorDetails=view.findViewById(R.id.textAuthorDet);  //συνδεση στοιχειων του layout
        photo=view.findViewById(R.id.imageAuthorPhoto);

        Bundle bundle =getArguments();                      //παιρνω απο το bundle το String
        String searchFirst=bundle.getString("firstName");
        String searchLast=bundle.getString("lastName");



        collectionReference=MainActivity.db.collection("Authors");
        Query query=collectionReference.where(Filter.and(
                Filter.equalTo("Surname", searchLast),                    //φιλτραρισμα δεδομενων απο το query στη βαση
                Filter.equalTo("FirstName",searchFirst)));


                query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override                                                                               //emfanisi stoixeion siggrafea
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
                                                                                                            //emfanisi vivlion toy siggrafea


        collectionReference=MainActivity.db.collection("Books");
        query=collectionReference.whereEqualTo("Author",searchFirst+" "+searchLast);

        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {



                recyclerView=view.findViewById(R.id.recycler_search);
                // To display the Recycler view linearly
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                booksArrayList =new ArrayList<Books>();
                bookSearchAdapter=new BookSearch_Adapter(getContext(),booksArrayList);
                recyclerView.setAdapter(bookSearchAdapter);

                recyclerView.setAdapter(bookSearchAdapter);

                bookSearchAdapter.setOnClickListener(new BookSearch_Adapter.OnClickListener() {
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