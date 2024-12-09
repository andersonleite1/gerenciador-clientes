package dev.andersonleite.erp.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import dev.andersonleite.erp.model.Cliente;
import dev.andersonleite.erp.repository.ClienteRepository;
import dev.andersonleite.erp.util.Transacional;

public class ClienteService implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Inject
    private ClienteRepository clienteRepository;
    
    @Transacional
    public void salvar(Cliente cliente) {
        clienteRepository.salvar(cliente);
    }
    
    @Transacional
    public void removerPorId(Long id) {
        Cliente cliente = clienteRepository.porId(id);
        if (cliente != null) {
            clienteRepository.remover(cliente);
        }
    }

    public List<Cliente> listarTodos() {
        return clienteRepository.todos();
    }

    public List<Cliente> pesquisar(String nome) {
        return clienteRepository.pesquisar(nome);
    }
    
    public boolean ehEmailUnico(Cliente cliente) {
        Cliente existente = clienteRepository.buscarPorEmail(cliente.getEmail());
        return existente == null || existente.getId().equals(cliente.getId());
    }
}
