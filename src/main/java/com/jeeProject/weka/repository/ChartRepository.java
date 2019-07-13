package com.jeeProject.weka.repository;

import com.jeeProject.weka.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GraphRepository extends JpaRepository<User, Long> {
}
