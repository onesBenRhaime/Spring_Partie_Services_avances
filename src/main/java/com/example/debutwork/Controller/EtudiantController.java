package com.example.debutwork.Controller;

import com.example.debutwork.Service.EtudiantServiceImpl;
import com.example.debutwork.entity.Etudiant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/etudiant")
public class EtudiantController {
    @Autowired
    EtudiantServiceImpl etudiantService;
    @PostMapping("/addEtudiant")
    Etudiant addEtudiant(@RequestBody Etudiant etudiant) {
        return etudiantService.addEtudiant(etudiant);
    }

    @PutMapping("/updateEtudiant")
    Etudiant updateEtudiant(@RequestBody Etudiant etudiant) {
        return etudiantService.updateEtudiant(etudiant);
    }

    @GetMapping("/getAllEtudiants")
    List<Etudiant> getAllEtudiants() {
        return etudiantService.getAllEtudiants();
    }

    @DeleteMapping("/deleteEtudiant/{id}")
    void deleteEtudiant(@PathVariable Long id) {
        etudiantService.deleteEtudiant(id);
    }
}
