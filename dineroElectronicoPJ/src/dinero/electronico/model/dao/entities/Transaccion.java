package dinero.electronico.model.dao.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the transaccion database table.
 * 
 */
@Entity
@NamedQuery(name="Transaccion.findAll", query="SELECT t FROM Transaccion t")
public class Transaccion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TRANSACCION_IDTRANS_GENERATOR", sequenceName="SEC_TRANSACCION", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TRANSACCION_IDTRANS_GENERATOR")
	@Column(name="id_trans")
	private long idTrans;

	private Timestamp fecha;

	private BigDecimal monto;

	@Column(name="nro_cuenta")
	private String nroCuenta;

	@Column(name="nroc_destino")
	private String nrocDestino;

	@Column(name="saldo_actual")
	private BigDecimal saldoActual;

	@Column(name="saldo_final")
	private BigDecimal saldoFinal;

	//bi-directional many-to-one association to Tipotran
	@ManyToOne
	@JoinColumn(name="id_tipotrans")
	private Tipotran tipotran;

	public Transaccion() {
	}

	public long getIdTrans() {
		return this.idTrans;
	}

	public void setIdTrans(long idTrans) {
		this.idTrans = idTrans;
	}

	public Timestamp getFecha() {
		return this.fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}

	public BigDecimal getMonto() {
		return this.monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public String getNroCuenta() {
		return this.nroCuenta;
	}

	public void setNroCuenta(String nroCuenta) {
		this.nroCuenta = nroCuenta;
	}

	public String getNrocDestino() {
		return this.nrocDestino;
	}

	public void setNrocDestino(String nrocDestino) {
		this.nrocDestino = nrocDestino;
	}

	public BigDecimal getSaldoActual() {
		return this.saldoActual;
	}

	public void setSaldoActual(BigDecimal saldoActual) {
		this.saldoActual = saldoActual;
	}

	public BigDecimal getSaldoFinal() {
		return this.saldoFinal;
	}

	public void setSaldoFinal(BigDecimal saldoFinal) {
		this.saldoFinal = saldoFinal;
	}

	public Tipotran getTipotran() {
		return this.tipotran;
	}

	public void setTipotran(Tipotran tipotran) {
		this.tipotran = tipotran;
	}

}