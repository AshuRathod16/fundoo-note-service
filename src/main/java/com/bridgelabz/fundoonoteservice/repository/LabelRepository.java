package com.bridgelabz.fundoonoteservice.repository;

import com.bridgelabz.fundoonoteservice.model.LabelModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LabelRepository extends JpaRepository<LabelModel, Long> {
    LabelModel findByLabelName(String labelName);
}
