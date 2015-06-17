package dinero.electronico.controller;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import dinero.electronico.model.dao.entities.Cliente;
import dinero.electronico.model.manager.ManagerRecargas;

@ViewScoped
@ManagedBean
public class RecargaBean implements Serializable{

	private static final long serialVersionUID = 6826173272668161760L;
	
	private ManagerRecargas mngRec;
	private String ci;
	private String pass;
	private BigDecimal monto;
	
	public RecargaBean() {
		mngRec = new ManagerRecargas();
	}

	/**
	 * @return the ci
	 */
	public String getCi() {
		return ci;
	}

	/**
	 * @param ci the ci to set
	 */
	public void setCi(String ci) {
		this.ci = ci;
	}

	/**
	 * @return the pass
	 */
	public String getPass() {
		return pass;
	}

	/**
	 * @param pass the pass to set
	 */
	public void setPass(String pass) {
		this.pass = pass;
	}

	/**
	 * @return the monto
	 */
	public BigDecimal getMonto() {
		return monto;
	}

	/**
	 * @param monto the monto to set
	 */
	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}
	
	/**
	 * Permiter realizar una recarga
	 * @return
	 */
	public String recargar(){
		try {
			if(getMonto()==null || getMonto().intValue()<=0){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Campo Monto","Valor menor a 0"));
			}else{
				Cliente usr = mngRec.validarUsuario(getCi(), getPass());
				mngRec.recargarDinero(usr.getCedula(), getMonto());
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Proceso Correcto","Recarga realizada"));
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al intentar recargar" ,e.getMessage()));
		}
		return "";
	}

}
