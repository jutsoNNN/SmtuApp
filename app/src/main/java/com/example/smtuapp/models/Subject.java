package com.example.smtuapp.models;

public class Subject {
    private String name;
    private String room;
    private String note;
    private String startTime;
    private String endTime;

    // Конструктор
    public Subject(String name, String room, String note, String startTime, String endTime) {
        this.name = name;
        this.room = room;
        this.note = note;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Конструкктор
    public Subject() {
    }

    // Геттеры и сеттеры
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
