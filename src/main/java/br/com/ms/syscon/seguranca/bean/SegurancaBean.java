/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.seguranca.bean;

import br.com.ms.syscon.seguranca.dominio.Acao;
import br.com.ms.syscon.seguranca.dominio.Funcionalidade;
import br.com.ms.syscon.seguranca.dominio.GrupoFuncionalidade;
import br.com.ms.syscon.seguranca.dominio.Modulo;
import br.com.ms.syscon.seguranca.dominio.Usuario;
import br.com.ms.syscon.seguranca.servico.ModuloServico;
import br.com.ms.syscon.seguranca.servico.SegurancaServico;
import br.com.ms.syscon.util.bean.primefaces.MessageView;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.spi.CDI;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

@Named("segurancaBean")
@SessionScoped
public class SegurancaBean implements Serializable {

    @Inject private SegurancaServico segurancaServico;
    
    @Inject private ModuloServico moduloServico;
    
    /* Componentes para AutenticaÃ§Ã£o e PermissÃµes do Usuario*/
    private Usuario usuario;
    private List<Funcionalidade>listFuncionalidade;
    
    /* Componentes para Mensagens de PossÃ­veis Erros*/
    private MessageView mensagem;
    
    /* Componentes para Menu Principal*/
    private MenuModel menuModel;
    private List<Funcionalidade>listTabFuncionalidade;
    int controleIndex = 0;
    
    @PostConstruct
    public void init(){
        usuario = new Usuario();
        listFuncionalidade = new ArrayList<>();
        
        mensagem = new MessageView();
        
        menuModel = new DefaultMenuModel();
        listTabFuncionalidade = new ArrayList<>();
    }
    

    public String login() {
        try{
            usuario = segurancaServico.autenticarUsuario(usuario);
            listFuncionalidade = segurancaServico.obterPermissoesUsuario(usuario);
            setMenuModel(menu());
            return "/pages/paginaInicial.xhtml";
        }catch(RuntimeException e){
            mensagem.mensagemAlerta(e);
        } 
        return null;
    }    
    
    public String logout() {
        try{
            return "/login.xhtml?faces-redirect=true";
        } finally{
            getListTabFuncionalidade().clear();
            getListFuncionalidade().clear();
            setMenuModel(null);
        }
    }

    public MenuModel menu(){
        MenuModel model = new DefaultMenuModel();
        boolean criaGrupo = false;
        boolean criaModulo = false;
        
        //Lista todos os módulos do sistema
        for ( Modulo modulo : moduloServico.listar() ){
            DefaultSubMenu menuModulo = new DefaultSubMenu();
            menuModulo.setLabel(modulo.getNmDescricao());
            
            for ( GrupoFuncionalidade grupoFuncionalidade : modulo.getListGrupoFuncionalidade() ){
                DefaultSubMenu menuGrupo = new DefaultSubMenu();
                menuGrupo.setLabel(grupoFuncionalidade.getNmDescricao());
                
                //Percorre todas as funcionalidades do sistema
                for ( Funcionalidade funcionalidade : grupoFuncionalidade.getListFuncionalidade() ){
                    //Percorre as Funcionalidades permitidas
                    for ( Funcionalidade funcionalidadePermissao : listFuncionalidade ){
                        //Se a funcionalidade for permitida, ela serÃ¡ montada.
                        if ( funcionalidadePermissao.getId().equals(funcionalidade.getId()) ){
                            DefaultMenuItem menuFuncionalidade = new DefaultMenuItem();
                            /* Monta os Itens da Funcionalidade */
                            menuFuncionalidade.setValue(funcionalidade.getNmFuncionalidade());
                            menuFuncionalidade.setIcon(funcionalidade.getNmIcone());

                            /* Componente de Auxilio da TabFuncionalidade. Chama a Função e Adiciona os Parâmetros */
                            menuFuncionalidade.setCommand("#{segurancaBean.adicionaTabFuncionalidade()}");
                            menuFuncionalidade.setParam("idTabFuncionalidade", funcionalidadePermissao.getId());
                            
                            criaGrupo = true; //Se existir uma funcionalidade, o grupo será criado
                            menuGrupo.addElement(menuFuncionalidade);
                        }
                    }
                }
                if ( criaGrupo ){
                    criaModulo = true; //Se existir um Grupo, o módulo será criado
                    menuModulo.addElement(menuGrupo);
                }
                    
            }
            if ( criaModulo ){
                model.addElement(menuModulo); 
            }
        }
        return model;
    }
    
    public void adicionaTabFuncionalidade(){
        RequestContext context = RequestContext.getCurrentInstance();        
        Long idTabFuncionalidade = Long.parseLong(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idTabFuncionalidade"));
   
        /* Se a funcionalidade já está criada, ela não será mais criada e será redirecionada
           para a tab já existente */
        boolean criaTab = true;
        for ( Funcionalidade tabAux : listTabFuncionalidade ){
            if ( tabAux.getId().equals(idTabFuncionalidade) ){
                criaTab = false;
                setControleIndex(listTabFuncionalidade.indexOf(tabAux) + 1);
            }
        }
        
        if ( criaTab == true ){
            Funcionalidade funcionalidade = new Funcionalidade();
            for ( Funcionalidade funAux : listFuncionalidade ){
                if ( funAux.getId().equals(idTabFuncionalidade) ){
                    funcionalidade = funAux;
                }
            }
            listTabFuncionalidade.add(funcionalidade);
            setControleIndex(listTabFuncionalidade.size());
        }
        context.update("formTabFuncionalidade");
    }
    
    public void removeTabFuncionalidade(Long id) {     
        Funcionalidade funcionalidade = new Funcionalidade();
        for ( Funcionalidade funAux : listTabFuncionalidade ){
            if ( funAux.getId().equals(id) ){
                funcionalidade = funAux;
            }
        }
        listTabFuncionalidade.remove(funcionalidade);
        setControleIndex(listTabFuncionalidade.size());
    }


    
    public void callSubAcaoMethod(Acao acao, Class bean, Object objeto){
        try {
            /* Método de instanciar um objeto dinamicamente em uso de CDI */
            Object objBean = CDI.current().select(bean).get();
            
            /* Estou chamando o método do Bean, dando como parâmetro suas ações e a classe que será alterada */
            Method method = bean.getMethod(acao.getTipoAcao().getNmBean(), objeto.getClass());
            method.invoke(objBean, objeto);
            
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(SegurancaBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
    
    
    
    //Getters e Setters
    
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Funcionalidade> getListFuncionalidade() {
        return listFuncionalidade;
    }

    public void setListFuncionalidade(List<Funcionalidade> listFuncionalidade) {
        this.listFuncionalidade = listFuncionalidade;
    }
    
    public MenuModel getMenuModel() {
        return menuModel;
    }

    public void setMenuModel(MenuModel menuModel) {
        this.menuModel = menuModel;
    }

        public List<Funcionalidade> getListTabFuncionalidade() {
        return listTabFuncionalidade;
    }

    public void setListTabFuncionalidade(List<Funcionalidade> listTabFuncionalidade) {
        this.listTabFuncionalidade = listTabFuncionalidade;
    }

    public int getControleIndex() {
        return controleIndex;
    }

    public void setControleIndex(int controleIndex) {
        this.controleIndex = controleIndex;
    }
    
}
