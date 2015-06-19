package dinero.electronico.controller;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import dinero.electronico.model.dao.entities.Cliente;
import dinero.electronico.model.manager.ManagerCliente;

@ViewScoped
@ManagedBean
public class TransferBean implements Serializable{

	private static final long serialVersionUID = -5489595825791589803L;
	
	private ManagerCliente mngCli;
	private String nroCuentaO; 
	private String token; 
	private BigDecimal costo;
	
	private Cliente sesion;
	
	public TransferBean() {
		mngCli = new ManagerCliente();
		sesion = SessionBean.verificarSession("usuario");
	}

	/**
	 * @return the nroCuentaO
	 */
	public String getNroCuentaO() {
		return nroCuentaO;
	}

	/**
	 * @param nroCuentaO the nroCuentaO to set
	 */
	public void setNroCuentaO(String nroCuentaO) {
		this.nroCuentaO = nroCuentaO;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the costo
	 */
	public BigDecimal getCosto() {
		return costo;
	}

	/**
	 * @param costo the costo to set
	 */
	public void setCosto(BigDecimal costo) {
		this.costo = costo;
	}

	/**
	 * @return the sesion
	 */
	public Cliente getSesion() {
		return sesion;
	}
	
	/**
	 * Permite realizar una transferencia
	 * @return ""
	 */
	public String transferir(){
		try {
			mngCli.generarTransferencia(getNroCuentaO(), sesion.getCedula(), getToken(), getCosto());
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta" ,e.getMessage()));
		}
		return "";
	}
}