package es.upm.miw.bantumi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.SharedPreferences;

public class BantumiPrefs extends AppCompatActivity {

    private EditText etPlayer1Name, etPlayer2Name;
    private SwitchCompat switchTogglePlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bantumi_prefs);

        etPlayer1Name = findViewById(R.id.etPlayer1Name);
        etPlayer2Name = findViewById(R.id.etPlayer2Name);
        switchTogglePlayer2 = findViewById(R.id.swPlayer2);
        Button btnSaveSettings = findViewById(R.id.btnSaveSettings);

        /**
         * Load player names from SharedPreferences
         * If there is no player name saved, set nothing
         */
        SharedPreferences sharedPreferences = getSharedPreferences("playerNames", MODE_PRIVATE);
        String player1Name = sharedPreferences.getString("player1Name", "");
        String player2Name = sharedPreferences.getString("player2Name", "");
        if (player2Name.equals(getString(R.string.txtPlayer2))) {
            switchTogglePlayer2.setChecked(false);
            etPlayer2Name.setVisibility(View.GONE);
        } else {
            switchTogglePlayer2.setChecked(true);
            etPlayer2Name.setVisibility(View.VISIBLE);
        }

        etPlayer1Name.setText(player1Name);
        if (!player2Name.equals(getString(R.string.txtPlayer2))) {
            etPlayer2Name.setText(player2Name);
        }

        switchTogglePlayer2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                etPlayer2Name.setVisibility(View.VISIBLE);
            } else {
                etPlayer2Name.setVisibility(View.GONE);
            }
        });

        btnSaveSettings.setOnClickListener(view -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString("player1Name", etPlayer1Name.getText().toString());

            if (switchTogglePlayer2.isChecked()) {
                editor.putString("player2Name", etPlayer2Name.getText().toString());
            } else {
                editor.putString("player2Name", getString(R.string.txtPlayer2));
            }

            editor.apply();
            finish();
        });
    }
}