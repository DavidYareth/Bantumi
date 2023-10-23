package es.upm.miw.bantumi;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.Locale;

import es.upm.miw.bantumi.model.BantumiViewModel;

public class MainActivity extends AppCompatActivity {

    protected final String LOG_TAG = "MiW";
    JuegoBantumi juegoBantumi;
    BantumiViewModel bantumiVM;
    int numInicialSemillas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instancia el ViewModel y el juego, y asigna observadores a los huecos
        numInicialSemillas = getResources().getInteger(R.integer.intNumInicialSemillas);
        bantumiVM = new ViewModelProvider(this).get(BantumiViewModel.class);
        juegoBantumi = new JuegoBantumi(bantumiVM, JuegoBantumi.Turno.turnoJ1, numInicialSemillas);
        crearObservadores();
    }

    /**
     * Crea y subscribe los observadores asignados a las posiciones del tablero.
     * Si se modifica el contenido del tablero -> se actualiza la vista.
     */
    private void crearObservadores() {
        for (int i = 0; i < JuegoBantumi.NUM_POSICIONES; i++) {
            int finalI = i;
            bantumiVM.getNumSemillas(i).observe(    // Huecos y almacenes
                    this,
                    integer -> mostrarValor(finalI, juegoBantumi.getSemillas(finalI)));
        }
        bantumiVM.getTurno().observe(   // Turno
                this,
                turno -> marcarTurno(juegoBantumi.turnoActual())
        );
    }

    /**
     * Indica el turno actual cambiando el color del texto
     *
     * @param turnoActual turno actual
     */
    private void marcarTurno(@NonNull JuegoBantumi.Turno turnoActual) {
        TextView tvJugador1 = findViewById(R.id.tvPlayer1);
        TextView tvJugador2 = findViewById(R.id.tvPlayer2);
        switch (turnoActual) {
            case turnoJ1:
                tvJugador1.setTextColor(getColor(R.color.white));
                tvJugador1.setBackgroundColor(getColor(android.R.color.holo_blue_light));
                tvJugador2.setTextColor(getColor(R.color.black));
                tvJugador2.setBackgroundColor(getColor(R.color.white));
                break;
            case turnoJ2:
                tvJugador1.setTextColor(getColor(R.color.black));
                tvJugador1.setBackgroundColor(getColor(R.color.white));
                tvJugador2.setTextColor(getColor(R.color.white));
                tvJugador2.setBackgroundColor(getColor(android.R.color.holo_blue_light));
                break;
            default:
                tvJugador1.setTextColor(getColor(R.color.black));
                tvJugador2.setTextColor(getColor(R.color.black));
        }
    }

    /**
     * Muestra el valor <i>valor</i> en la posición <i>pos</i>
     *
     * @param pos posición a actualizar
     * @param valor valor a mostrar
     */
    private void mostrarValor(int pos, int valor) {
        String num2digitos = String.format(Locale.getDefault(), "%02d", pos);
        // Los identificadores de los huecos tienen el formato casilla_XX
        int idBoton = getResources().getIdentifier("casilla_" + num2digitos, "id", getPackageName());
        if (0 != idBoton) {
            TextView viewHueco = findViewById(idBoton);
            viewHueco.setText(String.valueOf(valor));
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.opciones_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.opcAjustes: // @todo Preferencias
//                startActivity(new Intent(this, BantumiPrefs.class));
//                return true;
            case R.id.opcAcercaDe:
                new AlertDialog.Builder(this)
                        .setTitle(R.string.aboutTitle)
                        .setMessage(R.string.aboutMessage)
                        .setPositiveButton(android.R.string.ok, null)
                        .show();
                return true;

            case R.id.opcReiniciarPartida:
                new ResetAlertDialog().show(getSupportFragmentManager(), "ALERT_DIALOG");
                return true;

            case R.id.opcGuardarPartida:
                new AlertDialog.Builder(this)
                        .setTitle(R.string.saveTitle)
                        .setMessage(R.string.saveMessage)
                        .setPositiveButton(R.string.yes, (dialog, which) -> guardarPartida())
                        .setNegativeButton(R.string.no, null)
                        .show();
                return true;

            case R.id.opcRecuperarPartida:
                if (existeJuegoGuardado()) {
                    if (!juegoBantumi.juegoIniciado() || juegoBantumi.juegoTerminado()) {
                        cargarPartida();
                    } else {
                        new AlertDialog.Builder(this)
                                .setTitle(R.string.loadTitle)
                                .setMessage(R.string.loadMessage)
                                .setPositiveButton(R.string.yes, (dialog, which) -> cargarPartida())
                                .setNegativeButton(R.string.no, null)
                                .show();
                    }
                } else {
                    Snackbar.make(
                            findViewById(android.R.id.content),
                            (R.string.noSavedGame),
                            Snackbar.LENGTH_LONG
                    ).show();
                }
                return true;

            // @TODO!!! resto opciones

            default:
                Snackbar.make(
                        findViewById(android.R.id.content),
                        "Opción no implementada",
                        Snackbar.LENGTH_LONG
                ).show();
        }
        return true;
    }

    /**
     * Carga el estado del juego desde un fichero
     * y mostrar un mensaje de exito en caso de que
     * se haya cargado correctamente
     */
    private void cargarPartida() {
        String filename = obtenerNombreFichero();
        try {
            FileInputStream fis = openFileInput(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            fis.close();
            juegoBantumi.deserializa(sb.toString());
            Snackbar.make(
                    findViewById(android.R.id.content),
                    R.string.loadSuccess,
                    Snackbar.LENGTH_LONG
            ).show();
            Log.i(LOG_TAG, "cargarPartida() -> " + sb);
        } catch (Exception e) {
            Snackbar.make(
                    findViewById(android.R.id.content),
                    R.string.loadError,
                    Snackbar.LENGTH_LONG
            ).show();
            Log.e(LOG_TAG, "cargarPartida() -> " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Comprueba si existe un fichero con el nombre
     * por defecto
     * @return true si existe, false en caso contrario
     */
    private boolean existeJuegoGuardado() {
        String filename = obtenerNombreFichero();
        File file = new File(getFilesDir(), filename);
        return file.exists();
    }


    /**
     * Guarda el estado del juego en un fichero
     * y mostrar un mensaje de exito en caso de que
     * se haya guardado correctamente
     * o un mensaje de error en caso contrario
     */
    private void guardarPartida() {
        String filename = obtenerNombreFichero();
        String serializedGameData = juegoBantumi.serializa();
        try {
            FileOutputStream fos = openFileOutput(filename, MODE_PRIVATE);
            fos.write(serializedGameData.getBytes());
            fos.close();
            Snackbar.make(
                    findViewById(android.R.id.content),
                    R.string.saveSuccess,
                    Snackbar.LENGTH_LONG
            ).show();
            Log.e(LOG_TAG, "guardarPartida() -> " + serializedGameData);
        } catch (Exception e) {
            Snackbar.make(
                    findViewById(android.R.id.content),
                    R.string.saveError,
                    Snackbar.LENGTH_LONG
            ).show();
            Log.e(LOG_TAG, "guardarPartida() -> " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Devuelve el nombre del fichero
     *
     * @return nombre del fichero
     */
    private String obtenerNombreFichero() {
        return getResources().getString(R.string.default_filename);
    }

    /**
     * Acción que se ejecuta al pulsar sobre cualquier hueco
     *
     * @param v Vista pulsada (hueco)
     */
    public void huecoPulsado(@NonNull View v) {
        String resourceName = getResources().getResourceEntryName(v.getId()); // pXY
        int num = Integer.parseInt(resourceName.substring(resourceName.length() - 2));
        Log.i(LOG_TAG, "huecoPulsado(" + resourceName + ") num=" + num);
        switch (juegoBantumi.turnoActual()) {
            case turnoJ1:
                juegoBantumi.jugar(num);
                break;
            case turnoJ2:
                juegaComputador();
                break;
            default:    // JUEGO TERMINADO
                finJuego();
        }
        if (juegoBantumi.juegoTerminado()) {
            finJuego();
        }
    }

    /**
     * Elige una posición aleatoria del campo del jugador2 y realiza la siembra
     * Si mantiene turno -> vuelve a jugar
     */
    void juegaComputador() {
        while (juegoBantumi.turnoActual() == JuegoBantumi.Turno.turnoJ2) {
            int pos = 7 + (int) (Math.random() * 6);    // posición aleatoria [7..12]
            Log.i(LOG_TAG, "juegaComputador(), pos=" + pos);
            if (juegoBantumi.getSemillas(pos) != 0 && (pos < 13)) {
                juegoBantumi.jugar(pos);
            } else {
                Log.i(LOG_TAG, "\t posición vacía");
            }
        }
    }

    /**
     * El juego ha terminado. Volver a jugar?
     */
    private void finJuego() {
        String texto = (juegoBantumi.getSemillas(6) > 6 * numInicialSemillas)
                ? "Gana Jugador 1"
                : "Gana Jugador 2";
        if (juegoBantumi.getSemillas(6) == 6 * numInicialSemillas) {
            texto = "¡¡¡ EMPATE !!!";
        }
        Snackbar.make(
                findViewById(android.R.id.content),
                texto,
                Snackbar.LENGTH_LONG
        )
        .show();

        // @TODO guardar puntuación

        // terminar
        new FinalAlertDialog().show(getSupportFragmentManager(), "ALERT_DIALOG");
    }
}