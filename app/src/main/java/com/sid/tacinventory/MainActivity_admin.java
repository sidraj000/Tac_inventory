package com.sid.tacinventory;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

public class MainActivity_admin extends AppCompatActivity {
    FragmentPagerAdapter mPagerAdapter;
    ViewPager mViewPager;
    TabLayout tabLayout;
    Date currdate=Calendar.getInstance().getTime();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Date time=Calendar.getInstance().getTime();
        Date tempdate=new Date(119,3,28);
    /*  Request request1=new Request("1","Arduino","Siddharth","18065071","siddharthraj.civ18@itbhu.ac.in",time,0,null,1,currdate);
        Request request2=new Request("2","Motors","Siddharth","18065071","siddharthraj.civ18@itbhu.ac.in",time,0,null,1,tempdate);
        Request request3=new Request("3","ir sensors","Siddharth","18065071","siddharthraj.civ18@itbhu.ac.in",time,0,null,1,currdate);
        FirebaseDatabase.getInstance().getReference().child("requests").child("1").setValue(request1);
        FirebaseDatabase.getInstance().getReference().child("requests").child("2").setValue(request2);
        FirebaseDatabase.getInstance().getReference().child("requests").child("3").setValue(request3);
*/
        mViewPager = findViewById(R.id.viewpager1);
        mPagerAdapter =new FragmentPagerAdapter(getSupportFragmentManager()) {
            private final Fragment[] mFragments = new Fragment[] {
                    new frag_admin(),
                    new frag_issuedRequests(),
                    new fragInventory()

            };
            private final String[] mFragmentNames = new String[] {
                    "Current Request","Issued Inventory","Current Inventory"
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tabmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout_menu:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity_admin.this,LoginActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onBackPressed() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity_admin.this);
        builder.setMessage("Confirm to Exit");
        builder.setCancelable(true);
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton("Close!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();



    }
}
