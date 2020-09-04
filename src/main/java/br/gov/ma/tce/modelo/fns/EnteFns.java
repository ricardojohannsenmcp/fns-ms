package br.gov.ma.tce.modelo.fns;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(schema="fns",name="ente_fns")
@Data
public class EnteFns {
	
	@Id
	private Integer coMunicipioIbge;
	private String noMunicipio;
	private String sgUf;
	private String registroAtivo;
	private String stCapital;
	private String id;

}
