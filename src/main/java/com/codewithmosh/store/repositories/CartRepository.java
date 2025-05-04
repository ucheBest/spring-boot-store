package com.codewithmosh.store.repositories;

import com.codewithmosh.store.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {
//    @Query(value = "SELECT id, date_created from carts c where bin_to_uuid(c.id) = :uuid ", nativeQuery = true)
//    Optional<Cart> findByUuid(@Param("uuid") String uuid);
//
//    @Transactional
//    @Modifying
//    @Query(value = "insert into carts (id, date_created) values (uuid_to_bin(uuid()), :dateCreated)", nativeQuery = true)
//    Cart saveByUuid(@Param("dateCreated") LocalDate dateCreated);
}