package br.gov.ma.tce.modelo.fns;

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

import lombok.Data;

@Entity
@Table(schema="fns",name = "repasse")
@Data
public class Repasse {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="repasse_id")
	private Long repasseId;
	
	@ManyToOne
	@JoinColumn(name="entidade_repasse_id")
	private EntidadeRepasse entidadeRepasse;
	
	private String codigo;
	
	private String nome;
	
	private Double vlTotal;
	
	private Double vlDesconto;
	
	private Double vlLiquido;
	
	@OneToMany(mappedBy="repasse",cascade=CascadeType.ALL,orphanRemoval=true)
	private List<ItemRepasse> repasses;
	
	
}
