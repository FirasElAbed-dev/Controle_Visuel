package com.grokonez.jwtauthentication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grokonez.jwtauthentication.model.ParamCnxDB;



@Repository
public interface IParamCnxRepository extends JpaRepository<ParamCnxDB, Long> {

	public List<ParamCnxDB> findAll();
}
