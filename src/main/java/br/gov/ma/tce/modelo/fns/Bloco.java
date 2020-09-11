package br.gov.ma.tce.modelo.fns;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Entity
@Table(schema="fns",name="bloco")
@Data
public class Bloco implements Serializable{
	
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="bloco_id")
	private Integer blocoId;
	
	private String codigo;
	
	private String descricao;
	
	@Transient
	private String valorTotalGrupoFormatado;
	
	@Transient
	private String valorTotalGrupo2Formatado;
	
	@ManyToOne
	@JoinColumn(name="entidade_repasse_id")
	private EntidadeRepasse entidadeRepasse;
	
	@OneToMany(mappedBy="bloco",cascade = CascadeType.ALL,orphanRemoval=true)
	private List<Grupo>listaGrupo;

}
