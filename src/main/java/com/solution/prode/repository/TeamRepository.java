package com.solution.prode.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.solution.prode.model.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
}