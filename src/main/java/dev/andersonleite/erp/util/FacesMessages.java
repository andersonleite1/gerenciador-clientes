package dev.andersonleite.erp.util;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * Classe utilitária para exibição de mensagens no contexto JSF.
 * <p>
 * Essa classe encapsula a criação e exibição de mensagens no contexto do JSF, 
 * fornecendo métodos específicos para diferentes niveis de informacao.
 * </p>
 * 
 * <p><strong>Funcionalidades:</strong></p>
 * <ul>
 *   <li>Facilita a exibição de mensagens informativas na interface do usuário.</li>
 *   <li>Garante que as mensagens sejam associadas ao contexto JSF atual.</li>
 *   <li>Possui uma estrutura extensível para adicionar outros tipos de mensagens (info, erro, warn, etc.).</li>
 * </ul>
 * 
 * <p><strong>Exemplo de Uso:</strong></p>
 * <pre>
 * {@code
 * FacesMessages messages = new FacesMessages();
 * messages.info("Operação realizada com sucesso!");
 * }
 * </pre>
 */
public class FacesMessages implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Adiciona uma mensagem ao contexto JSF com a informação especificada.
     * 
     * @param msg     a mensagem a ser exibida.
     * @param severity o nível de severidade da mensagem (INFO, WARN, ERROR, FATAL).
     */
    private void add(String msg, FacesMessage.Severity severity) {
        FacesMessage facesMessage = new FacesMessage(msg);
        facesMessage.setSeverity(severity);

        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    }

    /**
     * Exibe uma mensagem informativa na interface do usuário.
     * 
     * @param msg a mensagem informativa a ser exibida.
     */
    public void info(String msg) {
        add(msg, FacesMessage.SEVERITY_INFO);
    }
    
    /**
     * Exibe uma mensagem de aviso na interface do usuário.
     * 
     * @param msg a mensagem de aviso a ser exibida.
     */
    public void warn(String msg) {
        add(msg, FacesMessage.SEVERITY_WARN);
    }
    
    /**
     * Exibe uma mensagem de erro na interface do usuário.
     * 
     * @param msg a mensagem de erro a ser exibida.
     */
    public void error(String msg) {
        add(msg, FacesMessage.SEVERITY_ERROR);
    }

}
