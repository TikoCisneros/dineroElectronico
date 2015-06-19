package dinero.electronico.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import dinero.electronico.model.dao.entities.Transaccion;
import dinero.electronico.model.manager.ManagerCliente;

@ViewScoped
@ManagedBean
public class HistorialBean implements Serializable{

	private static final long serialVersionUID = -5228725628173224014L;
	
	private ManagerCliente mngCli;
	private List<Transaccion> listado;
	
	public HistorialBean() {
		mngCli = new ManagerCliente();
	}

	/**
	 * @return the listado
	 */
	public List<Transaccion> getListado() {
		listado = mngCli.findTransaccionXCuenta("1003443296");//nro de sesion
		return listado;
	}
	
	

}
