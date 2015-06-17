package dinero.electronico.controller;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import dinero.electronico.model.manager.ManagerAdmin;
import dinero.electronico.model.dao.entities.Cliente;
import dinero.electronico.model.dao.entities.Usuario;


@SessionScoped
@ManagedBean
public class SessionBean implements Serializable {

	/**
	 * ID SERIALIZABLE
	 */
	private static final long serialVersionUID = 1L;
	
	private Usuario session;
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
	public Usuario getSession() {
		return session;
	}
	
	/**
	 * Permite el acceso al sistema
	 * @return direcci�n de p�gina seg�n usuario
	 */
	public String login(){
		String rsp = "";
		try {
			if(getAlias().equals("root") && getPass().equals("root")){
				rsp="/admin/index?faces-redirect=true";
				session = new Usuario();
				session.setNombre("Administrador Aplicacion");session.setApellido("Administrador Aplicacion");
				session.setAlias("root");session.setPass("root");session.setTipousr(mngAdmin.findTipoUSRByID(1));//Administrador
				System.out.println("Usuario ROOT conectado.");
			}else{
				Cliente cli = mngAdmin.findClienteByAliasAndPass(getAlias(), getPass());
				session.setNombre(cli.getNombre());session.setApellido(cli.getApellido());
				session.setAlias(cli.getAlias());session.setPass(cli.getPass());
				session.setTipousr(cli.getTipousr());
				String rol = cli.getTipousr().getTipo().toLowerCase();
				if(rol.equals("administrador")){
					rsp="/admin/index?faces-redirect=true";
				}else if(rol.equals("operador")){
					rsp="/usuario/index?faces-redirect=true";
				}
			}	
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al intentar ingresar al sistema",null));
		}
		return rsp;
	}

	/**
     * M�todo para salir del sistema
     * @return p�gina xhtml
     */
    public String logout(){
    	HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        session.invalidate();
        alias="";pass="";
    	return "/index?faces-redirect=true";
    }
    
    public String registro(){
    	return "/registro?faces-redirect=true";
    }

    /**
     * M�todo para verifiar la existencia de la sesi�n
     * @param rol de usuario
     * @return Clase Usuario
     */
    public static Usuario verificarSession(String rol){
    	HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        SessionBean user = (SessionBean) session.getAttribute("sessionBean");
        if (user==null || user.getSession() == null) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/dineroElectronicoPJ/login.xhtml");
            } catch (IOException ex) {
            	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(),null));
            }
            return null;
        } else {
            if (user.getSession().getTipousr().getTipo().toLowerCase().equals(rol)) {
                return user.getSession();
            } else {
            	
            	String rsp = "";
            	if(rol.equals("administrador")){
    				rsp="/admin/index?faces-redirect=true";
    			}else if(rol.equals("operador")){
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
    
}

