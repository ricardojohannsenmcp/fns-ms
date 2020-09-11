package br.gov.ma.tce.modelo.fns;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(schema="fns",name="grupo")
@Data
public class Grupo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="grupo_id")
	private Integer grupoId;

	private String codigo;
	private String descricao;
	private String valorTotalGrupoFormatado;
	private String valorTotalGrupo2Formatado;
	
	@ManyToOne
	@JoinColumn(name="bloco_id")
	private Bloco bloco;
	
	@OneToMany(mappedBy="grupo",cascade = CascadeType.ALL,fetch=FetchType.EAGER,orphanRemoval=true)
	private List<Componente>listaGrupo;
	
}
