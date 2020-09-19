package com.servicematrix.engine.model;

public class Door {
    private String name;
    private Double x;
    private Double y;

    public Door(String name, Double x, Double y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public Door(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }
}
