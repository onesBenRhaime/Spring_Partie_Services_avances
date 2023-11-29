package com.example.debutwork.Service;

import com.example.debutwork.entity.Universite;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UniversiteServiceImpl {
    Universite addUniversite(Universite universite);
    List<Universite> getAllUniversites();
    Universite updateUniversite(Universite universite);
    void deleteUniversite(Long id);

    Universite findUniversitesByChambreId(Long chambreId);

    Universite findUniversitesByIdEtudiant(Long IdEtudiant);


}
