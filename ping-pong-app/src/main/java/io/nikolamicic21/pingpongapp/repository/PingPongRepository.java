package io.nikolamicic21.pingpongapp.repository;

import io.nikolamicic21.pingpongapp.model.PingPong;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PingPongRepository extends CrudRepository<PingPong, Long> {

    String TITLE = "pingpong";

    Optional<PingPong> findByTitle(String title);

}
