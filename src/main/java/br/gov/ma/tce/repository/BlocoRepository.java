package br.gov.ma.tce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.gov.ma.tce.modelo.fns.Bloco;

public interface BlocoRepository extends JpaRepository<Bloco, Long>{
	
	@Query("select b from EntidadeRepasse e  join e.blocos b left join fetch b.listaGrupo g left join  g.listaGrupo c where e.entidadeRepasseId =:entidadeRepasseId  ")
	public List<Bloco> findByEntidadeRepasse(@Param("entidadeRepasseId") Long entidadeRepasseId);

}
