package com.EEG_Project.Final.Year.Project.Repository;

import com.EEG_Project.Final.Year.Project.Model.EEG_Data;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EEG_Date_Repo extends JpaRepository<EEG_Data, Long> {
    Optional<EEG_Data> findTopByUserIdOrderByTimestampDesc(Long userId);
    List<EEG_Data> findByUserId(Long userId);

}
