package com.example.debutwork.Service;


import com.example.debutwork.entity.Etudiant;

import java.util.List;

public interface EtudiantServiceImpl {
    Etudiant addEtudiant(Etudiant etudiant);
    List<Etudiant> getAllEtudiants();
    Etudiant updateEtudiant(Etudiant etudiant);
    void deleteEtudiant(Long id);
}
