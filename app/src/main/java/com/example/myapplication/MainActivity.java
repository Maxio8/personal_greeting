package com.example.myapplication;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "powitanie_channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editTextImie = findViewById(R.id.editTextImie);
        Button buttonPowitanie = findViewById(R.id.buttonPowitanie);

        buttonPowitanie.setOnClickListener(view -> {
            String imie = editTextImie.getText().toString().trim();

            if (imie.isEmpty()) {
                showEmptyNameDialog();
            } else {
                showConfirmationDialog(imie);
            }
        });
    }

    private void showEmptyNameDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Błąd")
                .setMessage("Proszę wpisać swoje imię!")
                .setPositiveButton("OK", null)
                .show();
    }

    private void showConfirmationDialog(String name) {
        new AlertDialog.Builder(this)
                .setTitle("Potwierdzenie")
                .setMessage("Cześć " + name + "! Czy chcesz otrzymać powiadomienie powitalne?")
                .setPositiveButton("Tak, poproszę", (dialog, which) -> {
                    showNotification(name);
                    Toast.makeText(this, "Powiadomienie zostało wysłane!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Nie, dziękuję", (dialog, which) -> {
                    Toast.makeText(this, "Rozumiem. Nie wysyłam powiadomienia.", Toast.LENGTH_SHORT).show();
                })
                .show();
    }

    private void showNotification(String name) {
        createNotificationChannel();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher) // użyj ikony launchera
                .setContentTitle("Witaj!")
                .setContentText("Miło Cię widzieć, " + name + "!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Kanał Powitań";
            String description = "Kanał do wyświetlania powitań";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
