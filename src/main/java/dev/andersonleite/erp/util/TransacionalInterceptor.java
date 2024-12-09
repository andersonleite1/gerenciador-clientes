package dev.andersonleite.erp.util;

import java.io.Serializable;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.jboss.logging.Logger;


/**
 * Interceptor responsável por gerenciar transações de forma automática em métodos anotados com {@link Transacional}.
 * <p>
 * Este interceptor utiliza a API de Interceptores do CDI para garantir que operações no banco de dados sejam
 * realizadas dentro de uma transação, com controle de commit e rollback em caso de exceções.
 * </p>
 * 
 * <p><strong>Características:</strong></p>
 * <ul>
 *   <li>Anotado com {@code @Interceptor}, permitindo sua ativação em métodos marcados com {@link Transacional}.</li>
 *   <li>Gerencia o ciclo de vida das transações utilizando {@link EntityTransaction}.</li>
 * </ul>
 */
@Interceptor
@Transacional
@Priority(Interceptor.Priority.APPLICATION)
public class TransacionalInterceptor implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Gerenciador de entidades, injetado pelo CDI, utilizado para acessar e controlar transações.
     */
    @Inject
    private EntityManager manager;

    private static final Logger logger = Logger.getLogger(TransacionalInterceptor.class.getName());
    
    /**
     * Método principal do interceptor, executado antes e depois do método interceptado.
     * <p>
     * Este método inicia uma transação caso nenhuma esteja ativa, garante o rollback em caso de exceções,
     * e faz o commit ao final da execução com sucesso.
     * </p>
     * 
     * @param context o contexto da invocação do método interceptado.
     * @return o resultado da execução do método interceptado.
     * @throws Exception caso ocorra algum erro durante a execução do método.
     */
    @AroundInvoke
    public Object invoke(InvocationContext context) throws Exception {
        EntityTransaction trx = manager.getTransaction();
        boolean criador = false;

        try {
            if (!trx.isActive()) {
            	logger.info("Iniciando nova transação...");
                // Garante que transações previamente abertas não interfiram
                trx.begin();
                trx.rollback();

                // Inicia uma nova transação
                trx.begin();
                criador = true;
            }

            logger.info("Executando método: " + context.getMethod().getName());
            // Executa o método interceptado
            return context.proceed();
        } catch (Exception e) {
            // Realiza rollback caso seja necessário
            if (trx != null && criador) {
            	logger.error("Erro encontrado, realizando rollback: " + e.getMessage());
                trx.rollback();
            }
            throw e; 
        } finally {
            // Faz o commit se a transação ainda estiver ativa e foi iniciada por este interceptor
            if (trx != null && trx.isActive() && criador) {
            	logger.info("Fazendo commit da transação...");
                trx.commit();
            }
        }
    }
}
