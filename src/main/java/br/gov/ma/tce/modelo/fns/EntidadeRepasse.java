package br.gov.ma.tce.modelo.fns;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(schema="fns",name="entidade_repasse")
@Data
@NamedEntityGraph(name = "EntidadeRepasse.blocos",
attributeNodes = @NamedAttributeNode("blocos")
)
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
	
	@Column(name="data_importacao")
	@Temporal(TemporalType.TIMESTAMP)
	
	private Date dataImportacao;
	
	@JsonIgnore
	@OneToMany(mappedBy="entidadeRepasse",cascade= CascadeType.ALL,orphanRemoval=true)
	private List<Repasse> repasses;
	
	@JsonIgnore
	@OneToMany(mappedBy="entidadeRepasse",cascade = CascadeType.ALL,orphanRemoval=true)
	private List<Bloco> blocos;

	@PrePersist
	public void prePersist() {
		this.dataImportacao =  new Date();
	}

	
	//Censo https://consultafns.saude.gov.br/recursos/municipios/211130/censo
	//https://consultafns.saude.gov.br/recursos/consulta-consolidada/repasse-bloco?ano=2020&coMunicipioIbge=211130&coTipoRepasse=M&count=10&page=1&sgUf=MA
	//https://consultafns.saude.gov.br/recursos/consulta-consolidada/entidades?ano=2020&coMunicipioIbge=211130&coTipoRepasse=M&count=10&page=1&sgUf=MA
	
	



	
	
}
