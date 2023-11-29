package com.example.debutwork.Service;

import com.example.debutwork.Repository.ChambreRepository;
import com.example.debutwork.Repository.EtudiantRepository;
import com.example.debutwork.Repository.ReservationRepository;
import com.example.debutwork.entity.Chambre;
import com.example.debutwork.entity.Etudiant;
import com.example.debutwork.entity.Reservation;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ReservationService implements ReservationServiceImpl {
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    ChambreRepository  chambreRepository;
    @Autowired
    EtudiantRepository  etudiantRepository;
    @Override
    public Reservation addReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

     public  Map<String, Object> ajouterReservationproject(Reservation reservation){
      Map<String, Object> result = new HashMap<>();

      // Vérifier si l'étudiant avec le CIN existe
      Optional<Etudiant> etudiantOpt = etudiantRepository.findByCin(reservation.getCinEtudiant());
      if (etudiantOpt.isEmpty()) {
          // L'étudiant n'existe pas, renvoyer un message d'erreur
          result.put("success", false);
          result.put("message", "Étudiant non trouvé avec le CIN : " + reservation.getCinEtudiant());
          return result;
      }

      // L'étudiant existe, continuez avec la réservation
      Etudiant etudiantConnecte = etudiantOpt.get();

      // Valider ou ajuster d'autres champs de la réservation si nécessaire
      reservation.setEstValide(false); // Par exemple, initialement non validée


      // Associer la réservation à l'étudiant connecté
      reservation.setEtudiants(Set.of(etudiantConnecte));

      // Associer l'étudiant connecté à la réservation
      Set<Reservation> reservationsEtudiant = etudiantConnecte.getReservations();
      if (reservationsEtudiant == null) {
          reservationsEtudiant = new HashSet<>();
      }
      reservationsEtudiant.add(reservation);
      etudiantConnecte.setReservations(reservationsEtudiant);

      // Enregistrer la réservation et l'étudiant
      reservationRepository.save(reservation);

      // Renvoyer un message de succès
      result.put("success", true);
      result.put("message", "Réservation ajoutée avec succès pour l'étudiant avec le CIN : " + reservation.getCinEtudiant());
      result.put("reservation", reservation); // Vous pouvez ajouter la réservation dans le résultat si nécessaire

      return result;

  }
    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation updateReservation(Reservation reservation) {
        if (reservationRepository.existsById(reservation.getIdReservation())) {
            return reservationRepository.save(reservation);
        }
        return null;
    }
    @Override
    public void deleteReservation(Integer id) { reservationRepository.deleteById(id); }

    @Override
    public Map<String, Object> estValide(Integer idReservation){

    Map<String, Object> response = new HashMap<>();

    // Vérifier si la réservation avec l'ID spécifié existe
    Optional<Reservation> reservationOpt = reservationRepository.findById(idReservation);
    if (reservationOpt.isEmpty()) {
        response.put("status", "Échec");
        response.put("message", "Aucune réservation trouvée avec l'ID : " + idReservation);
        return response;
    }

    // La réservation existe, continuer le processus de validation
    Reservation reservation = reservationOpt.get();

    // Vérifier si une chambre du type demandé existe
    Optional<Chambre> chambreOpt = chambreRepository.findAvailableChambreByType(reservation.getTypeChambre());

    if (chambreOpt.isPresent()) {
        // Si une chambre du type demandé existe, valider la réservation
        Chambre chambre = chambreOpt.get();
        reservation.setEstValide(true);

        // Ajouter la relation entre la chambre et la réservation
        chambre.getReservations().add(reservation);
        reservation.setTypeChambre(chambre.getTypeChambre());

        // Enregistrer les modifications
        chambreRepository.save(chambre);
        reservationRepository.save(reservation);

        response.put("status", "Succès");
        response.put("message", "Réservation validée avec succès.");
    } else {
        // Si aucune chambre du type demandé n'est disponible
        response.put("status", "Échec");
        response.put("message", "Aucune chambre disponible du type demandé pour la réservation.");
    }

    return response;
}

    @Override
    public Reservation ajouterReservation(long idBloc, long cinEtudiant) {
        // Rechercher la chambre disponible dans le bloc
        Chambre chambreDisponible = chambreRepository.findChambreDisponibleByBlocId(idBloc);

        // Rechercher l'étudiant par son cin
        Optional<Etudiant> etudiant = etudiantRepository.findByCin(cinEtudiant);
        Etudiant etudiantConnecte = etudiant.get();
        if (chambreDisponible != null && etudiant != null) {
            // Vérifier la capacité maximale de la chambre
            if (chambreDisponible.getCapaciteChambre() > 0) {
                // Générer le numéro de réservation
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
                String anneeUniversitaire = dateFormat.format(new Date());

                String numReservation = chambreDisponible.getNumeroChambre() + "-" +
                        chambreDisponible.getBloc().getNomBloc() + "-" +
                        anneeUniversitaire;

                // Créer la réservation
                Reservation reservation = new Reservation();
                reservation.setCinEtudiant(cinEtudiant);
                reservation.setAnneeUniversitaire(new Date());
                reservation.setEstValide(true);
                reservation.setNumReservation(numReservation);
                reservation.setTypeChambre(chambreDisponible.getTypeChambre());

                // Mettre à jour la capacité de la chambre
                chambreDisponible.setCapaciteChambre(chambreDisponible.getCapaciteChambre() - 1);

                // Ajouter la réservation à la chambre et à l'étudiant
                chambreDisponible.getReservations().add(reservation);
                etudiantConnecte.getReservations().add(reservation);

                // Enregistrer les modifications
                chambreRepository.save(chambreDisponible);
                etudiantRepository.save(etudiantConnecte);
                reservationRepository.save(reservation);

                return reservation;
            } else {
                throw new NoSuchElementException("Capacité maximale de la chambre atteinte.");
            }
        } else {
            throw new EntityNotFoundException("Chambre ou étudiant non trouvé.");
        }
    }

    @Override
    public Reservation annulerReservation(long cinEtudiant) {
        // Rechercher la réservation de l'étudiant
        Reservation reservation = reservationRepository.findByCinEtudiantAndEstValide(cinEtudiant, true);

        if (reservation != null) {
            // Mettre à jour l'état de la réservation
            reservation.setEstValide(false);

            // Désaffecter l'étudiant associé
            reservation.getEtudiants().remove(etudiantRepository.findByCin(cinEtudiant));

            // Désaffecter la chambre associée et mettre à jour sa capacité
            Optional<Chambre> ch = Optional.ofNullable(chambreRepository.findChambreByReservations(reservation));

            Chambre chambre = ch.get();
            chambre.setCapaciteChambre(chambre.getCapaciteChambre() + 1);
            chambre.getReservations().remove(reservation);

            // Enregistrer les modifications
            reservationRepository.save(reservation);
            chambreRepository.save(chambre);

            return reservation;
        } else {
            throw new EntityNotFoundException("Aucune réservation trouvée pour l'étudiant avec le CIN : " + cinEtudiant);
        }
    }
}
