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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(schema="fns",name="bloco")
@Data
public class Bloco implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="bloco_id")
	private Integer blocoId;
	
	private String codigo;
	private String descricao;
	private String valorTotalGrupoFormatado;
	private String valorTotalGrupo2Formatado;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="bloco_id")
	private List<Grupo>listaGrupo;

}
