package com.kraya.platform.repository;

import com.kraya.platform.model.Association;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * AssociationRepository provides methods for accessing association data.
 */
@Repository
public interface AssociationRepository extends JpaRepository<Association, Long> {

    /**
     * Finds an association by email.
     *
     * @param email the email
     * @return optional containing the association if found
     */
    Optional<Association> findByEmail(String email);

    /**
     * Finds all associations by area of focus.
     *
     * @param areaOfFocus the area of focus
     * @return list of associations
     */
    List<Association> findByAreaOfFocus(String areaOfFocus);

    /**
     * Finds an association by registration number.
     *
     * @param registrationNumber the registration number
     * @return optional containing the association if found
     */
    Optional<Association> findByRegistrationNumber(String registrationNumber);
}
