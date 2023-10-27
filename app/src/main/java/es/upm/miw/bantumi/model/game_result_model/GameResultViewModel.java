package es.upm.miw.bantumi.model.game_result_model;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

public class GameResultViewModel extends AndroidViewModel {

    private GameResultRepository gameResultRepository;

    public GameResultViewModel(Application application) {
        super(application);
        gameResultRepository = new GameResultRepository(application);
    }

    public void insert(GameResult gameResult) {
        this.gameResultRepository.insert(gameResult);
    }
}