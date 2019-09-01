package com.sid.tacinventory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

public class LoginActivity extends AppCompatActivity {

    public int PERMISSIONS_REQUEST = 100;
    private FirebaseAuth mAuth;
    public static final int RC_SIGN_IN=1;
    GoogleSignInClient mGoogleSignInClient;
    SignInButton signInButton;
    DatabaseReference mDatabase;
    public int flag;
    public  String uId;
    public ChildEventListener mChildEventListener;
    FirebaseUser currentUser;
    Date currdate=Calendar.getInstance().getTime();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        signInButton = findViewById(R.id.sign_in);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        mDatabase = FirebaseDatabase.getInstance().getReference();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        signInButton.setVisibility(View.GONE);

    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.

        currentUser = mAuth.getCurrentUser();
        if(currentUser!=null)
        {
            Date time= Calendar.getInstance().getTime();
            Date tempdate=new Date(119,3,28);
      Request request1=new Request("1","Arduino","Siddharth","18065071","siddharthraj.civ18@itbhu.ac.in","project",0,1,currdate);
        Request request2=new Request("2","Motors","Siddharth","18065071","siddharthraj.civ18@itbhu.ac.in","project",0,1,tempdate);
        Request request3=new Request("3","ir sensors","Siddharth","18065071","siddharthraj.civ18@itbhu.ac.in","project",0,1,currdate);
        FirebaseDatabase.getInstance().getReference().child("requests").child("1").setValue(request1);
        FirebaseDatabase.getInstance().getReference().child("requests").child("2").setValue(request2);
        FirebaseDatabase.getInstance().getReference().child("requests").child("3").setValue(request3);


            if(currentUser.getEmail().equals("siddharthraj000@gmail.com"))
            startActivity(new Intent(LoginActivity.this, MainActivity_clubs.class));
            else if(currentUser.getEmail().equals("adityarai.civ18@itbhu.ac.in"))
                startActivity(new Intent(LoginActivity.this,MainActivity_admin.class));

        }
        else
        {

            signInButton.setVisibility(View.VISIBLE);
            mGoogleSignInClient.signOut();
        }



    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                //  Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        //Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        final AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in User's information
                            // Log.d(TAG, "signInWithCredential:success");
                            currentUser = mAuth.getCurrentUser();
                            uId=currentUser.getUid();

                            updateUI(currentUser);



                        } else {
                            // If sign in fails, display a message to the User.
                            // Log.w(TAG, "signInWithCredential:failure", task.getException());
                            // Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            currentUser=null;
                            updateUI(currentUser);

                        }



                        // ...
                    }
                });

    }



    private void updateUI(final FirebaseUser user) {
        if(user!=null) {
            writeNewUser(user.getUid(), user.getDisplayName(), user.getEmail(), user.getPhotoUrl().toString());
            if(currentUser.getEmail().equals("siddharthraj000@gmail.com"))
                startActivity(new Intent(LoginActivity.this, MainActivity_clubs.class));
            else if(currentUser.getEmail().equals("adityarai.civ18@itbhu.ac.in"))
                startActivity(new Intent(LoginActivity.this,MainActivity_admin.class));
            else
            {
                Toast.makeText(this, "Authentication Failed...", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                mGoogleSignInClient.signOut();
            }

        }
    }
    private void writeNewUser(String userId, String name, String email,String pid) {
        User user=new User(name,userId,email,pid);
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference();
        ref.child("users").child(userId).setValue(user);
    }
    @Override
    public void onBackPressed() {

            final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
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



