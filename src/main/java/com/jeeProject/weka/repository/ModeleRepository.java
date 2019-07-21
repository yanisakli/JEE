package com.jeeProject.weka.repository;

import com.jeeProject.weka.model.Modele;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModeleRepository extends JpaRepository<Modele, Long> {
}
