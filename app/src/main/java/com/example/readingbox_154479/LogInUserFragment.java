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
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Filter;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LogInUserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LogInUserFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LogInUserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LogInUserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LogInUserFragment newInstance(String param1, String param2) {
        LogInUserFragment fragment = new LogInUserFragment();
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


    EditText usernameText, passText;
    String username,password, userID;
    Button login_button;
   CollectionReference usersRef;
    OnMessageSendListener messageSendListener;
    public interface OnMessageSendListener{
        public void onMessageSend(String message);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_log_in_user, container, false);

       usernameText= view.findViewById(R.id.editLoginUser);         //συνδεση με στοιχεια του fragment
       passText=view.findViewById(R.id.editLoginPass);



       login_button=view.findViewById(R.id.buttonLogSubmit);
       login_button.setOnClickListener(new View.OnClickListener() {         //λειτουργια αν πατηθει το κουμπι


           @Override
           public void onClick(View v) {

               username=usernameText.getText().toString().trim();                               //παιρνει τις τιμες που εγραψε ο χρηστης
               password=passText.getText().toString();



               usersRef=MainActivity.db.collection("Users");
               Query query=usersRef.where(Filter.equalTo("username",username));

               query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                           @Override
                           public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                               if(queryDocumentSnapshots.size()>0){


                               for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots){

                                   if(documentSnapshot.exists()) {

                                        userID=documentSnapshot.getId();
                                       String dbpsw = documentSnapshot.getString("password");
                                       if(password.equals(dbpsw)){
                                           Toast.makeText(getActivity(),userID+" user  logged in",Toast.LENGTH_LONG).show();
                                           MainActivity.username=username;
                                           MainActivity.global_userID=documentSnapshot.getId();




                                           messageSendListener.onMessageSend(username);
                                   }else Toast.makeText(getActivity(),"user NOT logged in",Toast.LENGTH_LONG).show();
                               } }




                                }else Toast.makeText(getActivity(),"user does NOT exists",Toast.LENGTH_LONG).show();
                           }
                       }).addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {
                       Toast.makeText(getActivity(),"query operation failed.",Toast.LENGTH_LONG).show();
                   }
               });
           }
       });



       return view;
    }
@Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
    Activity activity=(Activity) context;
    try{
        messageSendListener=(OnMessageSendListener) activity;
    }catch (ClassCastException e){
        throw new ClassCastException(activity.toString()+" must implement onMessageSend ");
    }
}

@Override
    public void onResume(){
        super.onResume();
        usernameText.setText("");
        passText.setText("");
}
}