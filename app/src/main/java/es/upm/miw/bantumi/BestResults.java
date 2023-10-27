package es.upm.miw.bantumi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import es.upm.miw.bantumi.model.game_result_model.GameResult;
import es.upm.miw.bantumi.model.game_result_model.GameResultViewModel;

public class BestResults extends AppCompatActivity {

    private GameResultViewModel viewModel;
    private GameResultAdapter adapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.best_results_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.deleteAction:
                new AlertDialog.Builder(this)
                        .setTitle("Confirmación")
                        .setMessage("¿Está seguro de que desea eliminar todos los resultados?")
                        .setPositiveButton("Sí", (dialog, which) -> {
                            viewModel.deleteAll();
                        })
                        .setNegativeButton("No", null)
                        .show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_best_results);

        RecyclerView resultsRecyclerView = findViewById(R.id.resultsRecyclerView);
        resultsRecyclerView.setLayoutManager(new LinearLayoutManager(this)); // This line is important

        adapter = new GameResultAdapter(new ArrayList<>());
        resultsRecyclerView.setAdapter(adapter);

        setTitle("Top 10 Resultados");

        viewModel = new ViewModelProvider(this).get(GameResultViewModel.class);

        viewModel.getTop10Results().observe(this, top10results -> {
            adapter.setGameResults(top10results);
        });
    }
}