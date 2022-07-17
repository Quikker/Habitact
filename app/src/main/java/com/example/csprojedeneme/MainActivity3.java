package com.example.csprojedeneme;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity3 extends AppCompatActivity{
    private EditText editTextUsername2;
    private EditText editTextPassword2;
    private EditText editTextPassword3;

    private Button btnSignup2;
    private boolean isDuplicateUser = false;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference usersRef = db.collection("users");

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        editTextUsername2 = (EditText)findViewById(R.id.editTextUsername2);
        editTextPassword2 = (EditText)findViewById(R.id.editTextPassword2);
        editTextPassword3 = (EditText)findViewById(R.id.editTextPassword3);

        btnSignup2 = (Button)findViewById(R.id.btnSignup2);


    }
    public void signup2Click(View v){
        String typedUsername = editTextUsername2.getText().toString();
        String firstPassword = editTextPassword2.getText().toString();
        String secondPassword = editTextPassword3.getText().toString();

        usersRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                isDuplicateUser = false;
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    User user = documentSnapshot.toObject(User.class);
                    String username = user.getUsername();
                    if(typedUsername.equals(username)){
                        isDuplicateUser = true;
                    }
                }
                if(!isDuplicateUser){
                    if(firstPassword.equals(secondPassword)){
                        User user = new User(typedUsername, firstPassword, 0);
                        usersRef.add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(MainActivity3.this,"user added database",
                                        Toast.LENGTH_SHORT).show();
                                setContentView(R.layout.activity_logged);
                            }
                        });

                    }
                    else {
                        Toast.makeText(MainActivity3.this, "passwords do not match",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(MainActivity3.this,"this username is taken",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
