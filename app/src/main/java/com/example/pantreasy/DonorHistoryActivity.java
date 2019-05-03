package com.example.pantreasy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class DonorHistoryActivity extends AppCompatActivity {

    private ImageButton mHomeButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.donor_history_view);
        mHomeButton = findViewById(R.id.home_button);
        setOnClickForHomeButton();

    }

    private void setOnClickForHomeButton() {
        mHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goHome = new Intent(DonorHistoryActivity.this, DonorHomeActivity.class);
                DonorHistoryActivity.this.startActivity(goHome);
            }
        });
    }
}
