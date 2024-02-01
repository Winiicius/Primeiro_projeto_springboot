package com.projeto.akross.akrosssquads.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.akross.akrosssquads.models.Squad;

public interface SquadRepository extends JpaRepository<Squad, Long> {
    Squad findById(long id);
}
