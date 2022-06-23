package com.coders.tournament.repositories;

import com.coders.tournament.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByLoginAndPassword(String login, String password);
}
