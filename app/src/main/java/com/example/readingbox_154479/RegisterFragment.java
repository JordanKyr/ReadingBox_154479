package com.example.readingbox_154479;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.readingbox_154479.database.ListUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.Filter;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {




    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
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
    CollectionReference usersRef;
    EditText usernameText, passText, uidText;
    String reg_username,reg_password,reg_id;
    Button reg_button;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_register, container, false);

        usernameText= view.findViewById(R.id.editRegUser);         //συνδεση με στοιχεια του fragment
        passText=view.findViewById(R.id.editRegPass);
        reg_button=view.findViewById(R.id.buttonRegSubmit);
        uidText=view.findViewById(R.id.editRegID);

        reg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reg_username=usernameText.getText().toString().trim();                               //παιρνει τις τιμες που εγραψε ο χρηστης
                reg_password=passText.getText().toString().trim();
                reg_id=uidText.getText().toString().trim();
                                                                            //elegxos an ola ta pedia exoyn simplirothei
                if(!TextUtils.isEmpty(reg_username) && !TextUtils.isEmpty(reg_password) && !TextUtils.isEmpty(reg_id) ) {

                    try {
                        Users users = new Users();                    //φτιαχνει αντικειμενο για τη βαση δεδομενων
                        users.setUsername(reg_username);
                        users.setPassword(reg_password);                //εισαγωγη αντικειμενου στη βαση

                        usersRef = MainActivity.db.collection("Users");
                        Query query = usersRef;

                        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                //elegxos an iparxei idi o xristis me to ID
                                if (queryDocumentSnapshots.size() >= 0) {
                                    int checkID_Flag = 0;    //metavliti gia elegxo an meinei 0 den iparxei to ID, an ginei 1 iparxei
                                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                                        if (documentSnapshot.exists()) {
                                            String checkId = documentSnapshot.getId();
                                            if (checkId.equals(reg_id)) checkID_Flag += 1;
                                        }
                                    }
                                    if (checkID_Flag == 0) {  //an einai 0 den iparxei idio ID kai kano thn eisagogi                   //eisagogi sto firestore
                                        MainActivity.db.collection("Users").document(reg_id).set(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(getActivity(), "User Registered", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getActivity(), "Registration failed.", Toast.LENGTH_LONG).show();
                                            }
                                        });
                                        ListUser listUser = new ListUser();
                                        listUser.setListUserID(reg_id);                                //eisagogi stin topii
                                        listUser.setListUsername(reg_username);
                                        listUser.setListPassword(reg_password);
                                        MainActivity.listDatabase.rbDao().upsertUser(listUser);

                                    } else
                                        Toast.makeText(getActivity(), "user ID already in use, try another", Toast.LENGTH_LONG).show();


                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), "query operation failed.", Toast.LENGTH_LONG).show();
                            }
                        });


                    } catch (Exception e) {
                        String message = e.getMessage();
                        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                    }
                }else Toast.makeText(getActivity(), "All the Fields Are Required", Toast.LENGTH_LONG).show();

                usernameText.setText("");  //αδειασμα πεδιων
                passText.setText("");
                uidText.setText("");
            }
        });
return view;
    }
}