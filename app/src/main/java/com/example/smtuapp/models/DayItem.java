package com.example.smtuapp.models;

public class DayItem {
    private String shortName;   // Пример: "ПН"
    private String date;        // Пример: "07.04"
    private boolean isSelected; // Выбран ли этот день

    // Конструктор
    public DayItem(String shortName, String date) {
        this.shortName = shortName;
        this.date = date;
        this.isSelected = false;
    }

    // Геттеры и сеттеры

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public String toString() {
        return shortName + " (" + date + ")";
    }

}
