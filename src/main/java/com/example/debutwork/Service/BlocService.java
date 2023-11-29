package com.example.debutwork.Service;

import com.example.debutwork.Repository.BlocRepository;
import com.example.debutwork.Repository.ChambreRepository;
import com.example.debutwork.Repository.FoyerRepository;
import com.example.debutwork.entity.Bloc;
import com.example.debutwork.entity.Chambre;
import com.example.debutwork.entity.Foyer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class BlocService implements BlocServiceImpl{
    @Autowired
    BlocRepository blocRepository;
    @Autowired
    ChambreRepository chambreRepository;
    @Autowired
    FoyerRepository foyerRepository;
    @Override
    public Bloc addBloc(Bloc bloc) {
    return blocRepository.save(bloc);
    }
    @Override
    public List<Bloc> getBlocByCapacite(long capacite) {
        return blocRepository.findByCapaciteBlocLike(capacite);
    }
    @Override
    public List<Bloc> getAllBlocs() {
        return blocRepository.findAll();
    }
    @Override
    public Bloc updateBloc(Bloc bloc) {

        if (blocRepository.existsById(bloc.getIdBloc())) {
            return blocRepository.save(bloc);
        }
        return null ;
    }
    @Override
    public void deleteBloc(Long id) {
    blocRepository.deleteById(id);
    }


    @Override
    @Scheduled(fixedRate = 30000)
    public void afficherListeBlocs() {
      /*  // Récupère la liste des blocs
        List<Bloc> listeBlocs = blocRepository.findAll();
        // Affiche la liste des blocs
        System.out.println("Liste des blocs chaque 30 secondes :");
        for (Bloc bloc : listeBlocs) {
            log.info(bloc.toString());
        }*/
    }

    @Override
    public Bloc affecterChambresABloc(List<Long> numChambres, Long idBloc) {
           Optional<Bloc> blocOpt = blocRepository.findById(idBloc);
        if (blocOpt.isEmpty()) {
            throw new NoSuchElementException ("Bloc non trouvé avec l'ID : " + idBloc);
        }
        Set<Chambre> chambres = chambreRepository.findByNumeroChambreIn(numChambres);
        if (chambres.size() != numChambres.size()) {
            throw new NoSuchElementException ("Certaines chambres n'ont pas été trouvées.");
        }
        Bloc bloc = blocOpt.get();
        bloc.getChambres().addAll(chambres);

        for (Chambre chambre : chambres) {
            chambre.setBloc(bloc);
        }
         blocRepository.save(bloc);
        return bloc;
    }


    @Override
    public Bloc affecterBlocAFoyer(Bloc bloc, Foyer foyer) {

        bloc.setFoyers(foyer);

        Bloc savedBloc = blocRepository.save(bloc);

        Set<Bloc> blocs = foyer.getBlocs();
        blocs.add(savedBloc);
        foyer.setBlocs(blocs);
        foyerRepository.save(foyer);

        return savedBloc;
    }
}








