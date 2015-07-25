package dinero.electronico.model.manager;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import dinero.electronico.model.dao.entities.Cliente;
import dinero.electronico.model.dao.entities.Cuenta;
import dinero.electronico.model.dao.entities.Tipotran;
import dinero.electronico.model.dao.entities.Transaccion;
import dinero.electronico.services.Mailer;

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
	public String ingresarTokenCuenta(String nrocuenta) throws Exception{
		try {
			Cuenta cta = (Cuenta) mngDAO.findById(Cuenta.class, nrocuenta);
			String token = this.tokenAleatorio();
			cta.setToken(token);
			mngDAO.actualizar(cta);
			return token;
		} catch (Exception e) {
			throw new Exception("Error: "+e.getMessage()); 
		}
	}
	
	/**
	 * Devuelve token actual
	 * @param nrocuenta
	 * @return
	 * @throws Exception
	 */
	public String cargarTokenCuenta(String nrocuenta) throws Exception{
		try {
			Cuenta cta = (Cuenta) mngDAO.findById(Cuenta.class, nrocuenta);
			String token = cta.getToken();
			return token;
		} catch (Exception e) {
			throw new Exception("Error: "+e.getMessage()); 
		}
	}
	
	/***********************************TRANSFERENCIA***********************************/
	
	/**
	 * Realiza una transferencia de dinero
	 * @param nroCuentaO cuenta del Cliente quien reduce su monto
	 * @param nroCuentaD cuenta del Cliente quien aumenta su monto
	 * @param token validacion de seguridad de cuenta Origen
	 * @param costo dinero a transferir
	 * @throws Exception
	 */
	public void generarTransferencia(String nroCuentaO, String nroCuentaD, String token, BigDecimal costo) throws Exception{
		try {
			Cuenta cta = (Cuenta) mngDAO.findById(Cuenta.class, nroCuentaO);
			Cuenta ctaf = (Cuenta) mngDAO.findById(Cuenta.class, nroCuentaD);
			String mailo = cta.getCliente().getCorreo();
			String maild = cta.getCliente().getCorreo();
			//Validar existencia cuenta origen
			if(cta == null || ctaf == null){
				throw new Exception("No existe la cuenta"); 
			}
			//Validar token cuenta origen
			if(!cta.getToken().equals(token)){
				throw new Exception("Token incorrecto"); 
			}
			//Revisa valor cuenta origen
			if(cta.getSaldo().compareTo(costo) == -1){
				throw new Exception("El valor supera el saldo de la cuenta");
			}
			//Tipo de transaccion
			Tipotran tp = (Tipotran) mngDAO.findById(Tipotran.class, 2);
			Date fa = new Date();
			/**Realiza transferencia para cuenta Origen, reduce valor**/
			//Reducir saldo
			BigDecimal oSaldo = cta.getSaldo().subtract(costo);
			cta.setSaldo(oSaldo);
			mngDAO.actualizar(cta);
			//Guardar transaccion
			Transaccion trans  = new Transaccion();
			trans.setTipotran(tp);trans.setMonto(costo);
			trans.setFecha(new Timestamp(fa.getTime()));
			trans.setNroCuenta(nroCuentaO);trans.setNrocDestino(nroCuentaD);
			trans.setSaldoActual(cta.getSaldo());trans.setSaldoFinal(oSaldo);
			mngDAO.insertar(trans);
			/**Realiza transferencia para cuenta Destino, aumenta valor**/
			//Aumentar saldo
			BigDecimal fSaldo = ctaf.getSaldo().add(costo);
			ctaf.setSaldo(fSaldo);
			mngDAO.actualizar(ctaf);
			//Guardar transaccion
			trans.setNroCuenta(nroCuentaD);trans.setNrocDestino(nroCuentaO);
			trans.setSaldoActual(ctaf.getSaldo());trans.setSaldoFinal(fSaldo);
			mngDAO.insertar(trans);
			//MENSAJES
			Mailer.generateAndSendEmail(maild, "Transferencia exitosa", "Se ha acreditado $"+costo.toString()+" de su cuenta.");
			Mailer.generateAndSendEmail(mailo, "Transferencia exitosa", "Se ha debitado $"+costo.toString()+" de su cuenta.");
		} catch (Exception e) {
			throw new Exception("Error: "+e.getMessage()); 
		}
	}
}
