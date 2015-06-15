package dinero.electronico.model.dao.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tipotrans database table.
 * 
 */
@Entity
@Table(name="tipotrans")
@NamedQuery(name="Tipotran.findAll", query="SELECT t FROM Tipotran t")
public class Tipotran implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_tipotrans")
	private Integer idTipotrans;

	private String tipotrans;

	//bi-directional many-to-one association to Transaccion
	@OneToMany(mappedBy="tipotran")
	private List<Transaccion> transaccions;

	public Tipotran() {
	}

	public Integer getIdTipotrans() {
		return this.idTipotrans;
	}

	public void setIdTipotrans(Integer idTipotrans) {
		this.idTipotrans = idTipotrans;
	}

	public String getTipotrans() {
		return this.tipotrans;
	}

	public void setTipotrans(String tipotrans) {
		this.tipotrans = tipotrans;
	}

	public List<Transaccion> getTransaccions() {
		return this.transaccions;
	}

	public void setTransaccions(List<Transaccion> transaccions) {
		this.transaccions = transaccions;
	}

	public Transaccion addTransaccion(Transaccion transaccion) {
		getTransaccions().add(transaccion);
		transaccion.setTipotran(this);

		return transaccion;
	}

	public Transaccion removeTransaccion(Transaccion transaccion) {
		getTransaccions().remove(transaccion);
		transaccion.setTipotran(null);

		return transaccion;
	}

}