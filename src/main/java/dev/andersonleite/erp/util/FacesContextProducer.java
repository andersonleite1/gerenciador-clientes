package dev.andersonleite.erp.util;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;

/**
 * Produtor de FacesContext para injeção em componentes gerenciados.
 * 
 * <p>
 * 	Esta classe utiliza CDI para produzir uma instância de FacesContext no escopo de uma requisição.
 * </p>
 * 
 * <p>
 * 	Para injetar o FacesContext em um bean gerenciado, utilize a anotação {@code @Inject}:
 * </p>
 */
public class FacesContextProducer {

	/**
     * Produz uma instância do {@link FacesContext} para injeção.
     * 
     * @return A instância atual do FacesContext.
     */
    @Produces
    @RequestScoped
    public FacesContext produceFacesContext() {
        return FacesContext.getCurrentInstance();
    }
}
