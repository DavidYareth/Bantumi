package es.upm.miw.bantumi.model.game_result_model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "game_result_table")
public class GameResult {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "winner_name")
    private String winnerName;

    @ColumnInfo(name = "winner_seeds")
    private int winnerSeeds;

    @ColumnInfo(name = "loser_name")
    private String loserName;

    @ColumnInfo(name = "loser_seeds")
    private int loserSeeds;

    @ColumnInfo(name = "date_time")
    private String dateTime;

    public GameResult(String winnerName, int winnerSeeds, String loserName, int loserSeeds, String dateTime) {
        this.winnerName = winnerName;
        this.winnerSeeds = winnerSeeds;
        this.loserName = loserName;
        this.loserSeeds = loserSeeds;
        this.dateTime = dateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWinnerName() {
        return winnerName;
    }

    public void setWinnerName(String winnerName) {
        this.winnerName = winnerName;
    }

    public int getWinnerSeeds() {
        return winnerSeeds;
    }

    public void setWinnerSeeds(int winnerSeeds) {
        this.winnerSeeds = winnerSeeds;
    }

    public String getLoserName() {
        return loserName;
    }

    public void setLoserName(String loserName) {
        this.loserName = loserName;
    }

    public int getLoserSeeds() {
        return loserSeeds;
    }

    public void setLoserSeeds(int loserSeeds) {
        this.loserSeeds = loserSeeds;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}