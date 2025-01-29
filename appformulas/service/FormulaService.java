package org.example.appformulas.service;

import org.example.appformulas.essence.Formulas;
import org.example.appformulas.essence.repository.FormulaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormulaService {

    @Autowired
    private FormulaRepository formulaRepository;

    public List<Formulas> getMathFormulas() {
        return formulaRepository.findByType("Математика");
    }

    public List<Formulas> getGeometryFormulas() {
        return formulaRepository.findByType("Геометрия");
    }

    public List<Formulas> getPhysicsFormulas() {
        return formulaRepository.findByType("Физика");
    }

    public void deleteFormula(String category, Long id) {
        Formulas formulaToDelete = formulaRepository.findByTypeAndId(category, id);
        if (formulaToDelete != null) {
            formulaRepository.delete(formulaToDelete);
        } else {
            throw new IllegalArgumentException("Формула не найдена для данной категории и id");
        }
    }
}