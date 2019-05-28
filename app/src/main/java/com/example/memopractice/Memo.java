package com.example.memopractice;

public class Memo {
    private String type , content;
    private int date , time;

    public Memo(int date,int time,String type,String content){
        this.date = date;
        this.content = content;
        this.type = type;
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public int getTime() {
        return time;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public int getDate() {
        return date;
    }
}
