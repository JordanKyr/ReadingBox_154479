package com.example.readingbox_154479;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment implements View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
Button login, register;
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
        View view= inflater.inflate(R.layout.fragment_login, container, false); //εμφάνιση του fragment

        login=view.findViewById(R.id.buttonLogin); //συνδέω τις μεταβλητές με τα κουμπια του fragment
        register=view.findViewById(R.id.buttonRegister);

        login.setOnClickListener(this);  //ενεργοποιώ listeners για το πάτημα του κάθε κουμπιού
        register.setOnClickListener(this);



        return view;
    }
    @Override                                     //εμφάνιση του κατάλληλου fragment μετά το click και add to Backstack
    public void onClick(View v){
        if(v.getId()==R.id.buttonLogin)
            MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container,new LogInUserFragment()).addToBackStack(null).commit();
        else if(v.getId()==R.id.buttonRegister)
            MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container,new RegisterFragment()).addToBackStack(null).commit();


}

}