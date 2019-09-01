package com.sid.tacinventory;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class frag_declined extends Fragment {

    public RecyclerView mRecycler;
    LinearLayoutManager mManager;
    public requestAdapter mAdapter;


    public frag_declined() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_frag2, container, false);
        mRecycler=view.findViewById(R.id.requestRecycler2);
        mManager=new LinearLayoutManager(getContext());
        mRecycler.setLayoutManager(mManager);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mAdapter = new requestAdapter(getContext());
        mRecycler.setAdapter(mAdapter);

    }

    public static class requestViewHolder extends RecyclerView.ViewHolder{
        TextView tvReq,tvName;

        public requestViewHolder(@NonNull View itemView) {
            super(itemView);
            tvReq=itemView.findViewById(R.id.tvReqName);
            tvName=itemView.findViewById(R.id.tvReqDetails);
        }
    }
    public class requestAdapter extends RecyclerView.Adapter<requestViewHolder>
    {
        public Context mContext;
        public List<Request> mReq=new ArrayList<>();
        public List<String> mIds=new ArrayList<>();
        public requestAdapter(final Context context) {
            mContext=context;
            FirebaseDatabase.getInstance().getReference().child("requests").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    if(dataSnapshot.exists())
                    {
                        Request req=dataSnapshot.getValue(Request.class);
                        if(req.status==10) {
                            mReq.add(req);
                            mIds.add(dataSnapshot.getKey());
                            notifyItemInserted(mReq.size() - 1);
                        }
                    }
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    String userKey = dataSnapshot.getKey();
                    int userIndex = mIds.indexOf(userKey);
                    Request req=dataSnapshot.getValue(Request.class);


                    // [START_EXCLUDE]

                    if (userIndex > -1) {
                        if(req.status!=10)
                        {
                            mReq.remove(userIndex);
                            mIds.remove(userIndex);
                            notifyItemRemoved(userIndex);
                        }
                        else {
                            if(req.status==10) {
                                mReq.set(userIndex, req);
                                notifyItemChanged(userIndex);
                            }
                        }
                    } else {
                        if(req.status==10) {
                            mReq.add(req);

                            mIds.add(dataSnapshot.getKey());
                            notifyItemInserted(mReq.size() - 1);
                        }
                    }


                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                    String userKey = dataSnapshot.getKey();
                    int userIndex = mIds.indexOf(userKey);
                    if(userIndex>-1)
                    {
                        mReq.remove(userIndex);
                        mIds.remove(userIndex);
                        notifyItemRemoved(userIndex);
                    }
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        @NonNull
        @Override
        public requestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = null;
            LayoutInflater inflater = LayoutInflater.from(mContext);
            view = inflater.inflate(R.layout.item_request, parent, false);

            return new requestViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull requestViewHolder holder, final int i) {
            holder.tvReq.setText(mReq.get(i).itemName);
            holder.tvName.setText(mReq.get(i).pName+"{"+mReq.get(i).rollNumber+"}");

        }

        @Override
        public int getItemCount() {
            return mReq.size();
        }
    }

}
