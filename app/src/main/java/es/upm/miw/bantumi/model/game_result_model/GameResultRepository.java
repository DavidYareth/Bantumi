package es.upm.miw.bantumi.model.game_result_model;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class GameResultRepository {

    private GameResultDAO gameResultDao;

    GameResultRepository(Application application) {
        GameResultRoomDatabase db = GameResultRoomDatabase.getDatabase(application);
        gameResultDao = db.gameResultDao();
    }

    void insert(GameResult gameResult) {
        GameResultRoomDatabase.databaseWriteExecutor.execute(() -> {
            gameResultDao.insert(gameResult);
        });
    }
}