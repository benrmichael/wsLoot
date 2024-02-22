package com.loot.server.repositories;

import com.loot.server.api.domain.entity.Player;
import org.springframework.data.repository.CrudRepository;

public interface PlayerRepository extends CrudRepository<Player, Long> {

}
