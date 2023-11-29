package com.example.debutwork.Service;

import com.example.debutwork.Repository.ChambreRepository;
import com.example.debutwork.Repository.UniversiteRepository;
import com.example.debutwork.entity.Chambre;
import com.example.debutwork.entity.Universite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ChambreService implements ChambreServiceImpl {
    @Autowired
    ChambreRepository chambreRepository;
    @Autowired
    private UniversiteRepository universiteRepository;
    @Override
    public Chambre addChambre(Chambre chambre) {
        return chambreRepository.save(chambre);
    }
    @Override
    public List<Chambre> getAllChambres() {
        return chambreRepository.findAll();
    }
    @Override
    public Chambre updateChambres(Chambre chambre) {
        if (chambreRepository.existsById(chambre.getIdChambre())) {
            return chambreRepository.save(chambre);
        }
        return null ;
    }
    @Override
    public void deleteChambre(Long id) {
        chambreRepository.deleteById(id);
    }

    @Override
    public Chambre GetChambreByEtudiant(Long idEtudiant) {
        return chambreRepository.findChambreByEtudiantId(idEtudiant);
    }
    @Override
    public List<Chambre> getChambresParNomUniversite(String nomUniversite) {
        Universite universite = universiteRepository.findByNomUniversite(nomUniversite);

        if (universite != null) {
            List<Chambre> chambres = universite.getBlocs().stream()
                    .flatMap(bloc -> bloc.getChambres().stream())
                    .toList();

            return chambres;
        } else {
            throw new NoSuchElementException("Aucune université trouvée avec le nom : " + nomUniversite);
        }
    }
}
