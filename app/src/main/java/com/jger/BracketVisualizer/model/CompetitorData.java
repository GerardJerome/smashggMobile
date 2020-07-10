package com.jger.BracketVisualizer.model;

import java.io.Serializable;

/**
 * Created by Emil on 21/10/17.
 */

public class CompetitorData implements Serializable {

    private String name;
    private String score;
    private boolean isFromLooser;

    public CompetitorData(String name, String score, Boolean isFromLooser) {
        this.name = name;
        this.score = score;
        this.isFromLooser = isFromLooser;
    }

    public boolean isFromLooser() {
        return isFromLooser;
    }

    public void setFromLooser(boolean fromLooser) {
        isFromLooser = fromLooser;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
