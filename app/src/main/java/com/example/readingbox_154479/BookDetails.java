package com.example.readingbox_154479;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.readingbox_154479.adapters.AuthorSearch_Adapter;
import com.example.readingbox_154479.database.ListBook;
import com.example.readingbox_154479.database.ListUser;
import com.example.readingbox_154479.database.ShelfBooks;
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
 Button addWant, addShelf, goAuthor;
    String  isbn="";
    String title="";
    String author="";
    Integer pubyear=0;
    String imgURL="";
    CollectionReference collectionReference;
    String firstName, lastName, search, authorID;
AuthorsFragment.OnAuthorSendListener authorSendListener;

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
        addShelf=view.findViewById(R.id.btnShelf);
        goAuthor=view.findViewById(R.id.buttonToAuthorDet);


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
                    imgURL="";

                for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                    Books books=documentSnapshot.toObject(Books.class);
                    author=books.getAuthor();
                    pubyear=books.getPubYear();
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

                    String checkShelf="";
                    checkShelf+=MainActivity.listDatabase.rbDao().checkShelf(isbn, MainActivity.global_userID);
                        if(checkShelf.equals(isbn)) {                                              //elegxos an iparxei idi stis listes
                        addShelf.setEnabled(false);               //apenergopoio ta klik an to exo valei tora
                        addShelf.setText("Already in 'Shelf'");}

                    String checktoRead="";
                    checktoRead+=MainActivity.listDatabase.rbDao().checktoRead(isbn,MainActivity.global_userID);
                    if(checktoRead.equals(isbn)) {                                              //elegxos an iparxei idi stis listes
                        addWant.setEnabled(false);               //apenergopoio ta klik an to exo valei tora
                        addWant.setText("Already in 'Want to Read'");}

                    addWant.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String var_uid=MainActivity.global_userID;
                            String var_bid=isbn;
                            String var_author=author;
                            String var_title=title;
                            Integer var_pubyear=pubyear;
                            String var_cover=imgURL;

                            try {



                                ListBook listBook=new ListBook();
                                listBook.setListISBN(var_bid);
                                listBook.setListAuthor(var_author);
                                listBook.setListTitle(var_title);
                                listBook.setPubYear(var_pubyear);
                                listBook.setListCover(var_cover);
                                MainActivity.listDatabase.rbDao().upsertBook(listBook);



                                WantToRead wantToRead = new WantToRead();
                                wantToRead.setTr_ISBN(var_bid);                 //δημιουργια αντικειμενου λιστας για διαβασμα
                                wantToRead.setTr_UserID(var_uid);               //εισαγωγη στοιχειων του




                                MainActivity.listDatabase.rbDao().insertWantRead(wantToRead);           //εκτελση insert στη βαση


                                Toast.makeText(getActivity(), title+ " added to your 'Want To Read'!", Toast.LENGTH_LONG).show();
                                addWant.setEnabled(false);                                                 //apenergopoio ta klik
                                addWant.setText("Added to 'Want to Read'");
                            } catch(Exception e){
                                        String message=e.getMessage();
                                        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
                            }
                        }
                    });



                    addShelf.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String var_uid=MainActivity.global_userID;
                            String var_bid=isbn;
                            String var_author=author;
                            String var_title=title;
                            Integer var_pubyear=pubyear;
                            String var_cover=imgURL;

                            try {



                                ListBook listBook=new ListBook();
                                listBook.setListISBN(var_bid);
                                listBook.setListAuthor(var_author);
                                listBook.setListTitle(var_title);
                                listBook.setPubYear(var_pubyear);
                                listBook.setListCover(var_cover);
                                MainActivity.listDatabase.rbDao().upsertBook(listBook);



                                ShelfBooks shelfBooks = new ShelfBooks();
                                shelfBooks.setShelf_ISBN(var_bid);                 //δημιουργια αντικειμενου λιστας που εχουν διαβαστει
                                shelfBooks.setShelf_UserID(var_uid);               //εισαγωγη στοιχειων του




                                MainActivity.listDatabase.rbDao().insertShef(shelfBooks);           //εκτελση insert στη βαση


                                Toast.makeText(getActivity(), title+ " added to your Shelf!", Toast.LENGTH_LONG).show();

                                addShelf.setEnabled(false);               //apenergopoio ta klik
                                addShelf.setText("Added to Shelf");

                            } catch(Exception e){
                                String message=e.getMessage();
                                Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    Toast.makeText(getActivity(),author+" ",Toast.LENGTH_LONG).show();
                    goAuthor.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String[]   splitSearch = author.split(" ");
                            String firstname="";
                            String lastname="";
                                firstname+=splitSearch[0];
                                lastname+=splitSearch[1];

                                                                                    //kalo thn methodo authorSendListener apo tin Authors Fragment gia na pao ston author
                            authorSendListener.onAuthorSend(lastname,firstname);

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

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        Activity activity=(Activity) context;
        try{
            authorSendListener=(AuthorsFragment.OnAuthorSendListener) activity;
        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString()+" must implement onAuthorSend ");
        }
    }

}
