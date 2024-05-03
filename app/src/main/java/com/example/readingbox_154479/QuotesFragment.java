package com.example.readingbox_154479;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.readingbox_154479.adapters.ListBookSpinner;
import com.example.readingbox_154479.database.ListBook;
import com.example.readingbox_154479.database.Quotes;
import com.example.readingbox_154479.database.Saved_Quotes;
import com.google.android.gms.tasks.OnFailureListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuotesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuotesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public QuotesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuotesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuotesFragment newInstance(String param1, String param2) {
        QuotesFragment fragment = new QuotesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    Spinner bookSpinner;
    TextView welcome;
    ListBookSpinner listBookSpinner;
    Button addQuote, goToSaved;

    EditText quote_Content;

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
        View view= inflater.inflate(R.layout.fragment_quotes, container, false);


        welcome=view.findViewById(R.id.TextUserWelcome);
        welcome.setText("Welcome "+MainActivity.username);

        quote_Content=view.findViewById(R.id.textContentQ);

        ArrayList<ListBook> toSpinner=(ArrayList<ListBook>) MainActivity.listDatabase.rbDao().getAllBooks(MainActivity.global_userID);
        bookSpinner=(Spinner)view.findViewById(R.id.spinnerBooks);

        listBookSpinner=new ListBookSpinner(getContext(),R.layout.custom_spinner,toSpinner);    //emfanisi sto spinenr
        bookSpinner.setAdapter(listBookSpinner);

        goToSaved=view.findViewById(R.id.btnGoToSaved);

        addQuote=view.findViewById(R.id.buttonAddQuote);                    //eisagogi quote stin topiki vasi
        addQuote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String var_uid=MainActivity.global_userID;

                ListBook listBook=(ListBook)bookSpinner.getSelectedItem();          //pairno to epilegmeno antikeimeno kai ta dedomena toy

                String var_isbn= listBook.getListISBN();
                Quotes quotes=null;
                int generatedQID=-1;

                String saveQText=quote_Content.getText().toString().trim();
                if(!TextUtils.isEmpty(saveQText)){


                try{

                   quotes=new Quotes(saveQText);

                    MainActivity.listDatabase.rbDao().insertQuote(quotes);

                    generatedQID=MainActivity.listDatabase.rbDao().getQID(MainActivity.global_userID,var_isbn,saveQText);           //pairno to Q ID


                }catch(Exception e){
                    String message=e.getMessage();
                    Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();

            }
                try{
                    Saved_Quotes savedQuotes=new Saved_Quotes();
                    savedQuotes.setQuotesISBN(listBook.getListISBN());
                    savedQuotes.setQuotesQID(generatedQID);
                    savedQuotes.setQuotesUID(var_uid);

                    MainActivity.listDatabase.rbDao().insertSavedQuote(savedQuotes);
                }catch(Exception e){
                    String message=e.getMessage();
                    Toast.makeText(getActivity(),"Already Saved" +message,Toast.LENGTH_SHORT).show();

            }

                    quote_Content.setText("");
                    Toast.makeText(getActivity(), "Quote Saved!", Toast.LENGTH_LONG).show();
        }else Toast.makeText(getActivity(), "Type a Quote!", Toast.LENGTH_LONG).show();
            }});


        goToSaved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container,new SavedQuotesFragment()).addToBackStack(null).commit();
            }
        });






        return view;
    }
}