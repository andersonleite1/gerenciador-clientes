package dev.andersonleite.erp.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.gson.Gson;

import dev.andersonleite.erp.dto.EnderecoDTO;
import dev.andersonleite.erp.model.Cliente;
import dev.andersonleite.erp.service.ClienteService;
import dev.andersonleite.erp.service.ViaCepService;
import dev.andersonleite.erp.util.FacesMessages;


@Named
@ViewScoped
public class ClienteBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String[] COLUNAS = {"Nome", "Email", "Telefone", "CEP"};
    
    private static final int COLUNA_LARGURA = 6000;
    
    @Inject
    private ClienteService clienteService;
    
    @Inject
    private FacesMessages messages;
    
    @Inject
    private FacesContext facesContext;
    
    private ViaCepService viaCepService = new ViaCepService();

    private List<Cliente> listaClientes;

    private String termoPesquisa;

    private Cliente cliente;

    @PostConstruct
    public void init() {
        getTodosClientes();
    }

    public void recarregarPagina() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        try {
            externalContext.redirect(externalContext.getRequestContextPath());
        } catch (IOException e) {
            messages.error("Erro ao recarregar a página!");
        }
    }
    
    public void prepararNovoCliente() {
        cliente = new Cliente();
    }

    public void prepararEdicao() {    
    }
    
    public void salvar() {
    	if(clienteService.ehEmailUnico(cliente)) {
    		clienteService.salvar(cliente);
            atualizarRegistros();
            recarregarPagina();
            messages.info("Cliente salvo com sucesso!");
    	} else {
    		facesContext.validationFailed();
            messages.error("O email já está cadastrado.");
    	}
    }

    public void excluir() {
        clienteService.removerPorId(cliente.getId());
        cliente = null;
        atualizarRegistros();
        messages.info("Cliente excluído com sucesso!");
    }

    public void pesquisar() {
        listaClientes = clienteService.pesquisar(termoPesquisa);
        
        if (listaClientes.isEmpty()) {
            messages.info("Sua consulta não retornou registros.");
        }
    }

    public void getTodosClientes() {
        listaClientes = clienteService.listarTodos();
    }

    private void atualizarRegistros() {
        if (jaHouvePesquisa()) {
            pesquisar();
        } else {
            getTodosClientes();
        }
    }

    private boolean jaHouvePesquisa() {
        return termoPesquisa != null && !termoPesquisa.isEmpty();
    }

    public List<Cliente> getListaClientes() {
        return listaClientes;
    }

    public void setListaClientes(List<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }

    public String getTermoPesquisa() {
        return termoPesquisa;
    }

    public void setTermoPesquisa(String termoPesquisa) {
        this.termoPesquisa = termoPesquisa;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public boolean getIsClienteSelecionado() {
        return cliente != null && cliente.getId() != null;
    }
    
    public void buscarCep() {
        try {
            String jsonResponse = viaCepService.consultaCep(cliente.getCep().replace("-", ""));
            if (jsonResponse != null && !jsonResponse.contains("\"erro\": true")) {
                Gson gson = new Gson();
                EnderecoDTO enderecoObtido = gson.fromJson(jsonResponse, EnderecoDTO.class);
                
                if(enderecoObtido.getCep() == null) {
                	messages.warn("O CEP informado não foi encontrado.");
                	return;
                }
                
                cliente.setEndereco(enderecoObtido.getLogradouro());
                cliente.setBairro(enderecoObtido.getBairro());
                cliente.setCidade(enderecoObtido.getLocalidade());
                cliente.setEstado(enderecoObtido.getEstado());
                cliente.setCep(enderecoObtido.getCep());
            } else {
                messages.warn("O CEP informado não foi encontrado.");
            }
        } catch (IOException e) {
            messages.error("Erro ao consultar CEP.");
        }
    }
    
    public void exportarParaExcel() {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = criarSheet(workbook);
            CellStyle headerStyle = criarHeaderStyle(workbook);
            CellStyle dataStyle = criarDataStyle(workbook);

            criarCabecalho(sheet, headerStyle);
            preencherDados(sheet, listaClientes, dataStyle);

            escreverParaResposta(workbook);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Sheet criarSheet(Workbook workbook) {
        Sheet sheet = workbook.createSheet("Planilha-Clientes");
        for (int i = 0; i < COLUNAS.length; i++) {
            sheet.setColumnWidth(i, COLUNA_LARGURA);
        }
        return sheet;
    }

    private CellStyle criarHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

    private CellStyle criarDataStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

    private void criarCabecalho(Sheet sheet, CellStyle headerStyle) {
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < COLUNAS.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(COLUNAS[i]);
            cell.setCellStyle(headerStyle);
        }
    }

    private void preencherDados(Sheet sheet, List<Cliente> clientes, CellStyle dataStyle) {
        int rowIndex = 1;
        for (Cliente cliente : clientes) {
            Row dataRow = sheet.createRow(rowIndex++);
            criarCelula(dataRow, 0, cliente.getNome(), dataStyle);
            criarCelula(dataRow, 1, cliente.getEmail(), dataStyle);
            criarCelula(dataRow, 2, cliente.getTelefone(), dataStyle);
            criarCelula(dataRow, 3, cliente.getCep(), dataStyle);
        }
    }

    private void criarCelula(Row dataRow, int colunaIndex, String valor, CellStyle style) {
        Cell cell = dataRow.createCell(colunaIndex);
        cell.setCellValue(valor);
        cell.setCellStyle(style);
    }

    private void escreverParaResposta(Workbook workbook) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);

        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=Clientes.xlsx");
        response.getOutputStream().write(outputStream.toByteArray());
        response.getOutputStream().flush();
        FacesContext.getCurrentInstance().responseComplete();
    }
}

