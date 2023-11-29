package com.example.debutwork.Repository;

import com.example.debutwork.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository  extends JpaRepository<Reservation, Integer>{
    Reservation findByCinEtudiantAndEstValide(Long cinEtudiant, boolean estValide);
}
