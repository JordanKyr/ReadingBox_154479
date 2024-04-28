package com.example.readingbox_154479;

import android.content.Entity;
import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
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
import androidx.room.Room;

import com.example.readingbox_154479.database.RB_DB;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity implements LogInUserFragment.OnMessageSendListener, UserHome.OnBookSendListener, AuthorsFragment.OnAuthorSendListener{


    public static RB_DB listDatabase;
    public static FragmentManager fragmentManager;
    public static FirebaseFirestore db;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    public static String global_userID;
    public static String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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

        listDatabase= Room.databaseBuilder(getApplicationContext(),RB_DB.class,"listDB").allowMainThreadQueries().build(); //δημιουργια και αρχικοποιησης τοπικης βασης

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);    //συνδεση στοιχειων με τα views

        toolbar.setNavigationIcon(R.drawable.toolbar_img);          //clickable εικονα στο toolbar για να ανοιγει το μενου
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.open();
            }
        });



        getSupportActionBar().setTitle("Reading Box");           //παραμετροποιηση toolbar
        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_View);




        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override                                                                             //επιλογες για κλικ στο μενου
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId()==R.id.home){
                    displayMessage("Open Home");

                  onMessageSend(username);

                    drawerLayout.closeDrawers();
                    return true;
                }
                else if(menuItem.getItemId()==R.id.authors){
                    displayMessage("Open Authors");
                    if(findViewById(R.id.fragment_container)!=null){
                        if(savedInstanceState!=null){return false;}
                        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new AuthorsFragment()); //ανοίγει ένα LoginFragment στο layout
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        onMessageSendAuthors();
                    }
                    drawerLayout.closeDrawers();
                    return true;
                }
                else if(menuItem.getItemId()==R.id.lists){
                    displayMessage("Open Want to Read");
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


    public void onMessageSendAuthors() {
       AuthorsFragment authorsFragment=new AuthorsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("username","Jordan");
        authorsFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, authorsFragment, null);
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


    @Override
    public void onAuthorSend(String surname) {
       AuthorDetails authorDetails = new AuthorDetails();  //επικοινωνια με αλλο fragment
        Bundle bundle = new Bundle();               //αποστολη δεδομενων μεσω πακετου bundle
        bundle.putString("lastname", surname);
        authorDetails.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, authorDetails, null);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    void displayMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}