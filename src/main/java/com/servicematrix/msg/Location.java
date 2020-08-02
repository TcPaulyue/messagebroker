package com.servicematrix.msg;

public  class Location{
    Double x;
    Double y;
    Double z;

    public Location(Double x,Double y,Double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String toString() {
        return "Location{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
