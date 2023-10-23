package es.upm.miw.bantumi;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialog;
import androidx.fragment.app.DialogFragment;

public class ResetAlertDialog extends DialogFragment {

    @NonNull
    @Override
    public AppCompatDialog onCreateDialog(Bundle savedInstanceState) {
        final MainActivity main = (MainActivity) requireActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(main);
        builder
                .setTitle(R.string.txtDialogoResetTitulo)
                .setMessage(R.string.txtDialogoResetPregunta)
                .setPositiveButton(
                        getString(R.string.yes),
                        (dialog, which) -> main.juegoBantumi.inicializar(JuegoBantumi.Turno.turnoJ1)
                )
                .setNegativeButton(
                        getString(R.string.no),
                        (dialog, which) -> {
                        }
                );

        return builder.create();
    }
}