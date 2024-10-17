package com.kraya.platform.repository;

import com.kraya.platform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link User} entities.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Checks if a user with the given username exists.
     *
     * @param username The username to check.
     * @return True if a user with the given username exists, false otherwise.
     */
    boolean existsByUsername(String username);

    /**
     * Checks if a user with the given email exists.
     *
     * @param email The email to check.
     * @return True if a user with the given email exists, false otherwise.
     */
    boolean existsByEmail(String email);

    /**
     * Finds a user by their username.
     *
     * @param username The username to search for.
     * @return An Optional containing the user if found, or an empty Optional if not found.
     */
    Optional<User> findByUsername(String username);

    /**
     * Finds all users with the given role.
     *
     * @param role The role to search for.
     * @return A list of users with the given role.
     */
    List<User> findByRole(String role);

    /**
     * Finds all users with a username containing the given keyword.
     *
     * @param keyword The keyword to search for.
     * @return A list of users with a username containing the given keyword.
     */
    List<User> findByUsernameContaining(String keyword);

    /**
     * Finds all users with an email containing the given keyword.
     *
     * @param keyword The keyword to search for.
     * @return A list of users with an email containing the given keyword.
     */
    List<User> findByEmailContaining(String keyword);
}