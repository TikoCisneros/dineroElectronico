package dinero.electronico.services;

import java.util.List;

import dinero.electronico.model.dao.entities.Transaccion;
import dinero.electronico.model.manager.ManagerDAO;

public class Test {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		try {
			ManagerDAO mngDAO = new ManagerDAO();
			//ManagerCliente m = new ManagerCliente();
			//m.generarTransferencia("1003443296", "1004469647", "4666", new BigDecimal(10));
			//m.generarTransferencia("1004469647", "1003443296", "2099", new BigDecimal(20));
			List<Transaccion> todos = mngDAO.findAll(Transaccion.class, "o.fecha DESC"); 
			for (Transaccion trs : todos) {
				System.out.println(trs.getFecha()+" "+trs.getNroCuenta());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
