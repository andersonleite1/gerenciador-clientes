package dev.andersonleite.erp.util;

import java.io.Serializable;

import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Classe responsável por gerenciar a criação e o encerramento do {@link EntityManager}.
 * <p>
 * Utiliza o padrão de escopo de contexto do CDI para produzir e gerenciar instâncias do {@link EntityManager},
 * garantindo a injeção e o gerenciamento automático pelo container.
 * </p>
 * 
 * <p><strong>Escopos utilizados:</strong></p>
 * <ul>
 *   <li>{@code @ApplicationScoped} - Garante que a instância da classe seja única durante o ciclo de vida da aplicação.</li>
 *   <li>{@code @RequestScoped} - Define que cada {@link EntityManager} produzido é exclusivo para a requisição HTTP.</li>
 * </ul>
 */
@ApplicationScoped
public class EntityManagerProducer implements Serializable {

    private static final long serialVersionUID = 1L;

    private EntityManagerFactory factory;

    /**
     * Construtor padrão.
     * <p>
     * Inicializa a {@link EntityManagerFactory} utilizando a unidade de persistência
     * definida no arquivo {@code persistence.xml}.
     * </p>
     */
    public EntityManagerProducer() {
        this.factory = Persistence.createEntityManagerFactory("GerenciadorClientes");
    }

    /**
     * Produz uma nova instância do {@link EntityManager}.
     * <p>
     * O escopo {@link RequestScoped} garante que cada requisição HTTP receba sua própria
     * instância de {@link EntityManager}, evitando problemas de concorrência.
     * </p>
     * 
     * @return uma nova instância de {@link EntityManager}.
     */
    @Produces
    @RequestScoped
    public EntityManager createEntityManager() {
        return this.factory.createEntityManager();
    }

    /**
     * Fecha uma instância do {@link EntityManager}.
     * <p>
     * Este método é chamado automaticamente pelo CDI ao final do ciclo de vida
     * da requisição, garantindo o encerramento correto dos recursos.
     * </p>
     * 
     * @param manager o {@link EntityManager} a ser fechado.
     */
    public void closeEntityManager(@Disposes EntityManager manager) {
        manager.close();
    }

    @PreDestroy
    public void closeEntityManagerFactory() {
        if (factory != null) {
            factory.close();
        }
    }

}
