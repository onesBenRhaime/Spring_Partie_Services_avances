package com.example.debutwork.Controller;

import com.example.debutwork.Service.BlocServiceImpl;
import com.example.debutwork.Service.FoyerServiceImpl;
import com.example.debutwork.entity.Foyer;
import com.example.debutwork.entity.TypeC;
import com.example.debutwork.entity.Universite;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/foyer")
@CrossOrigin(origins = "http://localhost:4200/")
public class FoyerController {
    @Autowired
    FoyerServiceImpl foyerService;
    @PostMapping("/addFoyer")
    Foyer addFoyer(@RequestBody Foyer foyer) {
        return foyerService.addFoyer(foyer);
    }

    @PutMapping("/updateFoyer")
    Foyer updateFoyer(@RequestBody Foyer foyer) {
        return foyerService.updateFoyer(foyer);
    }

    @GetMapping("/getAllFoyers")
    List<Foyer> getAllFoyers() {
        return foyerService.getAllFoyers();
    }

    @DeleteMapping("/deleteFoyer/{id}")
    void deleteFoyer(@PathVariable Long id) {
        foyerService.deleteFoyer(id);
    }

    //valide
    @GetMapping("/getFoyersChambre")
    List<Foyer> getFoyerBychambre(@PathParam("TypeChambre") TypeC TypeChambre) {
        return foyerService.findFoyersByTypeChambre(TypeChambre);
    }

    //valide
    @GetMapping("/getCapaciteFoyerBybloc")
    Long getFoyerBychambre(@PathParam("Idbloc") Long Idbloc) {
        return foyerService.findCapaciteFoyerByIdBloc(Idbloc);
    }


    //valide
    @PostMapping("/affecterFoyerAUniversite")
    Universite affecterFoyerAUniversite(@PathParam("IdFoyer") Long IdFoyer, @PathParam("nomUniversite") String nomUniversite) {
        return foyerService.affecterFoyerAUniversite(IdFoyer,nomUniversite);
    }

}