package com.routines.rutines_ms.controller;


import com.routines.rutines_ms.models.Reservation;
import com.routines.rutines_ms.models.Routine;
import com.routines.rutines_ms.repositories.ReservationRepository;
import com.routines.rutines_ms.repositories.RoutineRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
public class ReservationController {
    private final RoutineRepository routineRepository;
    private final ReservationRepository reservationRepository;




    public ReservationController(ReservationRepository reservationRepository,RoutineRepository routineRepository) {
        this.reservationRepository = reservationRepository;
        this.routineRepository = routineRepository;
    }


    @PostMapping("/reservation")
    Reservation createReservation (@RequestBody Reservation reservation){

        Routine originRoutine = routineRepository.findById(reservation.getClassId()).orElse(null);

        if (originRoutine != null ) {

            if(originRoutine.getPersonsAvailable() > 0 ){

                if ((this.reservationRepository.findDuplicates(reservation.getClientDni(),reservation.getClassId()).size() == 0)){

                    // sets para la reserva
                    reservation.setClassTipe(originRoutine.getClassTipe());
                    reservation.setInstructor(originRoutine.getInstructor());
                    reservation.setRoom(originRoutine.getRoom());
                    reservation.setRoutineDate(originRoutine.getRoutineDate());
                    reservation.setDuration(originRoutine.getDuration());
                    reservation.setDifficulty(originRoutine.getDifficulty());

                    // get modificador de la rutina original
                    originRoutine.setPersonsAvailable(originRoutine.getPersonsAvailable() - 1);

                }else {
                    throw new ResponseStatusException(HttpStatus.LOCKED, "Ya tiene registrada la rutina");
                }
            }else{
                throw new ResponseStatusException(HttpStatus.INSUFFICIENT_STORAGE, "La rutina ya esta llena");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El Id de la rutina no existe");
        }
        this.routineRepository.save(originRoutine);
        return this.reservationRepository.save(reservation);
    }

    @GetMapping("/reservationUs")
    List<Reservation> getAllReservations(@RequestParam("dni_user")Integer clientDni){

        if(reservationRepository.findReservations(clientDni).size() > 0){
            return this.reservationRepository.findReservations(clientDni);
        }else{
            throw new ResponseStatusException(HttpStatus.NO_CONTENT,"No se encuentran registros del cliente");
        }
    }

    @GetMapping("/reservationAd")
    List<Reservation> getAllReservations(@RequestParam("classId")String classId){

        if (reservationRepository.adminFindReservations(classId).size() > 0){
            return this.reservationRepository.adminFindReservations(classId);
        }else{
            throw new ResponseStatusException(HttpStatus.NO_CONTENT,"No se encuentran registros de la rutina");
        }
    }

    @DeleteMapping("/reservation/{reservationId}")
    String deleteReservation(@PathVariable String reservationId) {

            Routine originRoutine = this.routineRepository.findById((this.reservationRepository.findById(reservationId)).get().getClassId()).orElse(null);



        if(this.reservationRepository.existsById(reservationId)){
            this.reservationRepository.deleteById(reservationId);
            if (originRoutine != null){
                originRoutine.setPersonsAvailable(originRoutine.getPersonsAvailable() + 1);
                this.routineRepository.save(originRoutine);

            }else{
                return "la reserva fue eliminada, pero la rutina ya no existe" ;
            }
            return "la reserva fue eliminada y se desconto su participacion de la rutina original";

        } else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"reserva no encontrada");
        }
    }

}
