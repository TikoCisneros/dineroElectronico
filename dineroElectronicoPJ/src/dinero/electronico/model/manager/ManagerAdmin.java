package dinero.electronico.model.manager;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import dinero.electronico.model.dao.entities.Tipousr;
import dinero.electronico.model.dao.entities.Usuario;



public class ManagerAdmin {
	
	private ManagerDAO mngDAO;
	
	public ManagerAdmin() {
		mngDAO =  new ManagerDAO();
	}
	
	/**
	 * Devuelve todos los tipos de usuario existentes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Tipousr> findAllTipoUSR(){
		return mngDAO.findAll(Tipousr.class);
	}
	
	/**
	 * Devuelve un tipo de usuario por id
	 * @param id
	 * @return tipousr
	 * @throws Exception
	 */
	public Tipousr findTipoUSRByID(Integer id) throws Exception{
		return (Tipousr) mngDAO.findById(Tipousr.class, id);
	}
	
	/**
	 * Devuelve todos los usuarios existentes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Usuario> findAllUsuario(){
		return mngDAO.findAll(Usuario.class);
	}
	
	/**
	 * Devuelve un usuario por id
	 * @param id
	 * @return usuario
	 * @throws Exception
	 */
	public Usuario findUsuarioByID(Integer id) throws Exception{
		return (Usuario) mngDAO.findById(Usuario.class, id);
	}
	
	/**
	 * Devuelve un usuario por ALias
	 * @param alias
	 * @return
	 */
	public Usuario findUsuarioByAlias(String alias){
		Usuario resp = null;
		List<Usuario> listado = findAllUsuario();
		for (Usuario usuario : listado) {
			if(usuario.getAlias().equals(alias)){
				resp = usuario;
			}
		}
		return resp;
	}
	
	/**
	 * Verifica la existencia de alias
	 * @param alias
	 * @return true o false
	 */
	public boolean verificarAlias(String alias){
		boolean bandera = false;
		List<Usuario> listado = findAllUsuario();
		for (Usuario usr : listado) {
			if(usr.getAlias().equals(alias)){
				bandera = true;
			}
		}
		return bandera;
	}
	
	/**
	 * Ingresa un usuario a la BD
	 * @param nombre
	 * @param apellido
	 * @param alias
	 * @param pass
	 * @param tipo
	 * @throws Exception
	 */
	public void insertarUsuario(String nombre, String apellido, String alias, String pass, Tipousr tipo) throws Exception{
		Usuario usr = new Usuario();
		usr.setNombre(nombre);usr.setApellido(apellido);usr.setAlias(alias);usr.setPass(pass);
		usr.setTipousr(tipo);
		mngDAO.insertar(usr);
	}
	
	/**
	 * Modificar datos de usuario
	 * @param id
	 * @param pass
	 * @param tipo
	 * @throws Exception
	 */
	public void modificarUsuario(Integer id, String pass, Tipousr tipo) throws Exception{
		Usuario usr = this.findUsuarioByID(id);
		usr.setPass(pass);usr.setTipousr(tipo);
		mngDAO.actualizar(usr);
	}
	
		
	/**
	 * M�todo que busca un usuario respecto a su nick y contrase�a
	 * @param nick 
	 * @param pass
	 * @return Usuario o null
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Usuario findUserByAliasAndPass(String alias, String pass)throws Exception{
		try {
			List<Usuario> listado = (List<Usuario>) mngDAO.findByParam(Usuario.class, "o.alias", alias, null);
			if(listado == null || listado.isEmpty()){
				throw new Exception("No se encuentra el usuario."); 
			}
			Usuario u = listado.get(0);
			if (u.getPass().equals(pass)) {//MD5 PASS getMD5(pass)
				return u;
			}else{
				throw new Exception("Usuario o contrase�a invalidos");
			}		
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * Convierte un cadena en codigo MD5
	 * @param input entrada de cadena para convertirla en MD5
	 * @return String MD5
	 */
	public String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
