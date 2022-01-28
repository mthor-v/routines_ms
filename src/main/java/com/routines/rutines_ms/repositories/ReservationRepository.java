package com.routines.rutines_ms.repositories;


import com.routines.rutines_ms.models.Reservation;
import com.routines.rutines_ms.models.Routine;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ReservationRepository extends MongoRepository<Reservation, String> {

    @Query("{'clientDni': ?0 , 'classId':{'$regex': ?1 }}")
    List<Reservation> findDuplicates(Integer term, String term2); // changed Routine

    @Query("{'clientDni': ?0 }")
    List<Reservation> findReservations(Integer term);

    @Query("{'classId': {'$regex': ?0 } }")
    List<Reservation> adminFindReservations(String term);

}
