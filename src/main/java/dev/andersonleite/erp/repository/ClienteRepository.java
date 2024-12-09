package dev.andersonleite.erp.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import dev.andersonleite.erp.model.Cliente;

public class ClienteRepository implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private EntityManager manager;

    public ClienteRepository() {

    }

    public ClienteRepository(EntityManager manager) {
        this.manager = manager;
    }

    public Cliente porId(Long id) {
        return manager.find(Cliente.class, id);
    }
    
    public Cliente buscarPorEmail(String email) {
        String jpql = "select c from Cliente c where c.email = :email";
        
        TypedQuery<Cliente> query = manager.createQuery(jpql, Cliente.class);
        query.setParameter("email", email);
        
        return query.getResultList().stream().findFirst().orElse(null);
    }

    public List<Cliente> pesquisar(String nome) {
        String jpql = "from Cliente where nome like :nome";
        
        TypedQuery<Cliente> query = manager.createQuery(jpql, Cliente.class);
        query.setParameter("nome", "%" + nome + "%");
        
        return query.getResultList();
    }
    
    public List<Cliente> todos() {   
    	try {
    		return manager.createQuery("from Cliente", Cliente.class).getResultList();
		} catch (Exception e) {
			System.err.println("Erro ao recuperar todos clientes: " + e.getStackTrace());
			return new ArrayList<Cliente>();
		}
    }

    public Cliente salvar(Cliente cliente) {
    	
    	if (cliente.getId() != null) System.out.println("O ID nao esta nulo: " + cliente.getId());
    	
        return manager.merge(cliente);
    }

    public void remover(Cliente cliente) {
        cliente = porId(cliente.getId());
        manager.remove(cliente);
    }
}
