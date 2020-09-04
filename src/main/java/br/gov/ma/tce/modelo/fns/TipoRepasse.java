package br.gov.ma.tce.modelo.fns;

import lombok.Getter;

public enum TipoRepasse {
	
	
	M("Municipal"),E("Estadual");

	
	
	
	private TipoRepasse(String descricao) {
		this.descricao = descricao;
	}

	@Getter
	private String descricao;

}
