package com.wookie.animecontest.component.dto;

public class PlayerDTO {

    private String name;
    private Integer points;


    private boolean active;

    public PlayerDTO() {
    }

    public PlayerDTO(String name, Integer points) {
        this.name = name;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return String.format("%s: %d %s", name, points, active ? "<====" : "");
    }
}
