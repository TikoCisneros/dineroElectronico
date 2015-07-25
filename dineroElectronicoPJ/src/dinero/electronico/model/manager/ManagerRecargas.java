package dinero.electronico.model.manager;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import dinero.electronico.model.dao.entities.Cliente;
import dinero.electronico.model.dao.entities.Cuenta;
import dinero.electronico.model.dao.entities.Tipotran;
import dinero.electronico.model.dao.entities.Transaccion;
import dinero.electronico.services.Mailer;

public class ManagerRecargas {
	
	private ManagerDAO mngDAO;
	
	public ManagerRecargas() {
		mngDAO =  new ManagerDAO();
	}
	
	/**
	 * Busca una cuenta por numero de cuenta
	 * @param nrocuenta
	 * @return
	 * @throws Exception
	 */
	public Cuenta findClienteByCI(String nrocuenta) throws Exception{
		return (Cuenta) mngDAO.findById(Cuenta.class, nrocuenta);
	} 
	
	/**
	 * Valida el usuario a recargar dinero
	 * @param cedula
	 * @param pass
	 * @return Cliente existente
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Cliente validarUsuario(String cedula, String pass) throws Exception{
		try {
			List<Cliente> listado = (List<Cliente>) mngDAO.findByParam(Cliente.class, "o.cedula", cedula, null);
			if(listado == null || listado.isEmpty()){
				throw new Exception("No se encuentra el usuario."); 
			}
			Cliente u = listado.get(0);
			if (u.getPass().equals(pass)) {//MD5 PASS getMD5(pass)
				return u;
			}else{
				throw new Exception("Usuario o password invalidos");
			}		
		} catch (Exception e) {
			throw new Exception("Error al validar el usuario, "+e.getMessage());
		}
	}
	
	/**
	 * Realiza una recarga de dinero a una cuenta
	 * @param ci
	 * @param recarga
	 * @throws Exception
	 */
	public void recargarDinero(String ci, BigDecimal recarga) throws Exception{
		Cuenta cta = this.findClienteByCI(ci);
		String correo = cta.getCliente().getCorreo();
		BigDecimal saldo = cta.getSaldo();
		cta.setSaldo(saldo.add(recarga));
		mngDAO.actualizar(cta);
		//Tipo de transaccion
		Tipotran tp = (Tipotran) mngDAO.findById(Tipotran.class, 2);
		Date fa = new Date();
		Transaccion trans  = new Transaccion();
		trans.setTipotran(tp);trans.setMonto(recarga);
		trans.setFecha(new Timestamp(fa.getTime()));
		trans.setNroCuenta(cta.getNroCuenta());trans.setNrocDestino(cta.getNroCuenta());
		trans.setSaldoActual(saldo);trans.setSaldoFinal(saldo.add(recarga));
		mngDAO.insertar(trans);
		//Correo de aviso
		Mailer.generateAndSendEmail(correo, "Recarga Exitosa", "Se han acreditado $"+recarga.toString()+" a su cuenta.");
	}
}
