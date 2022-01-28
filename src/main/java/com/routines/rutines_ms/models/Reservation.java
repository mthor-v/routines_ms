package com.routines.rutines_ms.models;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class Reservation {

    @Id
    private String reservationId;
    private String classId;        // foreing routine
    private Integer clientDni;          // foreing client
    private String clientFirstName;     // foreing client
    private String clientLastName;      // foreing client
    private String classTipe;
    private String instructor;
    private Integer room;
    private Date routineDate;
    private Integer duration;
    private Integer difficulty;


public Reservation( String classId, Integer clientDni, String clientFirstName, String clientLastName ){

    this.classId = classId;
    this.clientDni = clientDni;
    this.clientFirstName = clientFirstName;
    this.clientLastName = clientLastName;

}

    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public Integer getClientDni() {
        return clientDni;
    }

    public void setClientDni(Integer clientDni) {
        this.clientDni = clientDni;
    }

    public String getClientFirstName() {
        return clientFirstName;
    }

    public void setClientFirstName(String clientFirstName) {
        this.clientFirstName = clientFirstName;
    }

    public String getClientLastName() {
        return clientLastName;
    }

    public void setClientLastName(String clientLastName) {
        this.clientLastName = clientLastName;
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
}
