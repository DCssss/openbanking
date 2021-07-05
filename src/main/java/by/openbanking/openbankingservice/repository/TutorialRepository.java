package by.openbanking.openbankingservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import by.openbanking.openbankingservice.model.Tutorial;

public interface TutorialRepository extends JpaRepository<Tutorial, Long> {
    List<Tutorial> findByPublished(boolean published);

    List<Tutorial> findByTitleContaining(String title);
}