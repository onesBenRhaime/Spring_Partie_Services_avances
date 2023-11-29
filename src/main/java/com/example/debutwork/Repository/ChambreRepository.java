package com.example.debutwork.Repository;

import com.example.debutwork.entity.Chambre;
import com.example.debutwork.entity.Reservation;
import com.example.debutwork.entity.TypeC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ChambreRepository extends JpaRepository<Chambre, Long> {

    //Get chambre qui a un etudiantId en parametre
    @Query("SELECT c FROM Chambre c JOIN c.Reservations r JOIN r.etudiants e WHERE e.idEtudiant = :IdEtudiant")
    Chambre findChambreByEtudiantId(@Param("IdEtudiant") Long IdEtudiant);
    @Query( "SELECT c FROM Chambre c " +
            "LEFT JOIN c.Reservations r " +
            "WHERE c.TypeChambre = :typeChambre " +
            "AND (r IS NULL OR r.estValide = false)"
          )
    Optional<Chambre> findAvailableChambreByType(@Param("typeChambre") TypeC typeChambre);
    Set<Chambre> findByNumeroChambreIn(List<Long> numeroChambres);
    Chambre findChambreDisponibleByBlocId(Long idBloc);

   Chambre findChambreByReservations(Reservation reservation);



}
