package com.grokonez.jwtauthentication.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grokonez.jwtauthentication.model.Repertoire;


@Repository
public interface IRepertoireRepository extends JpaRepository<Repertoire, Long>{

	public Repertoire findOneById(Long id);
}
