package dinero.electronico.model.manager;

import java.math.BigDecimal;
import java.util.List;

import dinero.electronico.model.dao.entities.Cliente;
import dinero.electronico.model.dao.entities.Cuenta;
import dinero.electronico.model.dao.entities.Tipousr;

public class ManagerRegistro {
	
	private ManagerDAO mnDAO;
	
	public ManagerRegistro() {
		mnDAO = new ManagerDAO();
	}
	
	/**
	 * Permite conocer si el alias existe o no
	 * @param alias
	 * @return true o false
	 */
	@SuppressWarnings("unchecked")
	public boolean aliasExiste(String alias){	
		boolean rsp = false;
		List<Cliente> lista = mnDAO.findAll(Cliente.class);
		for (Cliente cliente : lista) {
			if(cliente.getAlias().equals(alias)){
				rsp = true;
			}
		}
		return rsp;
	}
	
	/**
	 * Cliente por ci
	 * @param cedula
	 * @return Cliente existente
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Cliente clienteByCI(String cedula) throws Exception{
		try {
			List<Cliente> listado = (List<Cliente>) mnDAO.findByParam(Cliente.class, "o.cedula", cedula, null);
			if(listado == null || listado.isEmpty()){
				throw new Exception("No se encuentra el usuario."); 
			}
			Cliente u = listado.get(0);
			return u;	
		} catch (Exception e) {
			throw new Exception("Error al validar el usuario, "+e.getMessage());
		}
	}
	
	/**
	 * Registra un nuevo cliente
	 * @param cedula
	 * @param nombre
	 * @param apellido
	 * @param telefono
	 * @param correo
	 * @param direccion
	 * @param alias
	 * @param pass
	 * @throws Exception
	 */
	public void registrarClienteCuenta(String cedula,String nombre, String apellido, 
			String telefono, String correo, String direccion, String alias, String pass) throws Exception{
		//Registra Cliente
		Cliente cli = new Cliente();
		cli.setCedula(cedula);cli.setNombre(nombre);cli.setApellido(apellido);cli.setAlias(alias);
		cli.setPass(pass);cli.setCorreo(correo);cli.setDireccion(direccion);cli.setTelefono(telefono);
		cli.setTipousr((Tipousr) mnDAO.findById(Tipousr.class, 2));
		mnDAO.insertar(cli);
		//Registra Cuenta (Monto inicial 0)
		Cuenta cta = new Cuenta();
		cta.setNroCuenta(cedula);cta.setCliente(this.clienteByCI(cedula));
		cta.setSaldo(new BigDecimal(0));
		mnDAO.insertar(cta);
	}
}
