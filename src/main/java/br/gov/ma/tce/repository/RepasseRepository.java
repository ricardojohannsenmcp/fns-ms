package br.gov.ma.tce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.gov.ma.tce.modelo.fns.Repasse;

public interface RepasseRepository extends JpaRepository<Repasse,Long>{
	
	
	@Query("select distinct r from EntidadeRepasse e  join  e.repasses r left join fetch r.repasses i  where e.entidadeRepasseId =:entidadeRepasseId  ")
	List<Repasse>findByEntidadeRepasse(@Param("entidadeRepasseId") Long entidadeRepasseId);
	
	
	


}
