package com.jger.BracketVisualizer.model;

import java.io.Serializable;

/**
 * Created by Emil on 21/10/17.
 */

public class MatchData implements Serializable {

    private CompetitorData competitorOne;
    private CompetitorData competitorTwo;
    private int height;
    private int OriginalHeight;
    private String identifier;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public MatchData(CompetitorData competitorOne, CompetitorData competitorTwo,String identifier) {
        this.competitorOne = competitorOne;
        this.competitorTwo = competitorTwo;
        this.identifier=identifier;
    }

    public CompetitorData getCompetitorTwo() {
        return competitorTwo;
    }

    public void setCompetitorTwo(CompetitorData competitorTwo) {
        this.competitorTwo = competitorTwo;
    }

    public CompetitorData getCompetitorOne() {

        return competitorOne;
    }

    public void setCompetitorOne(CompetitorData competitorOne) {
        this.competitorOne = competitorOne;
    }

    public int getOriginalHeight() {
        return OriginalHeight;
    }

    public void setOriginalHeight(int originalHeight) {
        OriginalHeight = originalHeight;
    }
}
