package com.solution.prode;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.solution.prode.model.Team;
import com.solution.prode.repository.TeamRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestPropertySource(locations="classpath:application-test.properties")
public class TeamRepositoryIntegrationTest {
 
    @Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private TeamRepository teamRepository;
 
    @Test
    public void whenFindById_thenReturnTeam() {
        Team someTeam = new Team("TeamName");
        entityManager.persist(someTeam);
        entityManager.flush();
     
        // when
        Optional<Team> found = teamRepository.findById(someTeam.getId());
     
        // then
        assertThat(found.get().getName())
          .isEqualTo(someTeam.getName());
    }
 
}