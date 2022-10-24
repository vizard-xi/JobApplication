package com.example.application.repository;

import com.example.application.model.JobRow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<JobRow, Long> {

    List<JobRow> findJobRowsByJobState(String jobState);

}
