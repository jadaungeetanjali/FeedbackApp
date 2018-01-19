package com.silive.pc.feedbackapp;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FrameLayout fragmentContainer = (FrameLayout) findViewById(R.id.main_fragment_container);

        Bundle studentBundle = new Bundle();
        studentBundle.putString("type", "students");
        final FeedbackFragment studentFragment = new FeedbackFragment();
        studentFragment.setArguments(studentBundle);

        Bundle delegatesBundle = new Bundle();
        delegatesBundle.putString("type", "delegates");
        final FeedbackFragment delegatesFragment = new FeedbackFragment();
        delegatesFragment.setArguments(delegatesBundle);

        Bundle visitorsBundle = new Bundle();
        visitorsBundle.putString("type", "visitors");
        final FeedbackFragment visitorsFragment = new FeedbackFragment();
        visitorsFragment.setArguments(visitorsBundle);


        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.main_tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Students"));
        tabLayout.addTab(tabLayout.newTab().setText("International Delegates"));
        tabLayout.addTab(tabLayout.newTab().setText("Visitors"));


        fragmentTransition(studentFragment);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    fragmentTransition(studentFragment);

                } else if (tab.getPosition() == 1) {

                    fragmentTransition(delegatesFragment);
                } else {

                    fragmentTransition(visitorsFragment);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    private void fragmentTransition(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, fragment).commit();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
