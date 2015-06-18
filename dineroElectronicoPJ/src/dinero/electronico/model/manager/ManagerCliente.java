package dinero.electronico.model.manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dinero.electronico.model.dao.entities.Cliente;
import dinero.electronico.model.dao.entities.Cuenta;
import dinero.electronico.model.dao.entities.Transaccion;

public class ManagerCliente {
	private ManagerDAO mngDAO;
	
	public ManagerCliente() {
		mngDAO = new ManagerDAO();
	}
	
	/***********************************CUENTA***********************************/
	
	/**
	 * Busca un cliente por ID
	 * @param ci
	 * @return
	 * @throws Exception
	 */
	public Cliente findClienteByCI(String ci) throws Exception{
		return (Cliente) mngDAO.findById(Cliente.class, ci);
	}
	
	/**
	 * Permite cambiar el pass de un cliente
	 * @param ci
	 * @param lastpass
	 * @param newpass
	 * @throws Exception
	 */
	public void cambiarPassCliente(String ci, String lastpass,String newpass)throws Exception{
		Cliente cli = findClienteByCI(ci);
		if(!cli.getPass().equals(lastpass)){
			throw new Exception("El password actual no coincide");
		}else{
			cli.setPass(newpass);
			mngDAO.actualizar(cli);
		}
	}
	
	/***********************************HISTORIAL***********************************/
	
	/**
	 * Devuelve toda las lista de transacciones de un cliente
	 * @param nrocuenta
	 * @return List<Transaccion>
	 */
	@SuppressWarnings("unchecked")
	public List<Transaccion> findTransaccionXCuenta(String nrocuenta){
		List<Transaccion> todos = mngDAO.findAll(Transaccion.class); 
		List<Transaccion> listado = new ArrayList<Transaccion>();
		for (Transaccion trs : todos) {
			if(trs.getNroCuenta().equals(nrocuenta)){
				listado.add(trs);
			}
		}
		return listado;
	}
	
	/***********************************TOKEN***********************************/
	
	/**
	 * Genera un valor de 4 dígitos que es el token para una transaccion
	 * @return valor tipo String
	 */
	public String tokenAleatorio(){
		Random rnd = new Random();
		Integer nrm = rnd.nextInt(9000)+999;
		return nrm.toString();
	}
	
	/**
	 * Ingresa un token a la cuenta
	 * @param nrocuenta
	 * @throws Exception
	 */
	public void ingresarTokenCuenta(String nrocuenta) throws Exception{
		try {
			Cuenta cta = (Cuenta) mngDAO.findById(Cuenta.class, nrocuenta);
			cta.setToken(this.tokenAleatorio());
			mngDAO.actualizar(cta);
		} catch (Exception e) {
			throw new Exception("No se pudo generar token"); 
		}
	}
	
	/***********************************TRANSFERENCIA***********************************/
	
	public void generarTransferencia(String nroCuentaO, String nroCuentaD, String token, BigDecimal costo){
		//Validar existencia cuenta origen
		
		//Validar token cuenta origen
		
		//Revisa valor cuenta origen
		
		//Realiza transferencia para cuenta Origen
		
		//Realiza transferencia para cuenta Destino
	}
}
