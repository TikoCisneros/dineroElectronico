package dinero.electronico.controller;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import dinero.electronico.model.manager.ManagerAdmin;
import dinero.electronico.model.dao.entities.Cliente;


@SessionScoped
@ManagedBean
public class SessionBean implements Serializable {

	/**
	 * ID SERIALIZABLE
	 */
	private static final long serialVersionUID = 1L;
	
	private Cliente session;
	private ManagerAdmin mngAdmin;
	
	private String alias;
	private String pass;
	
	public SessionBean(){
		mngAdmin = new ManagerAdmin();
	}

	/**
	 * @return the alias
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * @param alias the alias to set
	 */
	public void setAlias(String alias) {
		this.alias = alias;
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
	 * @return the session
	 */
	public Cliente getSession() {
		return session;
	}
	
	/**
	 * Permite el acceso al sistema
	 * @return dirección de página según usuario
	 */
	public String login(){
		String rsp = "";
		try {
			session = mngAdmin.findClienteByAliasAndPass(getAlias(), getPass());
			String rol = session.getTipousr().getTipo().toLowerCase();
			if(rol.equals("usuario")){
				rsp="/usuario/index?faces-redirect=true";
			}	
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ingresar al sistema?",e.getMessage()));
		}
		return rsp;
	}

	/**
     * Método para salir del sistema
     * @return página xhtml
     */
    public String logout(){
    	HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        session.invalidate();
        alias="";pass="";
    	return "/index?faces-redirect=true";
    }
    
    /**
     * Registrarse
     * @return
     */
    public String registro(){
    	return "registro?faces-redirect=true";
    }

    /**
     * Método para verifiar la existencia de la sesión
     * @param rol de usuario
     * @return Clase Usuario
     */
    public static Cliente verificarSession(String rol){
    	HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        SessionBean user = (SessionBean) session.getAttribute("sessionBean");
        if (user==null || user.getSession() == null) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/dineroElectronicoPJ/index.xhtml");
            } catch (IOException ex) {
            	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(),null));
            }
            return null;
        } else {
            if (user.getSession().getTipousr().getTipo().toLowerCase().equals(rol)) {
            	return user.getSession();
            } else {
            	
            	String rsp = "";
            	if(rol.equals("usuario")){
    				rsp="/usuario/index?faces-redirect=true";
    			}
            	
                try {
                	FacesContext.getCurrentInstance().getExternalContext().redirect("/dineroElectronicoPJ"+rsp);
                } catch (IOException ex) {
                	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(),null));
                }
                return null;
            }
        }
    }
    
    /**
     * Mantiene una sesion activa p:poll primefaces
     */
    public void keepUserSessionAlive() {
	    FacesContext context = FacesContext.getCurrentInstance();
	    HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
	    request.getSession();
	    System.out.print("Tiempo de expiracion---------------");
	}

    
}

