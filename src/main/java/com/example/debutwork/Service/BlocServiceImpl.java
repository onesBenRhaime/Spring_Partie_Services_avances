package com.example.debutwork.Service;

import com.example.debutwork.entity.Bloc;
import com.example.debutwork.entity.Foyer;

import java.util.List;

public interface BlocServiceImpl {
    Bloc addBloc(Bloc bloc);
    List<Bloc> getBlocByCapacite(long capacite);
    List<Bloc> getAllBlocs();
    Bloc updateBloc(Bloc bloc);
    void deleteBloc(Long id);
    void afficherListeBlocs ();

    Bloc affecterChambresABloc(List<Long> numChambres, Long idBloc);


    Bloc affecterBlocAFoyer(Bloc bloc, Foyer foyer);
}
