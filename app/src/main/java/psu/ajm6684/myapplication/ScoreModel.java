package psu.ajm6684.myapplication;

public class ScoreModel
{
    private String Match;
    private String Score;

    public ScoreModel() {}

    public ScoreModel(String Match, String Score){
        this.Match = Match;
        this.Score = Score;
    }

    public String getMatch() {
        return Match;
    }

    public void setMatch(String match) {
        Match = match;
    }

    public String getScore() {
        return Score;
    }

    public void setScore(String score) {
        Score = score;
    }
}
