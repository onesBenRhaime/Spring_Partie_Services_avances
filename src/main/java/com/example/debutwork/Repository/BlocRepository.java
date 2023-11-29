package com.example.debutwork.Repository;

import com.example.debutwork.entity.Bloc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlocRepository extends JpaRepository<Bloc, Long> {
List<Bloc> findByCapaciteBlocLike(Long capaciteBloc);
    Bloc findByNomBloc(String nomBloc);
}
