package com.routines.rutines_ms.controller;


import com.routines.rutines_ms.models.Routine;
import com.routines.rutines_ms.repositories.RoutineRepository;

import org.springframework.data.mongodb.core.aggregation.DateOperators;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.UnaryOperator;


@RestController
public class RoutineController {
    private final RoutineRepository routineRepository;
    private RoutineRepository routinesUpdaterDates;


    public RoutineController(RoutineRepository routineRepository,RoutineRepository routinesUpdaterDates){
        this.routineRepository = routineRepository;
        this.routinesUpdaterDates = routinesUpdaterDates;
    }


    @PostMapping("/routines")

    Routine createRoutine (@RequestBody Routine routine){return this.routineRepository.save(routine);}


    @PutMapping("/routines")
    Routine updateRoutine (@RequestBody Routine routine) {
        if (this.routineRepository.existsById(routine.getClassId())){
            return this.routineRepository.save(routine);
    }else {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "la rutina a modificar no existe");
    }
    }


    // actualizar rutina por identificador y por atributos independientes
    @PatchMapping("/routines")
    Routine patchRoutine (@RequestBody Map routine) throws ParseException {



        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZ");

        Optional<Routine> routinePatch = this.routineRepository.findById((String) routine.get("classId"));
        if (routinePatch.isPresent()) {
            System.out.println(routine);
            if (routine.containsKey("classTipe")) {
                routinePatch.get().setClassTipe((String) routine.get("classTipe"));
            }
            if (routine.containsKey("instructor")) {
                routinePatch.get().setInstructor((String) routine.get("instructor"));
            }
            if (routine.containsKey("room")) {
                routinePatch.get().setRoom((Integer) routine.get("room"));
            }
            if (routine.containsKey("routineDate")) {
                routinePatch.get().setRoutineDate(df.parse((String) routine.get("routineDate")));
            }
            if (routine.containsKey("duration")) {
                routinePatch.get().setDuration((Integer) routine.get("duration"));
            }
            if (routine.containsKey("difficulty")) {
                routinePatch.get().setDifficulty((Integer) routine.get("difficulty"));
            }
            if (routine.containsKey("personsLimit")) {
                routinePatch.get().setPersonsLimit((Integer) routine.get("personsLimit"));
            }
            if (routine.containsKey("personsAvailable")) {
                routinePatch.get().setPersonsAvailable((Integer) routine.get("personsAvailable"));
            }
            if (routine.containsKey("classState")) {
                routinePatch.get().setClassState((boolean) routine.get("classState"));
            }

            return this.routineRepository.save(routinePatch.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "el identificador de la rutina no existe");
        }
    };



    // modifica todos los modelos que tengan fecha vencida. posible implementacion individual del metodo actualizar estado de rutinas
    @GetMapping("/updateRoutines")
    void updateRoutinesStates(){


        for (Routine r1:this.routinesUpdaterDates.findAll()){
            if(r1.getRoutineDate().before(new Date())){
                r1.setClassState(false);
                this.routinesUpdaterDates.save(r1);
            }
        }
    }

    // obtiene json de todas las rutinas segun los filtros
    @GetMapping("/routines")
    List<Routine> getAllRoutines(@RequestParam("tipe") Optional<String> classTipe, @RequestParam("instructor")Optional<String> instructor, @RequestParam("difficulty")Optional<Integer> difficulty , @RequestParam("classState")Optional<Boolean> classState){

        List<Routine> lDescart= this.routineRepository.findAll();
        List<Routine> temp = new ArrayList<>();
        List indice= new ArrayList();



        if (classTipe.isPresent() ) {

            temp.addAll(this.routineRepository.findByRegexClassTipe(classTipe.get())); ;
           for(int i = 0; i < temp.size();i++){
               for(int j = 0; j < lDescart.size();j++){

                   if (lDescart.get(j).getClassId().equals(temp.get(i).getClassId()) ) {
                       indice.add(j);
                   }
               }
           }

            keepObjectsByIndexs(indice,lDescart);
            indice.clear();
            temp.clear();

        }
        if (instructor.isPresent() ){

            temp.addAll(this.routineRepository.findByRegexIntructor(instructor.get()));
            for(int i = 0; i < temp.size();i++){
                for(int j = 0; j < lDescart.size();j++){

                    if (lDescart.get(j).getClassId().equals(temp.get(i).getClassId()) ) {
                        indice.add(j);
                    }
                }
            }

            keepObjectsByIndexs(indice,lDescart);
            indice.clear();
            temp.clear();

        }
        if (difficulty.isPresent() ){

            temp.addAll(this.routineRepository.findByRegexDifficulty(difficulty.get()));
            for(int i = 0; i < temp.size();i++){
                for(int j = 0; j < lDescart.size();j++){

                    if (lDescart.get(j).getClassId().equals(temp.get(i).getClassId()) ) {
                        indice.add(j);
                    }
                }
            }

            keepObjectsByIndexs(indice,lDescart);
            indice.clear();
            temp.clear();

        }
        if(classState.isPresent()){

            temp.addAll(this.routineRepository.findByClassState(classState.get()));
            for(int i = 0; i < temp.size();i++){
                for(int j = 0; j < lDescart.size();j++){

                    if (lDescart.get(j).getClassId().equals(temp.get(i).getClassId()) ) {
                        indice.add(j);
                    }
                }
            }
            keepObjectsByIndexs(indice,lDescart);
            indice.clear();
            temp.clear();

        }

        return lDescart;
    }

    @DeleteMapping("/routines/{classId}")
    String deleteRoutine(@PathVariable String classId) {
        if(this.routineRepository.existsById(classId)){
            this.routineRepository.deleteById(classId);
            return "rutina eliminada";
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Reserva no encontrada");
        }}





    private void keepObjectsByIndexs(List indices,List rutinas){
        for (int i = 0; i < rutinas.size();i++){
            if(!indices.contains(i)){
                rutinas.remove(i);
                rutinas.add(i,null);
            }
        }
        Boolean flag = true;
        while(flag){
            flag = rutinas.remove(null);
        }

    }
}


