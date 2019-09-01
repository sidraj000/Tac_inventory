package com.sid.tacinventory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class requestDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextView tvName,tvQuant,tvRoll,tvItem,tvStatus;
    Button btnAccept,btnDecline;
    Spinner sp;
    String reqid;
    Integer status;
    List<String> categories = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reqid=getIntent().getStringExtra("details");
        setContentView(R.layout.activity_request_details);
        tvName=findViewById(R.id.tvReqDetName);
        tvRoll=findViewById(R.id.tvReqDetRoll);
        tvItem=findViewById(R.id.tvReqDetItem);
        tvQuant=findViewById(R.id.tvReqDetQuant);
        btnAccept=findViewById(R.id.btnReqAccept);
        btnDecline=findViewById(R.id.btnReqDecline);
        tvStatus=findViewById(R.id.tvStatusNotice);
        sp=findViewById(R.id.spClubs);

        sp.setVisibility(View.GONE);
        btnDecline.setVisibility(View.GONE);
        btnAccept.setVisibility(View.GONE);

        categories.add("1- Robotics Club");
        categories.add("2- Tac Inventory");
        sp.setOnItemSelectedListener(requestDetails.this);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp.setAdapter(dataAdapter);
        sp.getOnItemSelectedListener();
        FirebaseDatabase.getInstance().getReference().child("requests").child(reqid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final Request request=dataSnapshot.getValue(Request.class);
                tvName.setText(request.pName);
                tvItem.setText(request.itemName);
                tvQuant.setText(Integer.toString(request.quantity));
                tvRoll.setText(request.rollNumber);
                if(request.status==0) {
                    tvStatus.setText("New request");
                    tvStatus.setTextColor(getResources().getColor(R.color.blue));

                    sp.setVisibility(View.VISIBLE);
                    btnDecline.setVisibility(View.VISIBLE);
                    btnAccept.setVisibility(View.VISIBLE);

                    btnAccept.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(status==2)
                            {
                                request.status=11;
                                FirebaseDatabase.getInstance().getReference().child("requests").child(reqid).setValue(request);
                                Toast.makeText(requestDetails.this, "Request Accepeted Successfully", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                request.status = status;
                                FirebaseDatabase.getInstance().getReference().child("requests").child(reqid).setValue(request);
                                Toast.makeText(requestDetails.this, "Request Accepeted Successfully", Toast.LENGTH_SHORT).show();
                                btnAccept.setVisibility(View.GONE);
                                btnDecline.setVisibility(View.GONE);
                            }
                        }
                    });
                    btnDecline.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            request.status = 10;
                            FirebaseDatabase.getInstance().getReference().child("requests").child(reqid).setValue(request);
                            Toast.makeText(requestDetails.this, "Request Declined Successfully", Toast.LENGTH_SHORT).show();
                            btnAccept.setVisibility(View.GONE);
                            btnDecline.setVisibility(View.GONE);
                        }
                    });
                }
                else if(request.status==6) {

                    tvStatus.setTextColor(getResources().getColor(R.color.red));
                    tvStatus.setText("Declined by Inventory Manager");
                    sp.setVisibility(View.GONE);
                    btnDecline.setVisibility(View.GONE);
                    btnAccept.setVisibility(View.GONE);

                }
                else if(request.status>0&&request.status<6)
                {

                    tvStatus.setTextColor(getResources().getColor(R.color.yellow));
                    tvStatus.setText("Processing");
                    sp.setVisibility(View.GONE);
                    btnDecline.setVisibility(View.GONE);
                    btnAccept.setVisibility(View.GONE);
                }
                else if(request.status==10)
                {

                    tvStatus.setTextColor(getResources().getColor(R.color.red));
                    tvStatus.setText("Declined");
                    btnDecline.setVisibility(View.GONE);
                    btnAccept.setVisibility(View.GONE);
                }

                else if(request.status==12)
                {

                    sp.setVisibility(View.GONE);

                    tvStatus.setTextColor(getResources().getColor(R.color.red));
                    tvStatus.setText("Cancelled by Inventory Manager");
                    sp.setVisibility(View.GONE);
                }
                else if(request.status==11)
                {


                    tvStatus.setTextColor(getResources().getColor(R.color.green));
                    tvStatus.setText("Approved");
                    sp.setVisibility(View.GONE);
                    btnDecline.setVisibility(View.VISIBLE);
                    btnAccept.setVisibility(View.VISIBLE);
                    btnAccept.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            request.status =13;
                            Date endDate=request.date ;
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(endDate);
                            cal.add(Calendar.MONTH,1);
                            request.date=cal.getTime();
                            FirebaseDatabase.getInstance().getReference().child("requests").child(reqid).setValue(request);
                            Toast.makeText(requestDetails.this, "Request Accepeted Successfully", Toast.LENGTH_SHORT).show();
                            btnAccept.setVisibility(View.GONE);
                            btnDecline.setVisibility(View.GONE);
                        }
                    });
                    btnDecline.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            request.status = 12;
                            FirebaseDatabase.getInstance().getReference().child("requests").child(reqid).setValue(request);
                            Toast.makeText(requestDetails.this, "Request Declined Successfully", Toast.LENGTH_SHORT).show();
                            btnAccept.setVisibility(View.GONE);
                            btnDecline.setVisibility(View.GONE);
                        }
                    });


                }
                else if(request.status==13)
                {

                    tvStatus.setText("Issued");
                    tvStatus.setTextColor(getResources().getColor(R.color.green));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        status= Character.getNumericValue(categories.get(position).charAt(0));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
