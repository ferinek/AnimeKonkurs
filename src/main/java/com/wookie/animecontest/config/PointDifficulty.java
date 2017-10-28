package com.wookie.animecontest.config;

public enum PointDifficulty {

    EASY(1, "yellowgreen"),
    MEDIUM(2, "orange"),
    HARD(3, "tomato");

    int points;
    String color;

    PointDifficulty(int points, String color) {
        this.points = points;
        this.color = color;
    }
    public int getPoints(){
        return points;
    }
    public String getColor (){
        return color;
    }

    static public PointDifficulty getByPoints(int points){
        if (points==1)return EASY;
        if (points==2)return MEDIUM;
        if (points==3)return HARD;
        return null;
    }
}
