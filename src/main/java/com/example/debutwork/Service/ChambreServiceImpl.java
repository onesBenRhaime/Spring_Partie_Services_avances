package com.example.debutwork.Service;

import com.example.debutwork.entity.Chambre;

import java.util.List;

public interface ChambreServiceImpl {
    Chambre addChambre(Chambre chambre);
    List<Chambre> getAllChambres();
    Chambre updateChambres(Chambre chambre);
    void deleteChambre(Long id);
    Chambre GetChambreByEtudiant(Long idEtudiant);


    List<Chambre> getChambresParNomUniversite(String nomUniversite);
}
