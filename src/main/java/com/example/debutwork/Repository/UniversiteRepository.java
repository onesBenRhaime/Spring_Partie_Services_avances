package com.example.debutwork.Repository;

import com.example.debutwork.entity.Universite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UniversiteRepository extends JpaRepository<Universite, Long> {

    /*
    @Query("SELECT u FROM Universite u JOIN u.foyer f JOIN f.blocs b JOIN b.chambres c WHERE c.idChambre = :chambreId")
    Universite findUniversitesByChambreId(@Param("chambreId") Long chambreId);
    */


    @Query("SELECT u FROM Universite u JOIN Chambre c on (c.bloc.foyers.universite.idUniversite = u.idUniversite) WHERE c.idChambre = :chambreId")
    Universite findUniversitesByChambreId(@Param("chambreId") Long chambreId);

    Universite findByNomUniversite(String nomUniversite);

    //getUniversiteByIdEtudiant
    @Query("SELECT u FROM Universite u JOIN u.foyer f JOIN f.blocs b JOIN b.chambres c JOIN c.Reservations r JOIN r.etudiants e " +
            "WHERE e.idEtudiant = :IdEtudiant")
    Universite findUniversitesByIdEtudiant(@Param("IdEtudiant") Long IdEtudiant);

}
