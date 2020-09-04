package br.gov.ma.tce.modelo.fns;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(schema="fns",name="entidade_repasse")
@Data
public class EntidadeRepasse {
	
	@Id
	@Column(name="entidade_repasse_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long entidadeRepasseId;
	

	
	private Integer exercicio;
	private String tipoRepasse;

	
	
	private String uf;
	private String municipio;
	private String cpfCnpj;
	private String razaoSocial;
	private String codigoMunicipioIBGE;
	private String esferaAdministrativa;
	private Double valorTotalUF;
	private Double valorTotalUFNumerico;
	private boolean entidadeSemRepasse;
	private String cpfCnpjFormatado;
	private boolean federal;
	private boolean esferaEstadual;
	private String tipoDirigente;
	
	@OneToMany(cascade= CascadeType.ALL)
	@JoinColumn(name="entidade_repasse_id")
	private List<Repasse> repasses;
	
	
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="entidade_repasse_id")
	private List<Bloco> blocos;


	
	//Censo https://consultafns.saude.gov.br/recursos/municipios/211130/censo
	
	
	
	//https://consultafns.saude.gov.br/recursos/consulta-consolidada/repasse-bloco?ano=2020&coMunicipioIbge=211130&coTipoRepasse=M&count=10&page=1&sgUf=MA
	
	//https://consultafns.saude.gov.br/recursos/consulta-consolidada/entidades?ano=2020&coMunicipioIbge=211130&coTipoRepasse=M&count=10&page=1&sgUf=MA
	
	



	
	
}
