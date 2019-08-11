package com.solution.prode.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solution.prode.services.TeamService;
import com.solution.prode.model.Team;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/private/v1/team")
public class TeamController {
	
    @Autowired
    private TeamService teamService;

    @GetMapping
    public ResponseEntity<List<Team>> findAll() {
        return ResponseEntity.ok(teamService.findAll());
    }

    @PostMapping
    public ResponseEntity<Team> create(@Valid @RequestBody Team team) {
        return ResponseEntity.ok(teamService.save(team));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Team> findById(@PathVariable Long id) {
        Optional<Team> team = teamService.findById(id);
        if (!team.isPresent()) {
            ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(team.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Team> update(@PathVariable Long id, @Valid @RequestBody Team team) {
        if (!teamService.findById(id).isPresent()) {
            ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(teamService.save(team));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Team> delete(@PathVariable Long id) {
        if (!teamService.findById(id).isPresent()) {
            ResponseEntity.badRequest().build();
        }
        teamService.deleteById(id);

        return ResponseEntity.ok().build();
    }
}
