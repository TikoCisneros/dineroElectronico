package dinero.electronico.services;

import java.math.BigDecimal;

import dinero.electronico.model.manager.ManagerCliente;

public class Test {

	public static void main(String[] args) {
		try {
			ManagerCliente m = new ManagerCliente();
			m.generarTransferencia("1003443296", "1004469647", "4666", new BigDecimal(10));
			m.generarTransferencia("1004469647", "1003443296", "2099", new BigDecimal(20));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
