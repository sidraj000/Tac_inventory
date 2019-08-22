package com.sid.tacinventory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TableLayout;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    FragmentPagerAdapter mPagerAdapter;
    ViewPager mViewPager;
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Date time=Calendar.getInstance().getTime();
        Request request1=new Request("1","Arduino","Siddharth","18065071",time,0,1);
        Request request2=new Request("2","Motors","Siddharth","18065071",time,0,1);
        Request request3=new Request("2","ir sensors","Siddharth","18065071",time,0,1);


        FirebaseDatabase.getInstance().getReference().child("requests").child("1").setValue(request1);
        FirebaseDatabase.getInstance().getReference().child("requests").child("2").setValue(request2);
        FirebaseDatabase.getInstance().getReference().child("requests").child("3").setValue(request3);
        mViewPager = findViewById(R.id.viewpager1);

        mPagerAdapter =new FragmentPagerAdapter(getSupportFragmentManager()) {
            private final Fragment[] mFragments = new Fragment[] {
                    new frg1(),
                    new frag2()

            };
            private final String[] mFragmentNames = new String[] {
                    "Frag1","Frag2"
            };
            @Override
            public Fragment getItem(int position) {

                    return mFragments[position];

            }

            @Override
            public int getCount() {
                return mFragments.length;
            }
            @Override
            public CharSequence getPageTitle(int position) {
                return mFragmentNames[position];
            }
        };
        mViewPager.setAdapter(mPagerAdapter);
        tabLayout = findViewById(R.id.tabs1);
        tabLayout.setupWithViewPager(mViewPager);
    }
}
