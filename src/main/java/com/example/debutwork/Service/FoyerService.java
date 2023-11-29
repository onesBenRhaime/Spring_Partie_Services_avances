package com.example.debutwork.Service;

import com.example.debutwork.Repository.FoyerRepository;
import com.example.debutwork.Repository.UniversiteRepository;
import com.example.debutwork.entity.Bloc;
import com.example.debutwork.entity.Foyer;
import com.example.debutwork.entity.TypeC;
import com.example.debutwork.entity.Universite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@Service
public class FoyerService implements FoyerServiceImpl {
    @Autowired
    FoyerRepository foyerRepository;
    @Autowired
    UniversiteRepository universiteRepository;

    @Autowired
    private BlocService blocService;

    @Override
    public Foyer addFoyer(Foyer foyer) { return foyerRepository.save(foyer); }
    @Override
    public List<Foyer> getAllFoyers() {
        return foyerRepository.findAll();
    }
    @Override
    public Foyer updateFoyer(Foyer foyer) {
        if (foyerRepository.existsById(foyer.getIdFoyer())) {
            return foyerRepository.save(foyer);
        }
        return null;
    }
    @Override
    public void deleteFoyer(Long id) { foyerRepository.deleteById(id); }

    @Override
    public List<Foyer> findFoyersByTypeChambre(TypeC TypeChambre) {
        return foyerRepository.findFoyersByTypeChambre(TypeChambre);
    }

    @Override
    public long findCapaciteFoyerByIdBloc(Long Idbloc) {
        return foyerRepository.findCapaciteFoyerByIdBloc(Idbloc);
    }
    @Override
    public Universite affecterFoyerAUniversite(Long idFoyer, String nomUniversite) {

        Optional<Foyer> foyerOpt = foyerRepository.findById(idFoyer);
        if (foyerOpt.isEmpty()) {
            throw new NoSuchElementException("Foyer non trouvé avec l'ID : " + idFoyer);
        }

        Universite universite = universiteRepository.findByNomUniversite(nomUniversite);
        if (universite == null) {
            throw new NoSuchElementException("Université non trouvée avec le nom : " + nomUniversite);
        }

        Foyer foyer = foyerOpt.get();
        foyer.setUniversite(universite);
        universite.setFoyer(foyer);

        foyerRepository.save(foyer);
        universiteRepository.save(universite);

        return universite;
    }
    @Override
    public Foyer ajouterFoyerEtAffecterAUniversite(Foyer foyer, Long idUniversite) {
        // Rechercher l'université par son ID
        Universite universite = universiteRepository.findById(idUniversite)
                .orElseThrow(() -> new NoSuchElementException("Université non trouvée avec l'ID : " + idUniversite));

        // Affecter l'université au foyer
        foyer.setUniversite(universite);

        // Enregistrer le foyer
        Foyer savedFoyer = foyerRepository.save(foyer);

        // Affecter le foyer aux blocs et les enregistrer
        Set<Bloc> blocs = savedFoyer.getBlocs();
        if (blocs != null) {
            for (Bloc bloc : blocs) {
                bloc.setFoyers(savedFoyer);
                blocService.affecterBlocAFoyer(bloc, savedFoyer);
            }
        }

        return savedFoyer;
    }

    @Override
    public Universite desaffecterFoyerAUniversite(Long idUniversite) {

        Universite universite = universiteRepository.findById(idUniversite)
                .orElseThrow(() -> new NoSuchElementException("Université non trouvée avec l'ID : " + idUniversite));
        universite.setFoyer(null);
        return universiteRepository.save(universite);


    }
}
