package es.upm.miw.bantumi;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialog;
import androidx.fragment.app.DialogFragment;

public class FinalAlertDialog extends DialogFragment {

    @NonNull
    @Override
	public AppCompatDialog onCreateDialog(Bundle savedInstanceState) {
		final MainActivity main = (MainActivity) requireActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(main);
        builder
                .setTitle(R.string.txtDialogoFinalTitulo)
                .setMessage(R.string.txtDialogoFinalPregunta)
                .setPositiveButton(
                        getString(android.R.string.ok),
                        (dialog, which) -> main.juegoBantumi.inicializar(JuegoBantumi.Turno.turnoJ1)
                )
                .setNegativeButton(
                        getString(android.R.string.cancel),
                        (dialog, which) -> main.finish()
                );

		return builder.create();
	}
}
