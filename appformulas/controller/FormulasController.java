package org.example.appformulas.controller;

import org.example.appformulas.essence.Formulas;
import org.example.appformulas.essence.repository.FormulaRepository;
import org.example.appformulas.service.FormulaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/app/formulas")
@CrossOrigin(origins = "http://localhost:4200")
public class FormulasController {
    @Autowired
    private FormulaRepository formulaRepository;
        @Autowired
        private FormulaService formulaService;

        @GetMapping("/Математика")
        public List<Formulas> getMathFormulas() {
            return formulaService.getMathFormulas();
        }

        @GetMapping("/Геометрия")
        public List<Formulas> getGeometryFormulas() {
            return formulaService.getGeometryFormulas();
        }

        @GetMapping("/Физика")
        public List<Formulas> getPhysicsFormulas() {
            return formulaService.getPhysicsFormulas();
        }

    @DeleteMapping("/{category}/{id}")
    public void deleteFormula(@PathVariable String category, @PathVariable Long id) {
        formulaService.deleteFormula(category, id);
    }

}
