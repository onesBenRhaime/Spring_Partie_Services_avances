package com.example.debutwork.Service;

import com.example.debutwork.entity.Reservation;

import java.util.List;
import java.util.Map;

public interface ReservationServiceImpl {
    Reservation addReservation(Reservation reservation);

    List<Reservation> getAllReservations();
    Reservation updateReservation(Reservation reservation);
    void deleteReservation(Integer id);

    Map<String, Object> estValide(Integer id);

    Reservation ajouterReservation(long idBloc, long cinEtudiant);

    Reservation annulerReservation(long cinEtudiant);
}
