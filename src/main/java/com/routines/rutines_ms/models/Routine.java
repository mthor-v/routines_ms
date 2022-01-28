package com.routines.rutines_ms.models;

import org.springframework.data.annotation.Id;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Routine {

    @Id
    private String classId;
    private String classTipe;
    private String instructor;
    private Integer room;
    private Date routineDate;
    private Integer duration;
    private Integer difficulty;
    private Integer personsLimit;
    private Integer personsAvailable;
    private boolean classState;



    public Routine(String classTipe, String instructor, Integer room , Date routineDate ,Integer duration,Integer difficulty,Integer personsLimit ){

        this.classTipe = classTipe;
        this.instructor = instructor;
        this.room = room;
        this.routineDate =  routineDate;
        this.duration = duration;
        this.difficulty = difficulty;
        this.personsLimit = personsLimit;
        this.personsAvailable = personsLimit;

        Date postDayDate = new Date();

        // validador de fecha de rutina posterior a la fecha actual
        if (this.routineDate.after(new Date())){
           //System.out.println(this.routineDate.before(new Date())+"<--- date marker"); // borrar
            this.classState = true;
        } else{
            this.classState = false;
        }


    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassTipe() {
        return classTipe;
    }

    public void setClassTipe(String classTipe) {
        this.classTipe = classTipe;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public Integer getRoom() {
        return room;
    }

    public void setRoom(Integer room) {
        this.room = room;
    }

    public Date getRoutineDate() {


        return routineDate;
    }

    public void setRoutineDate(Date routineDate) {
        this.routineDate = routineDate;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public Integer getPersonsLimit() {
        return personsLimit;
    }

    public void setPersonsLimit(Integer personsLimit) {
        this.personsLimit = personsLimit;
    }

    public Integer getPersonsAvailable() {
        return personsAvailable;
    }

    public void setPersonsAvailable(Integer personsAvailable) {
        this.personsAvailable = personsAvailable;
    }

    public boolean getClassState() {
        return classState;
    }

    public void setClassState(boolean classState) {
        this.classState = classState;
    }


    @Override
    public String toString(){
        return this.classId;
    }

}
