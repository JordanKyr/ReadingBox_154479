package com.example.readingbox_154479;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity implements LogInUserFragment.OnMessageSendListener, UserHome.OnBookSendListener{


    public static FragmentManager fragmentManager;
    public static FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = FirebaseFirestore.getInstance();
        fragmentManager=getSupportFragmentManager();

        if(findViewById(R.id.fragment_container)!=null){
            if(savedInstanceState!=null){return;}
            fragmentManager.beginTransaction().add(R.id.fragment_container,new LoginFragment()).commit(); //ανοίγει ένα LoginFragment στο layout
            }
    }


    @Override
public void onMessageSend(String message) {
        UserHome userHome = new UserHome();
        Bundle bundle = new Bundle();
        bundle.putString("username", message);
        userHome.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, userHome, null);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

@Override
    public void onBookSend(String title) {
        BookDetails bookDetails = new BookDetails();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bookDetails.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, bookDetails, null);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
}