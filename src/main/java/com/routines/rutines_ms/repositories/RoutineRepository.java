package com.routines.rutines_ms.repositories;



import com.routines.rutines_ms.models.Routine;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RoutineRepository extends MongoRepository<Routine, String> {

    @Query("{'classTipe':{'$regex': ?0, '$options': 'i'}}")
    List<Routine> findByRegexClassTipe(String term);

    @Query("{'instructor':{'$regex': ?0, '$options': 'i'}}")
    List<Routine> findByRegexIntructor(String term);

    @Query("{'difficulty':?0}")
    List<Routine> findByRegexDifficulty(Integer term);

    @Query("{'classState':?0}")
    List<Routine> findByClassState(Boolean term);

  

}