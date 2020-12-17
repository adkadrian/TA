package com.example.trainingassistantv2;

public class Exercise {
    private int id;
    private String name, tool, muscle, category ;

    public Exercise(int id, String name, String tool, String muscle, String category) {
        this.id = id;
        this.name = name;
        this.tool = tool;
        this.muscle = muscle;
        this.category = category;
    }

    public Exercise(String name, String tool, String muscle, String category) {
        this.name = name;
        this.tool = tool;
        this.muscle = muscle;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTool() {
        return tool;
    }

    public void setTool(String tool) {
        this.tool = tool;
    }

    public String getMuscle() {
        return muscle;
    }

    public void setMuscle(String muscle) {
        this.muscle = muscle;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tool='" + tool + '\'' +
                ", part='" + muscle + '\'' +
                ", group='" + category + '\'' +
                '}';
    }
}
