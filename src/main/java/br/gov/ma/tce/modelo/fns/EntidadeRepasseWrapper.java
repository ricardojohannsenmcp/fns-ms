package br.gov.ma.tce.modelo.fns;

import lombok.Data;


@Data
public class EntidadeRepasseWrapper {
	
	private Resultado resultado;
	private Integer pagina;
	private Integer itensPorPagina;
	private Integer total;
	private Integer totalPaginas;
	

}
