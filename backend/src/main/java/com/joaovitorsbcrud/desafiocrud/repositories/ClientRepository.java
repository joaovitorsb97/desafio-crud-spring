package com.joaovitorsbcrud.desafiocrud.repositories;

import com.joaovitorsbcrud.desafiocrud.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}
