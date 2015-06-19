package dinero.electronico.controller;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import dinero.electronico.model.manager.ManagerCliente;

@ViewScoped
@ManagedBean
public class TokenBean implements Serializable{

	private static final long serialVersionUID = 3690425756589803477L;
	
	private ManagerCliente mngCli;
	private String token;
	
	public TokenBean() {
		mngCli = new ManagerCliente();
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
	 * Genera un token para realizar una transaccion
	 * @return ""
	 */
	public String genToken(){
		try {
			setToken(mngCli.ingresarTokenCuenta("1003443296")); //Cambiar por la sesion de usuario
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta" ,e.getMessage()));
		}
		return "";
	}

}
