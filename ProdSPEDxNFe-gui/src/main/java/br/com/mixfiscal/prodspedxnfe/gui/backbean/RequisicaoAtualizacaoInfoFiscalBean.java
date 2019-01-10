
package br.com.mixfiscal.prodspedxnfe.gui.backbean;

import br.com.mixfiscal.prodspedxnfe.domain.enums.EFormatarCodigoInterno;
import br.com.mixfiscal.prodspedxnfe.domain.own.Cliente;
import br.com.mixfiscal.prodspedxnfe.services.ClienteService;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;
 

@ManagedBean(name = "requisicaoAtualizacaoInfoFiscalBean")
@RequestScoped
public class RequisicaoAtualizacaoInfoFiscalBean {    
    private List<SelectItem> selectItem;
    private  ClienteService clienteService;
    
    public List<SelectItem> getSelectItem() {
        return selectItem;
    }    
    
    public RequisicaoAtualizacaoInfoFiscalBean(){} 
    
    @PostConstruct
    public void init(){
        selectItem = new ArrayList<>();
        clienteService = new ClienteService();
        preencherSelectItem();
    }
    
    private void preencherSelectItem(){
        List<Cliente> clientes = new ArrayList<Cliente>();
        try{
            clientes = clienteService.listar(null, -1, -1, null, null);
            for(Cliente cl : clientes){
                selectItem.add(new SelectItem(cl.getId(), cl.getNome()));
            }
        }catch(Exception ex){
            System.out.println(""+ex.getMessage());
        }
    }
    
    public EFormatarCodigoInterno[] getFormatarCodigoInterno(){
        return EFormatarCodigoInterno.values();
    }
}
