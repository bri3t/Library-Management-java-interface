
package dao;

import model.Usuari;

/**
 *
 * @author arnau
 */
public interface LoginDAO  {
    
    Usuari verificarLogin(String usr, String passwd);
    
    
}
