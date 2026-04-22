package org.w2105627_coursework;

public class SensorReading{
        private String id; // Unique reading event ID (UUID recommended)
        private long timestamp; // Epoch time (ms) when the reading was captured
        private double value; // The actual metric value recorded by the hardware


    public SensorReading(){};
    public SensorReading(String id, long timestamp,double value){
        this.id = id; this.timestamp = timestamp; this.value = value;
    }

    public String getId() {
        return id;
    }
    public double getValue() {
        return value;
    }
    public long getTimestamp() {
        return timestamp;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    public void setValue(double value) {
        this.value = value;
    }
}
