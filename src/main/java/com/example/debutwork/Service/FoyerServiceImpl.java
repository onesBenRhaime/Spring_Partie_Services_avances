package com.example.debutwork.Service;

import com.example.debutwork.entity.Foyer;
import com.example.debutwork.entity.TypeC;
import com.example.debutwork.entity.Universite;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FoyerServiceImpl {
    Foyer addFoyer(Foyer foyer);
    List<Foyer> getAllFoyers();
    Foyer updateFoyer(Foyer foyer);
    void deleteFoyer(Long id);
    List<Foyer> findFoyersByTypeChambre(TypeC TypeChambre);
    long findCapaciteFoyerByIdBloc(Long Idbloc);


    Universite affecterFoyerAUniversite(Long idFoyer, String nomUniversite);

    Foyer ajouterFoyerEtAffecterAUniversite(Foyer foyer, Long idUniversite);

    Universite desaffecterFoyerAUniversite(Long idUniversite);
}
