package com.example.readingbox_154479;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.readingbox_154479.database.ListBook;
import com.example.readingbox_154479.database.ListUser;
import com.example.readingbox_154479.database.WantToRead;
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
 * Use the {@link BookDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookDetails extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BookDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static BookDetails newInstance(String param1, String param2) {
        BookDetails fragment = new BookDetails();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    ImageView cover;
    TextView bookDetails;
 Button addWant, addRead;
    String  isbn="";
    String title="";
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
       View view =inflater.inflate(R.layout.fragment_book_details, container, false);
    bookDetails=view.findViewById(R.id.textBookDet);  //συνδεση στοιχειων του layout
    cover=view.findViewById(R.id.imageViewCover);


        addWant=view.findViewById(R.id.btnWant);
        addRead=view.findViewById(R.id.btnAlreadyRead);



        Bundle bundle =getArguments();                      //παιρνω απο το bundle το String
        String searchTitle=bundle.getString("title");



        collectionReference=MainActivity.db.collection("Books");
        Query query=collectionReference.where(
                Filter.equalTo("Title", searchTitle)                    //φιλτραρισμα δεδομενων απο το query στη βαση
        );
        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                String result="";
                String imgURL="";

                for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                    Books books=documentSnapshot.toObject(Books.class);
                    String author=books.getAuthor();
                    Integer pubyear=books.getPubYear();
                    title=books.getTitle();
                    imgURL=books.getCover();
                    isbn=documentSnapshot.getId();
                    List<String> genres= new ArrayList<>();   //παιρνω τη λιστα με το Genre και φτιαχνω ενα string
                    genres=  books.getGenre();


                    String genresString=genres.get(0);
                    for(int i=1; i<genres.size(); i++){
                        genresString+= ", " +genres.get(i);
                    }


                    result+=" Author: "+author+"\n Title: "+title+"\n Publication Year: "+pubyear+"\n Genre: "+genresString+"\n ISBN: "+ isbn +"\n";

                    addWant.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String var_uid=MainActivity.global_userID;
                            String var_bid=isbn;
                            try {
                                ListUser listUser=new ListUser();
                                listUser.setListUserID(var_uid);                                //ελεγχος για υπαρξη χρηστη---να μετακινηθει?
                                MainActivity.listDatabase.rbDao().upsertUser(listUser);


                                ListBook listBook=new ListBook();
                                listBook.setListISBN(var_bid);
                                MainActivity.listDatabase.rbDao().upsertBook(listBook);



                                WantToRead wantToRead = new WantToRead();
                                wantToRead.setTr_ISBN(var_bid);                 //δημιουργια αντικειμενου λιστας για διαβασμα
                                wantToRead.setTr_UserID(var_uid);               //εισαγωγη στοιχειων του




                                MainActivity.listDatabase.rbDao().insertWantRead(wantToRead);           //εκτελση insert στη βαση


                                Toast.makeText(getActivity(), title+ " added to your Want To Read!", Toast.LENGTH_LONG).show();
                            } catch(Exception e){
                                        String message=e.getMessage();
                                        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                }
                bookDetails.setText(result);
                Picasso.get().load(imgURL).into(cover);











            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(),"query operation failed.",Toast.LENGTH_LONG).show();
            }});

    return view;
    }
}