package dinero.electronico.controller;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import dinero.electronico.model.manager.ManagerRegistro;
import dinero.electronico.services.Validator;

@ViewScoped
@ManagedBean
public class RegistroBean implements Serializable{
	
	private static final long serialVersionUID = 3350620189769544829L;

	private ManagerRegistro mngReg;
	
	private String cedula; 
	private String nombre; 
	private String apellido; 
	private String telefono; 
	private String correo; 
	private String direccion; 
	private String alias; 
	private String pass;
	private String confpass;
	
	public RegistroBean() {
		mngReg = new ManagerRegistro();
	}

	/**
	 * @return the cedula
	 */
	public String getCedula() {
		return cedula;
	}

	/**
	 * @param cedula the cedula to set
	 */
	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the apellido
	 */
	public String getApellido() {
		return apellido;
	}

	/**
	 * @param apellido the apellido to set
	 */
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	/**
	 * @return the telefono
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * @param telefono the telefono to set
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * @return the correo
	 */
	public String getCorreo() {
		return correo;
	}

	/**
	 * @param correo the correo to set
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}

	/**
	 * @return the direccion
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * @param direccion the direccion to set
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
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
	 * @return the confpass
	 */
	public String getConfpass() {
		return confpass;
	}

	/**
	 * @param confpass the confpass to set
	 */
	public void setConfpass(String confpass) {
		this.confpass = confpass;
	}
	
	/**
	 * Cancelar registro
	 * @return
	 */
	public String cancelar(){
		setCedula("");setNombre("");setApellido("");setCedula("");setTelefono("");
		setCorreo("");setDireccion("");setPass("");setConfpass("");
		return "index?faces-redirect=true";
	}
	
	/**
	 * Permite el registro de una nueva cuenta de dinero electrónico
	 * @return
	 */
	public String registrarse(){
		try {
			if(mngReg.aliasExiste(alias)){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Alias existente" ,"Use otro alias alternativo"));
			}else if(!getPass().equals(getConfpass())){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Password" ,"Tanto la contrasenia como su confirmacion deben ser iguales"));
			/*}else if(!Validator.validarCI(cedula)){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Cedula" ,"Ingrese un numero de cedula valido"));*/
			}else{
				mngReg.registrarClienteCuenta(cedula, nombre, apellido, telefono, correo, direccion, alias, confpass);
				setCedula("");setNombre("");setApellido("");setCedula("");setTelefono("");
				setCorreo("");setDireccion("");setPass("");setConfpass("");
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro correcto" ,"Ya puede usar el servicio, realice una recarga"));
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al intentar registrarse" ,e.getMessage()));
		}
		return "";
	}
	
}
