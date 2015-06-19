package dinero.electronico.controller;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import dinero.electronico.model.dao.entities.Cliente;
import dinero.electronico.model.manager.ManagerCliente;

@ViewScoped
@ManagedBean
public class CuentaBean implements Serializable {

	private static final long serialVersionUID = 8838149352975057583L;
	
	private ManagerCliente mngCli;
	private String cedula; 
	private String nombre; 
	private String apellido; 
	private String telefono; 
	private String correo; 
	private String direccion;  
	private String pass;
	private String npass;
	private String nconfpass;
	
	private Cliente sesion;
	
	public CuentaBean() {
		mngCli = new ManagerCliente();
		sesion = SessionBean.verificarSession("usuario");
		cargarDatos();
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
	 * @return the nconfpass
	 */
	public String getNconfpass() {
		return nconfpass;
	}

	/**
	 * @param confpass the nconfpass to set
	 */
	public void setNconfpass(String nconfpass) {
		this.nconfpass = nconfpass;
	}
	
	/**
	 * @return the npass
	 */
	public String getNpass() {
		return npass;
	}

	/**
	 * @param npass the npass to set
	 */
	public void setNpass(String npass) {
		this.npass = npass;
	}

	/**
	 * @return the sesion
	 */
	public Cliente getSesion() {
		return sesion;
	}
	
	/**
	 * Carga los datos del Usuario conectado
	 */
	public void cargarDatos(){
		setCedula(sesion.getCedula());setNombre(sesion.getNombre());setApellido(sesion.getApellido());
        setTelefono(sesion.getTelefono());setCorreo(sesion.getCorreo());setDireccion(sesion.getDireccion());		
	}
	
	/**
	 * Permite cambiar de contraseña
	 * @return ""
	 */
	public String cambiarPass(){
		try {
			if(getPass().isEmpty() || getPass()==null){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta" ,"Campo pass vacio"));
			}else if(getNpass().isEmpty() || getNpass()==null || getNconfpass().isEmpty() || getNconfpass()==null){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta" ,"Campos de nueva pass vacios"));
			}else if(!getNpass().equals(getNconfpass())){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta" ,"La nueva pass como su confirmacion deben ser iguales"));
			}else{
				mngCli.cambiarPassCliente(getCedula(), getPass(), getNpass());
				setPass("");setNconfpass("");setNpass("");
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cambio correcto" ,"Ya posee nueva password"));
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta" ,e.getMessage()));
		}
		return "";
	}
}
