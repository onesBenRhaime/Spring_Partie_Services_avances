package com.example.debutwork.Controller;

import com.example.debutwork.Service.FoyerServiceImpl;
import com.example.debutwork.Service.ReservationServiceImpl;
import com.example.debutwork.entity.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reservation")
@CrossOrigin(origins = "http://localhost:4200/")
public class ReservationController {
    @Autowired
    ReservationServiceImpl reservationService;

    @PostMapping("/addReservation")
    Reservation addReservation(@RequestBody Reservation reservation) {
        return reservationService.addReservation(reservation);
    }
    @PostMapping("/ajouterReservation")
    Reservation ajouterReservation( @PathVariable Long idBloc, @PathVariable Long cinEtudiant) {
        return reservationService.ajouterReservation( idBloc ,cinEtudiant);
    }

    @PutMapping("/updateReservation")
    Reservation updateReservation(@RequestBody Reservation reservation) {
        return reservationService.updateReservation(reservation);
    }

    /************** Valider une reservation ou refuser *************/

    @PutMapping("/estValide/{id}")
    public ResponseEntity<Map<String, Object>> estValide(@PathVariable Integer id) {
        Map<String, Object> response = reservationService.estValide(id);

        if ("Succès".equals(response.get("status"))) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
    /**************************************************************/



    @GetMapping("/getAllReservations")
    List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @DeleteMapping("/deleteReservation/{id}")
    void deleteReservation(@PathVariable Integer id) {
        reservationService.deleteReservation(id);
    }
}
