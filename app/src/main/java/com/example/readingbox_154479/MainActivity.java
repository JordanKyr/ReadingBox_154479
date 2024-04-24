package com.example.readingbox_154479;

import android.content.Entity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity implements LogInUserFragment.OnMessageSendListener, UserHome.OnBookSendListener{


    public static FragmentManager fragmentManager;
    public static FirebaseFirestore db;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;



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

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);    //συνδεση στοιχειων με τα views
        getSupportActionBar().setIcon(R.drawable.toolbar_img);

        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_View);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override                                                                             //επιλογες για κλικ στο μενου
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId()==R.id.home){
                    displayMessage("Open Home");
                    drawerLayout.closeDrawers();
                    return true;
                }
                else if(menuItem.getItemId()==R.id.authors){
                    displayMessage("Open Authors");
                    drawerLayout.closeDrawers();
                    return true;
                }
                else if(menuItem.getItemId()==R.id.lists){
                    displayMessage("Open Lists");
                    drawerLayout.closeDrawers();
                    return true;
                }else return false;
            }
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
        BookDetails bookDetails = new BookDetails();  //επικοινωνια με αλλο fragment
        Bundle bundle = new Bundle();               //αποστολη δεδομενων μεσω πακετου bundle
        bundle.putString("title", title);
        bookDetails.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, bookDetails, null);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    void displayMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}