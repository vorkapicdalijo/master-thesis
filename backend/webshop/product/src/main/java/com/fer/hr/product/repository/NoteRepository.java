package com.fer.hr.product.repository;

import com.fer.hr.product.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {
}
