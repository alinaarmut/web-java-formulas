package org.example.appformulas.essence.repository;

import org.example.appformulas.essence.Formulas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormulaRepository extends JpaRepository<Formulas, Long> {
    List<Formulas> findByType(String type);
    void deleteById(Long id);
    Formulas findByTypeAndId(String type, Long id);
}
