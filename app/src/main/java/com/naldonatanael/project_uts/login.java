package com.naldonatanael.project_uts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {

    private static String CHANNEL_ID = "Channel 1";

    EditText editEmail, editPass;
    Button btnUP, btnIn;
    private FirebaseAuth auth = FirebaseAuth.getInstance();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);

        editEmail = findViewById(R.id.inputEmail);
        editPass = findViewById(R.id.inputPass);
        btnUP = findViewById(R.id.btnSignUp);
        btnIn = findViewById(R.id.btnSignIn);

        btnUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (!editEmail.getText().toString().matches("^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$")) {
                        Toast.makeText(getApplicationContext(), "Invalid Email", Toast.LENGTH_SHORT).show();
                    }else if (editPass.getText().toString().matches("")) {
                        Toast.makeText(getApplicationContext(), "Please Fill Password", Toast.LENGTH_SHORT).show();
                    }else if (editPass.getText().toString().length() < 6) {
                        Toast.makeText(getApplicationContext(), "Password Too Short", Toast.LENGTH_SHORT).show();
                    }else {
                        signUp();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        btnIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (!editEmail.getText().toString().matches("^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$")) {
                        Toast.makeText(getApplicationContext(), "Invalid Email", Toast.LENGTH_SHORT).show();
                    }else if (editPass.getText().toString().matches("")) {
                        Toast.makeText(getApplicationContext(), "Please Fill Password", Toast.LENGTH_SHORT).show();
                    }else if (editPass.getText().toString().length() < 6) {
                        Toast.makeText(getApplicationContext(), "Password Too Short", Toast.LENGTH_SHORT).show();
                    }else {
                        signIn();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

    }

    private void signUp() {
        String email = editEmail.getText().toString();
        String password = editPass.getText().toString();

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Registration Complete", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Registration Failed", Toast.LENGTH_SHORT).show();
                        }

                        editEmail.getText().clear();
                        editPass.getText().clear();
                    }
                });

    }

    private void signIn() {
        String email = editEmail.getText().toString();
        String password = editPass.getText().toString();

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                            createNotificationChannel();
                            addNotificationSignin();
                            Intent i = new Intent(login.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void createNotificationChannel () {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name ="Channel 1";
            String description ="This is Channel 1";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void addNotificationSignin() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, login.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Hello")
                .setContentText("Selamat anda berhasil Sign In!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Intent notificationIntent = new Intent( this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivities(this, 0, new Intent[]{notificationIntent}, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());

    }

}