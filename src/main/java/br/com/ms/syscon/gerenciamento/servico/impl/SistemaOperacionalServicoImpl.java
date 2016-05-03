/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.gerenciamento.servico.impl;

import br.com.ms.syscon.gerenciamento.dominio.SistemaOperacional;
import br.com.ms.syscon.gerenciamento.negocio.SistemaOperacionalNegocio;
import br.com.ms.syscon.gerenciamento.servico.SistemaOperacionalServico;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class SistemaOperacionalServicoImpl implements SistemaOperacionalServico {

    @Inject private SistemaOperacionalNegocio sistemaOperacionalNegocio;
    
    @Override
    public void cadastrar(SistemaOperacional entity) {
        sistemaOperacionalNegocio.cadastrar(entity);
    }

    @Override
    public void alterar(SistemaOperacional entity) {
        sistemaOperacionalNegocio.alterar(entity);
    }

    @Override
    public void excluir(SistemaOperacional entity) {
        sistemaOperacionalNegocio.excluir(entity);
    }

    @Override
    public SistemaOperacional pesquisar(Long Key) {
        return sistemaOperacionalNegocio.pesquisar(Key);
    }

    @Override
    public List<SistemaOperacional> listar() {
        return sistemaOperacionalNegocio.listar();
    }
    
}
