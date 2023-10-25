package es.upm.miw.bantumi;

import android.os.Bundle;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.EditTextPreference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreferenceCompat;

import java.util.Objects;

public class BantumiPrefs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());

            EditTextPreference player1NamePref = findPreference("player1Name");
            EditTextPreference player2NamePref = findPreference("player2Name");
            SwitchPreferenceCompat togglePlayer2Pref = findPreference("togglePlayer2");

            // Handle visibility based on switch preference
            assert togglePlayer2Pref != null;
            togglePlayer2Pref.setOnPreferenceChangeListener((preference, newValue) -> {
                assert player2NamePref != null;
                player2NamePref.setVisible((boolean) newValue);
                return true;
            });

            if (!togglePlayer2Pref.isChecked()) {
                assert player2NamePref != null;
                player2NamePref.setVisible(false);
            }
        }
    }
}