package br.gov.ma.tce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.ma.tce.modelo.fns.EntidadeRepasse;

public interface EntidadeRepasseRepository extends JpaRepository<EntidadeRepasse, Long>{
	
  //  @EntityGraph(value = "EntidadeRepasse.blocos")
	//public EntidadeRepasse findByEntidadeRepasseId(Long id);
	
	
	public List<EntidadeRepasse> findByExercicio(Integer exercicio);
	
	
	Long countByCodigoMunicipioIBGEAndExercicio(String codigoMunicipioIBGE,Integer exercicio);
	
	Optional<EntidadeRepasse> findByCodigoMunicipioIBGEAndExercicio(String codigoMunicipioIBGE,Integer exercicio);



}
