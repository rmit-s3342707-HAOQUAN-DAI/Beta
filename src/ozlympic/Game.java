package ozlympic;

import java.util.*;


public class Game {

    ArrayList<Athelete> atheletes = new ArrayList<Athelete>();
    ArrayList<Integer> scores = new ArrayList<Integer>();
    ArrayList<Integer> ranks = new ArrayList<Integer>();

    int gameType = 0;
    int gameIndex = 0;

    int guessedWinnerIndex = -1;
    boolean guessedCorrect = false;

    public Game(int gameType, int gameIndex, Athelete[] atheletes) {
        this.gameType = gameType;
        this.gameIndex = gameIndex;
        int totalAtheletes = Utility.GetRandom(4, 8);
        for (int i = 0; i < atheletes.length; i++) {
            Athelete athelete = atheletes[i];
            athelete.initGame();
            if (athelete.gameType == gameType || athelete.gameType == GameType.ALL) {
                athelete.selectedGameType = gameType;
                this.atheletes.add(athelete);
                if (this.atheletes.size() >= totalAtheletes) {
                    return;
                }
            }
        }
    }

    public ArrayList<Athelete> getAtheletes() {
        return this.atheletes;
    }

    public void setGuessedWinnerIndex(int guessedWinnerIndex) {
        this.guessedWinnerIndex = guessedWinnerIndex - 1; // change sequence to index: e.g. from 1 - 10 to 0 - 9
    }

    public boolean guessedCorrect() {
        return this.guessedCorrect;
    }

    public int getGuessedWinnerIndex() {
        return this.guessedWinnerIndex;
    }

    void run() {
        if (this.atheletes.size() <= 0) {
            System.out.println("NO ATHELETES in this game");
            return;
        }
        for (Athelete athelete : this.atheletes) {
            scores.add(athelete.compete());
            athelete.putSpeedByGame(athelete.getSpeed(), this.gameIndex);
        }

        Collections.sort(scores, Collections.reverseOrder());
        int atheleteIndex = 0;
        for (Athelete athelete : this.atheletes) {
            if (athelete.getSpeed() == scores.get(0)) {
                athelete.putRankByGame(1, gameIndex);
                athelete.putScoreByGame(3, gameIndex);

                //Check if guessedWinner is correct?
                if (this.guessedWinnerIndex == atheleteIndex) {
                    this.guessedCorrect = true;
                }

            } else if (athelete.getSpeed() == scores.get(1)) {
                athelete.putRankByGame(2, gameIndex);
                athelete.putScoreByGame(2, gameIndex);
            } else if (athelete.getSpeed() == scores.get(2)) {
                athelete.putRankByGame(3, gameIndex);
                athelete.putScoreByGame(1, gameIndex);
            }
            atheleteIndex++;
        }
    }

    void showAtheleteNames() {
        System.out.format("%8s%15s\n", "", "Name");
        System.out.println("=========================");
        int sequence = 1; // for display purpose, not 0.
        for (Athelete athelete : this.atheletes) {
            System.out.format("%8d%15s\n", sequence++, athelete.getName());
        }
        System.out.println("=========================");
    }

    void showAtheleteResults() {
//        System.out.println("Name" + "\t" + "Result" + "\t" + "Rank");
        System.out.format("%16s%10s%12s\n", "Name", "Result", "Rank");
        System.out.println("==========================================");
        for (Athelete athelete : this.atheletes) {
            System.out.format("%16s%10d%12s\n", athelete.name, athelete.getSpeedByGameIndex(gameIndex), athelete.getRankByGameIndex(gameIndex));
//            System.out.println(athelete.name + "\t" + athelete.getSpeedByGameIndex(gameIndex) + "\t" + athelete.getRankByGameIndex(gameIndex));
        }
        System.out.println("==========================================");
    }


    String getGameTypeText() {
        if (gameType == GameType.SWIMMING) {
            return "Swimming";
        }
        if (gameType == GameType.CYCLING) {
            return "Cycling";
        }
        if (gameType == GameType.RUNNING) {
            return "Running";
        }
        return "";
    }
}