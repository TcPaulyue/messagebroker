package com.servicematrix.matrixmq.msg.client;

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

    public boolean equals(Location location) {
        return this.getX().equals(location.getX()) && this.getY().equals(location.getY()) && this.getZ().equals(location.getZ());
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

    public Double getZ() {
        return z;
    }

    public void setZ(Double z) {
        this.z = z;
    }


    public Double calcDistance(Location location){
        return Math.sqrt(Math.pow(Math.abs(this.x - location.getX()),2)+Math.pow(Math.abs(this.y - location.getY()),2)+Math.pow(Math.abs(this.z - location.getZ()),2));
    }
}
