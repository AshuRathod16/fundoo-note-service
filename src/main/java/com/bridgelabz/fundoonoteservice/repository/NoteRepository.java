package com.bridgelabz.fundoonoteservice.repository;

import com.bridgelabz.fundoonoteservice.model.NoteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<NoteModel, Long> {
    Optional<NoteModel> findByEmailId(String emailId);

    Optional<NoteModel> findByUserId(Long userId);


    List<NoteModel> findByIsArchive();

    List<NoteModel> findByTrash();

    List<NoteModel> findByIsPin();
}
