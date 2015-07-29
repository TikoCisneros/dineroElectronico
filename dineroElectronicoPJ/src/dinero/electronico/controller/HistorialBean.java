package dinero.electronico.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import dinero.electronico.model.dao.entities.Transaccion;
import dinero.electronico.model.dao.entities.Cliente;
import dinero.electronico.model.manager.ManagerCliente;

@ViewScoped
@ManagedBean
public class HistorialBean implements Serializable{

	private static final long serialVersionUID = -5228725628173224014L;
	
	private ManagerCliente mngCli;
	private List<Transaccion> listado;
	private Cliente session;
	private BigDecimal saldoActual;
	
	public HistorialBean() {
		mngCli = new ManagerCliente();
		session = SessionBean.verificarSession("usuario");
	}
	
	
	/**
	 * @return saldo actual
	 */
	public BigDecimal getSaldoActual() {
		try {
			saldoActual = mngCli.saldoActualCuenta(session.getCedula()).setScale(2, RoundingMode.HALF_UP);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return saldoActual;
	}
	
	public Cliente getSession() {
		return session;
	}

	/**
	 * https://github.com/TheCoder4eu/BootsFaces/issues/22
	 * @return the listado
	 */
	public List<Transaccion> getListado() {
		listado = mngCli.findTransaccionXCuenta(getSession().getCedula());//nro de sesion
		return listado;
	}
	
	

}
