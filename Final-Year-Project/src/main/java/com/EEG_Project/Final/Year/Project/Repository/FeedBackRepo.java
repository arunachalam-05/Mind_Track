package com.EEG_Project.Final.Year.Project.Repository;

import com.EEG_Project.Final.Year.Project.Model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedBackRepo extends JpaRepository<Feedback, Long> {

}
