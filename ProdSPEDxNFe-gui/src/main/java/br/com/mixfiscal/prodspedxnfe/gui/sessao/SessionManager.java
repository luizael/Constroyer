package br.com.mixfiscal.prodspedxnfe.gui.sessao;


import br.com.mixfiscal.prodspedxnfe.domain.own.Usuario;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServlet;

public class SessionManager {
   
    private Usuario sessionaUsuarioLogado;   
    
    public Usuario getSessionaUsuarioLogado() {
        return sessionaUsuarioLogado;
    }

    public void setSessionaUsuarioLogado(Usuario sessionaUsuarioLogado) {
        this.sessionaUsuarioLogado = sessionaUsuarioLogado;
    }
       
}
