package es.upm.miw.bantumi.model.game_result_model;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class GameResultViewModel extends AndroidViewModel {

    private GameResultRepository gameResultRepository;

    public GameResultViewModel(Application application) {
        super(application);
        gameResultRepository = new GameResultRepository(application);
    }

    public void insert(GameResult gameResult) {
        this.gameResultRepository.insert(gameResult);
    }

    public LiveData<List<GameResult>> getTop10Results() {
        return this.gameResultRepository.getTop10Results();
    }

    public void deleteAll() {
        this.gameResultRepository.deleteAll();
    }
}