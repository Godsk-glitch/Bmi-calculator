package com.example.frag;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    FragmentTwo fragmentTwo;
    FragmentThree fragmentThree;
    int showingFragment = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentTwo = new FragmentTwo();
        fragmentThree = new FragmentThree();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.clayout,fragmentTwo);
        fragmentTransaction.commit();
        showingFragment = 1;

    }

    public void switchFragment(View view)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(showingFragment == 1)
        {
            fragmentTransaction.replace(R.id.clayout,fragmentTwo);
            showingFragment = 2;
        }
        else if(showingFragment == 2)
        {
            fragmentTransaction.replace(R.id.clayout,fragmentThree);
            showingFragment = 1;
        }
        fragmentTransaction.commit();
    }
}
