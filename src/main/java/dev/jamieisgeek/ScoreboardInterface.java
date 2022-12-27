package dev.jamieisgeek;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import java.util.ArrayList;
import java.util.List;

public class ScoreboardInterface {
    private final ScoreboardManager manager;
    private final Scoreboard scoreboard;

    private String title;
    private List<String> lines;

    public ScoreboardInterface(String title, List<String> lines) {
        this.title = ChatColor.translateAlternateColorCodes('&', title);
        this.lines = this.formatLines(lines);

        manager = Bukkit.getScoreboardManager();
        scoreboard = manager.getNewScoreboard();
    }

    private List<String> formatLines(List<String> lines) {
        List<String> formatted = new ArrayList<>();
        lines.forEach(line -> {
            String newline = ChatColor.translateAlternateColorCodes('&', line);
            formatted.add(newline);
        });

        return formatted;
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public ScoreboardManager getManager() {
        return manager;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getLines() {
        return lines;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLines(List<String> lines) {
        this.lines = lines;
    }

    private void makeScoreboard() {
        scoreboard.registerNewObjective("scoreboard", "dummy", title).setDisplaySlot(DisplaySlot.SIDEBAR);
        lines.forEach(line -> {
            Score score = scoreboard.getObjective("scoreboard").getScore(line);
            score.setScore(lines.indexOf(line));
        });
    }

    public void update(Player player) {
        this.makeScoreboard();
        player.setScoreboard(scoreboard);
    }

    public void resetScoreboard(Player player) {
        player.setScoreboard(manager.getNewScoreboard());
    }
}
