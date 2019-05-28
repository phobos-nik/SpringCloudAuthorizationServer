package edu.practice.authorizationServer.model.repository;

import edu.practice.authorizationServer.model.entity.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<ApplicationUser, UUID> {
}
