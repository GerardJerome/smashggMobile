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
    private Boolean isVisible=true;

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

    public MatchData(CompetitorData competitorOne, CompetitorData competitorTwo, int height, int originalHeight, String identifier, Boolean isVisible) {
        this.competitorOne = competitorOne;
        this.competitorTwo = competitorTwo;
        this.height = height;
        OriginalHeight = originalHeight;
        this.identifier = identifier;
        this.isVisible = isVisible;
    }

    public Boolean getVisible() {
        return isVisible;
    }

    public void setVisible(Boolean visible) {
        isVisible = visible;
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
