package com.sid.tacinventory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class requestDetails extends AppCompatActivity {
    TextView tvName,tvQuant,tvRoll,tvItem;
    Button btnAccept,btnDecline;
    String reqid;
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
        FirebaseDatabase.getInstance().getReference().child("requests").child(reqid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final Request request=dataSnapshot.getValue(Request.class);
                tvName.setText(request.pName);
                tvItem.setText(request.itemName);
                tvQuant.setText(Integer.toString(request.quantity));
                tvRoll.setText(request.rollNumber);
                btnAccept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        request.status=1;
                        FirebaseDatabase.getInstance().getReference().child("requests").child(reqid).setValue(request);
                        Toast.makeText(requestDetails.this, "Request Accepeted Successfully", Toast.LENGTH_SHORT).show();
                        btnAccept.setVisibility(View.GONE);
                        btnDecline.setVisibility(View.GONE);
                    }
                });
                btnDecline.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        request.status=2;
                        FirebaseDatabase.getInstance().getReference().child("requests").child(reqid).setValue(request);
                        Toast.makeText(requestDetails.this, "Request Declined Successfully", Toast.LENGTH_SHORT).show();
                        btnAccept.setVisibility(View.GONE);
                        btnDecline.setVisibility(View.GONE);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
