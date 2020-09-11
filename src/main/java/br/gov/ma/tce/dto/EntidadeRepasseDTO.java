package br.gov.ma.tce.dto;

import java.util.List;

import br.gov.ma.tce.modelo.fns.Bloco;
import br.gov.ma.tce.modelo.fns.EntidadeRepasse;
import br.gov.ma.tce.modelo.fns.Repasse;
import lombok.Data;

@Data
public class EntidadeRepasseDTO {
	
	private EntidadeRepasse entidadeRepasse;
	
	private List<Repasse> repasses;
	
	private List<Bloco> blocos;

	public EntidadeRepasseDTO(EntidadeRepasse entidadeRepasse, List<Repasse> repasses, List<Bloco> blocos) {
		this.entidadeRepasse = entidadeRepasse;
		this.repasses = repasses;
		this.blocos = blocos;
	}
	
	
	
	
	

}
