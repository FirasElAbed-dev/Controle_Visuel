
package com.grokonez.jwtauthentication.repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grokonez.jwtauthentication.model.Cheque;



@Repository
public interface IChequeRepository extends JpaRepository<Cheque, Long> {




	public Cheque findOneById(Long id);
	public boolean existsChequeBynumChq(String numChq);

	public List<Cheque> findBynumChq(String numChq);
	public List<Cheque> findByDateTrait(Date dateTrait);
	public List<Cheque> findByMontant(Long montant);

	
	//public List<cheque> findByDateRapportTraitAndApplicationSourceAndFileName (Date dateRapportTrait,String applicationSource );

}
