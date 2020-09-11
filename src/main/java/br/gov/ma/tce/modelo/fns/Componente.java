package br.gov.ma.tce.modelo.fns;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;


@Entity
@Table(schema="fns",name="componente")
@Data
public class Componente implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="componente_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer componenteId;
	
	
	@ManyToOne
	@JoinColumn(name="grupo_id")
	private Grupo grupo;
	
	private String coGrupo;
	private String noGrupo;
	private String coBloco;
	private String noBloco;
	private String coComponente;
	private String noComponente;
	private String dsProgramaFundo;
	private String descricao;
	private BigDecimal valorJaneiro;
	private BigDecimal valorFevereiro;
	private BigDecimal valorMarco;
	private BigDecimal valorAbril;
	private BigDecimal valorMaio;
	private BigDecimal valorJunho;
	private BigDecimal valorJulho;
	private BigDecimal valorAgosto;
	private BigDecimal valorSetembro;
	private BigDecimal valorOutubro;
	private BigDecimal valorNovembro;
	private BigDecimal valorDezembro;
	private BigDecimal valorTotal;
	private String valorJulhoFormatado;
	private String valorAgostoFormatado;
	private String valorSetembroFormatado;
	private String valorOutubroFormatado;
	private String valorNovembroFormatado;
	private String valorDezembroFormatado;
	private String valorSaldoFormatado;
	private String valorTotalFormatado;
	private String valorJaneiroFormatado;
	private String valorFevereiroFormatado;
	private String valorMarcoFormatado;
	private String valorAbrilFormatado;
	private String valorMaioFormatado;
	private String valorJunhoFormatado;

}
