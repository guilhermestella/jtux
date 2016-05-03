/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.util.bean.primefaces;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class MessageView {

    public void mensagemSucesso() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensagem: ", "Gravado com Sucesso!"));
    }
    
    public void mensagemSucesso(String mensagem) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensagem: ", mensagem));
    }

    public void mensagemAlerta(Throwable e) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta!", getRootCause(e)));
    }
    
    public void mensagemAlerta(String mensagem) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta!", mensagem));
    }

    public void mensagemErro(Throwable e) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", getRootCause(e)));
    }

    public void mensagemErroFatal() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erro Fatal!", "Erro Fatal. Entre em contato com o Administrador do Sistema."));
    }
    
    /**
     *Este método captura a mensagem da Exception.
     * @param throwable
     * @return
     */
    public static String getRootCause(Throwable throwable) {
        if (throwable.getCause() != null)
            return getRootCause(throwable.getCause());
        return throwable.getMessage();
    }
}
