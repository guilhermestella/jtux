/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.gerenciamento.servico.impl;

import br.com.ms.syscon.gerenciamento.dominio.VariavelArquivo;
import br.com.ms.syscon.gerenciamento.negocio.VariavelArquivoNegocio;
import br.com.ms.syscon.gerenciamento.servico.VariavelArquivoServico;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class VariavelArquivoServicoImpl implements VariavelArquivoServico {

    @Inject private VariavelArquivoNegocio variavelArquivoNegocio;
    
    
    @Override
    public void cadastrar(VariavelArquivo entity) {
        variavelArquivoNegocio.cadastrar(entity);
    }

    @Override
    public void alterar(VariavelArquivo entity) {
        variavelArquivoNegocio.alterar(entity);
    }

    @Override
    public void excluir(VariavelArquivo entity) {
        variavelArquivoNegocio.excluir(entity);
    }

    @Override
    public VariavelArquivo pesquisar(Long Key) {
        return variavelArquivoNegocio.pesquisar(Key);
    }

    @Override
    public List<VariavelArquivo> listar() {
        return variavelArquivoNegocio.listar();
    }
    
}
