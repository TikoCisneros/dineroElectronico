package dinero.electronico.model.dao.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tipousr database table.
 * 
 */
@Entity
@NamedQuery(name="Tipousr.findAll", query="SELECT t FROM Tipousr t")
public class Tipousr implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_tipo")
	private Integer idTipo;

	private String tipo;

	//bi-directional many-to-one association to Cliente
	@OneToMany(mappedBy="tipousr")
	private List<Cliente> clientes;

	//bi-directional many-to-one association to Usuario
	@OneToMany(mappedBy="tipousr")
	private List<Usuario> usuarios;

	public Tipousr() {
	}

	public Integer getIdTipo() {
		return this.idTipo;
	}

	public void setIdTipo(Integer idTipo) {
		this.idTipo = idTipo;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public List<Cliente> getClientes() {
		return this.clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public Cliente addCliente(Cliente cliente) {
		getClientes().add(cliente);
		cliente.setTipousr(this);

		return cliente;
	}

	public Cliente removeCliente(Cliente cliente) {
		getClientes().remove(cliente);
		cliente.setTipousr(null);

		return cliente;
	}

	public List<Usuario> getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Usuario addUsuario(Usuario usuario) {
		getUsuarios().add(usuario);
		usuario.setTipousr(this);

		return usuario;
	}

	public Usuario removeUsuario(Usuario usuario) {
		getUsuarios().remove(usuario);
		usuario.setTipousr(null);

		return usuario;
	}

}