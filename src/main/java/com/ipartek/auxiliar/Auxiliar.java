package com.ipartek.auxiliar;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import org.springframework.http.ResponseEntity;
import com.ipartek.modelo.Rol;
import com.ipartek.modelo.Usuario;
import com.ipartek.pojo.MsgError;

public class Auxiliar {

	public static String generarSal(int bytes) {
        SecureRandom random = new SecureRandom();
        byte[] saltBytes = new byte[bytes];
        random.nextBytes(saltBytes);

        StringBuilder sb = new StringBuilder();
        for (byte b : saltBytes) {
            sb.append(String.format("%02X", b)); 
        }
        return sb.toString();
    }
	
	//SHA256 de una entrada de texto
    public static String hashear(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(input.getBytes(StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder(digest.length * 2);
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            
            return sb.toString();      
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 no disponible", e);
        }
    }
    
    public static ResponseEntity<?> statusError200y404(Object resultado, String TipoClassParaMensaje){
		if(resultado == null) {
			return  ResponseEntity.status(404).body(new MsgError(404, TipoClassParaMensaje + " esta vacia/o"));
		}
		if(resultado instanceof List) {
			if(((List<?>)resultado).isEmpty()) {
				return ResponseEntity.status(404).body(new MsgError(404, TipoClassParaMensaje + " lista esta vacio/a"));
			}
		}
		int id = -1;
	    if (resultado instanceof Rol) id = ((Rol) resultado).getId();
	    else if (resultado instanceof Usuario) id = ((Usuario) resultado).getId();

	    if (id == 0) {
	        return ResponseEntity.status(404).body(new MsgError(404, TipoClassParaMensaje + " no encontrado o no insertado"));
	    }
		return ResponseEntity.ok(resultado);	
	}
}
