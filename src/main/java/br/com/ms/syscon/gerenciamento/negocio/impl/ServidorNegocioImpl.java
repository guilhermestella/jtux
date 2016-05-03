/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.gerenciamento.negocio.impl;

import br.com.ms.syscon.gerenciamento.dao.ServidorDao;
import br.com.ms.syscon.gerenciamento.dao.VariavelArquivoDao;
import br.com.ms.syscon.gerenciamento.dominio.ArquivoConfiguracao;
import br.com.ms.syscon.gerenciamento.dominio.Servidor;
import br.com.ms.syscon.gerenciamento.dominio.VariavelArquivo;
import br.com.ms.syscon.gerenciamento.dominio.VariavelGlobal;
import br.com.ms.syscon.gerenciamento.negocio.ServidorNegocio;
import br.com.ms.syscon.util.dominio.Arquivo;
import br.com.ms.syscon.util.validation.NegocioException;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class ServidorNegocioImpl implements ServidorNegocio {

    @Inject private ServidorDao servidorDao;
    
    @Inject private VariavelArquivoDao variavelArquivoDao;
    
    @Override
    public void cadastrar(Servidor entity) {
        
        if ( entity.getNmDescricao().isEmpty() || entity.getNmUsuarioSsh().isEmpty() || entity.getNmSenhaSsh().isEmpty() || entity.getNmEnderecoIp().isEmpty() || entity.getNmRepositorioArquivos().isEmpty() )
            throw new NegocioException("Campos Vazios");

        if ( entity.getNrPorta() != 0 )
            throw new NegocioException("Porta não pode ser 0");

        servidorDao.add(entity);
    }

    @Override
    public void alterar(Servidor entity) {
        
        if ( entity.getNmDescricao().isEmpty() || entity.getNmUsuarioSsh().isEmpty() || entity.getNmSenhaSsh().isEmpty() || entity.getNmEnderecoIp().isEmpty() || entity.getNmRepositorioArquivos().isEmpty() )
            throw new NegocioException("Campos Vazios");
        
        servidorDao.update(entity);
    }

    @Override
    public void excluir(Servidor entity) {
        
        if ( !entity.getListUsuario().isEmpty() ){
            throw new NegocioException("Servidor está associado com Usuários");
        }
        if ( !entity.getListServico().isEmpty() ){
            throw new NegocioException("Servidor está associado Serviços");
        }
        
        for ( VariavelArquivo var : entity.getListVariavelArquivo() ){
            variavelArquivoDao.remove(var);
        }

        servidorDao.remove(entity);
    }

    @Override
    public Servidor pesquisar(Long Key) {
        return servidorDao.find(Key);
    }

    @Override
    public List<Servidor> listar() {
        return servidorDao.list();
    }

    
    /**
     * Abre Conexão SSH com Servidor com os dados cadastrados no Servidor.
     * @param servidor
     * @return
     * @throws NegocioException 
     */
    @Override
    public Session abrirConexaoSsh(Servidor servidor) throws NegocioException{
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(servidor.getNmUsuarioSsh(), servidor.getNmEnderecoIp(), servidor.getNrPorta());
            session.setPassword(servidor.getNmSenhaSsh());
            session.setConfig("StrictHostKeyChecking", "no");
            System.out.println("Estabelecendo Conexão...");
            session.connect();
            System.out.println("Conexão estabilizada.");
            return session;
        } catch (JSchException ex) {
            throw new NegocioException("Falha na conexão! Verifique se o serviço de SSH do Servidor está ativo!");
        }

    }

    
    /**
     * Fecha Conexão SSH da Sessão Atual.
     * @param session 
     */
    @Override
    public void fecharConexaoSsh(Session session) {
        if ( session == null ){
            throw new NegocioException("Falha ao fechar a Conexão!");
        }
        session.disconnect();
        System.out.println("Desconectado.");
    }

    @Override
    public Arquivo executarComandoSsh(Session session, String comando) {
        
        if ( comando == null || comando.isEmpty() || comando.length() <= 3  )
            throw new NegocioException("Comando Vazio ou Não Implementado");
        
        try {
            ChannelExec channel = (ChannelExec) session.openChannel("exec");
            ((ChannelExec) channel).setPty(true);
            ((ChannelExec) channel).setPtyType("vt100");
            ((ChannelExec) channel).setXForwarding(true);
            System.out.println("Comando " + comando);
            channel.setCommand(comando);
            channel.setInputStream(null);
            channel.setErrStream(System.err);
            BufferedReader in = new BufferedReader(new InputStreamReader(channel.getInputStream()));

            channel.connect();
            String line = "";

            
            /* Aqui colocamos a Data e Hora no arquivo de Backup */
            DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
            Date date = new Date();
            
            Arquivo arquivo = new Arquivo("log" + dateFormat.format(date));
            PrintWriter writer = new PrintWriter(arquivo);
            
            while (true) {
                while ((line = in.readLine()) != null) {
                    System.out.println("-- " + line);
                    writer.println("-- " + line);
                }
                break;
            }

            writer.close();
            channel.disconnect();
            session.disconnect();
            return arquivo;
            
        } catch (JSchException | IOException ex) {
            Logger.getLogger(ServidorNegocioImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void adicionarArquivosConfiguracaoServidor(Servidor servidor) {
        
        
        /* Squid */
        
            /* Squid.conf */
                /* http_port */
                VariavelArquivo varArquivo1 = new VariavelArquivo();
                ArquivoConfiguracao arq1 = new ArquivoConfiguracao();
                VariavelGlobal varGlobal1 = new VariavelGlobal();
                arq1.setId(1L);
                varGlobal1.setId(1L);
                varArquivo1.setArquivoConfiguracao(arq1);
                varArquivo1.setVariavelGlobal(varGlobal1);
                varArquivo1.setServidor(servidor);
                varArquivo1.setDsCampo("");
                variavelArquivoDao.add(varArquivo1);

                /* visible_hostname */
                VariavelArquivo varArquivo2 = new VariavelArquivo();
                ArquivoConfiguracao arq2 = new ArquivoConfiguracao();
                VariavelGlobal varGlobal2 = new VariavelGlobal();
                arq2.setId(1L);
                varGlobal2.setId(2L);
                varArquivo2.setArquivoConfiguracao(arq2);
                varArquivo2.setVariavelGlobal(varGlobal2);
                varArquivo2.setServidor(servidor);
                varArquivo2.setDsCampo("");
                variavelArquivoDao.add(varArquivo2);

                /* cache_mgr */
                VariavelArquivo varArquivo3 = new VariavelArquivo();
                ArquivoConfiguracao arq3 = new ArquivoConfiguracao();
                VariavelGlobal varGlobal3 = new VariavelGlobal();
                arq3.setId(1L);
                varGlobal3.setId(3L);
                varArquivo3.setArquivoConfiguracao(arq3);
                varArquivo3.setVariavelGlobal(varGlobal3);
                varArquivo3.setServidor(servidor);
                varArquivo3.setDsCampo("");
                variavelArquivoDao.add(varArquivo3);

                /* error_directory */
                VariavelArquivo varArquivo4 = new VariavelArquivo();
                ArquivoConfiguracao arq4 = new ArquivoConfiguracao();
                VariavelGlobal varGlobal4 = new VariavelGlobal();
                arq4.setId(1L);
                varGlobal4.setId(4L);
                varArquivo4.setArquivoConfiguracao(arq4);
                varArquivo4.setVariavelGlobal(varGlobal4);
                varArquivo4.setServidor(servidor);
                varArquivo4.setDsCampo("");
                variavelArquivoDao.add(varArquivo4);

                /* cache_mem */
                VariavelArquivo varArquivo5 = new VariavelArquivo();
                ArquivoConfiguracao arq5 = new ArquivoConfiguracao();
                VariavelGlobal varGlobal5 = new VariavelGlobal();
                arq5.setId(1L);
                varGlobal5.setId(5L);
                varArquivo5.setArquivoConfiguracao(arq5);
                varArquivo5.setVariavelGlobal(varGlobal5);
                varArquivo5.setServidor(servidor);
                varArquivo5.setDsCampo("");
                variavelArquivoDao.add(varArquivo5);

                /* maximum_object_size_in_memory */
                VariavelArquivo varArquivo6 = new VariavelArquivo();
                ArquivoConfiguracao arq6 = new ArquivoConfiguracao();
                VariavelGlobal varGlobal6 = new VariavelGlobal();
                arq6.setId(1L);
                varGlobal6.setId(6L);
                varArquivo6.setArquivoConfiguracao(arq6);
                varArquivo6.setVariavelGlobal(varGlobal6);
                varArquivo6.setServidor(servidor);
                varArquivo6.setDsCampo("");
                variavelArquivoDao.add(varArquivo6);
            
                /* maximum_object_size */
                VariavelArquivo varArquivo7 = new VariavelArquivo();
                ArquivoConfiguracao arq7 = new ArquivoConfiguracao();
                VariavelGlobal varGlobal7 = new VariavelGlobal();
                arq7.setId(1L);
                varGlobal7.setId(7L);
                varArquivo7.setArquivoConfiguracao(arq7);
                varArquivo7.setVariavelGlobal(varGlobal7);
                varArquivo7.setServidor(servidor);
                varArquivo7.setDsCampo("");
                variavelArquivoDao.add(varArquivo7);
            
		/* acl block_arq urlpath_regex -i */
                VariavelArquivo varArquivo8 = new VariavelArquivo();
                ArquivoConfiguracao arq8 = new ArquivoConfiguracao();
                VariavelGlobal varGlobal8 = new VariavelGlobal();
                arq8.setId(1L);
                varGlobal8.setId(8L);
                varArquivo8.setArquivoConfiguracao(arq8);
                varArquivo8.setVariavelGlobal(varGlobal8);
                varArquivo8.setServidor(servidor);
                varArquivo8.setDsCampo("");
                variavelArquivoDao.add(varArquivo8);
            
            /* g_full_access */
                
		/* group_full_access */
                VariavelArquivo varArquivo9 = new VariavelArquivo();
                ArquivoConfiguracao arq9 = new ArquivoConfiguracao();
                VariavelGlobal varGlobal9 = new VariavelGlobal();
                arq9.setId(2L);
                varGlobal9.setId(9L);
                varArquivo9.setArquivoConfiguracao(arq9);
                varArquivo9.setVariavelGlobal(varGlobal9);
                varArquivo9.setServidor(servidor);
                varArquivo9.setDsCampo("");
                variavelArquivoDao.add(varArquivo9);
            
            /* g_mid_access */
                
		/* group_mid_access */
                VariavelArquivo varArquivo10 = new VariavelArquivo();
                ArquivoConfiguracao arq10 = new ArquivoConfiguracao();
                VariavelGlobal varGlobal10 = new VariavelGlobal();
                arq10.setId(3L);
                varGlobal10.setId(3L);
                varArquivo10.setArquivoConfiguracao(arq10);
                varArquivo10.setVariavelGlobal(varGlobal10);
                varArquivo10.setServidor(servidor);
                varArquivo10.setDsCampo("");
                variavelArquivoDao.add(varArquivo10);
            
            /* g_no_access */
                
		/* group_no_access */
                VariavelArquivo varArquivo11 = new VariavelArquivo();
                ArquivoConfiguracao arq11 = new ArquivoConfiguracao();
                VariavelGlobal varGlobal11 = new VariavelGlobal();
                arq11.setId(4L);
                varGlobal11.setId(11L);
                varArquivo11.setArquivoConfiguracao(arq11);
                varArquivo11.setVariavelGlobal(varGlobal11);
                varArquivo11.setServidor(servidor);
                varArquivo11.setDsCampo("");
                variavelArquivoDao.add(varArquivo11);
                
            /* url_liberado */
                
		/* url_liberado */
                VariavelArquivo varArquivo47 = new VariavelArquivo();
                ArquivoConfiguracao arq47 = new ArquivoConfiguracao();
                VariavelGlobal varGlobal47 = new VariavelGlobal();
                arq47.setId(12L);
                varGlobal47.setId(47L);
                varArquivo47.setArquivoConfiguracao(arq47);
                varArquivo47.setVariavelGlobal(varGlobal47);
                varArquivo47.setServidor(servidor);
                varArquivo47.setDsCampo("");
                variavelArquivoDao.add(varArquivo47);
                
            /* url_bloqueado */
                
		/* url_bloqueado */
                VariavelArquivo varArquivo48 = new VariavelArquivo();
                ArquivoConfiguracao arq48 = new ArquivoConfiguracao();
                VariavelGlobal varGlobal48 = new VariavelGlobal();
                arq48.setId(13L);
                varGlobal48.setId(48L);
                varArquivo48.setArquivoConfiguracao(arq48);
                varArquivo48.setVariavelGlobal(varGlobal48);
                varArquivo48.setServidor(servidor);
                varArquivo48.setDsCampo("");
                variavelArquivoDao.add(varArquivo48);
            
        /* DHCP */  
            /* dhcpd.conf */
                
		/* default-lease-time */
                VariavelArquivo varArquivo12 = new VariavelArquivo();
                ArquivoConfiguracao arq12 = new ArquivoConfiguracao();
                VariavelGlobal varGlobal12 = new VariavelGlobal();
                arq12.setId(5L);
                varGlobal12.setId(12L);
                varArquivo12.setArquivoConfiguracao(arq12);
                varArquivo12.setVariavelGlobal(varGlobal12);
                varArquivo12.setServidor(servidor);
                varArquivo12.setDsCampo("");
                variavelArquivoDao.add(varArquivo12);
            
		/* max-lease-time */
                VariavelArquivo varArquivo13 = new VariavelArquivo();
                ArquivoConfiguracao arq13 = new ArquivoConfiguracao();
                VariavelGlobal varGlobal13 = new VariavelGlobal();
                arq13.setId(5L);
                varGlobal13.setId(13L);
                varArquivo13.setArquivoConfiguracao(arq13);
                varArquivo13.setVariavelGlobal(varGlobal13);
                varArquivo13.setServidor(servidor);
                varArquivo13.setDsCampo("");
                variavelArquivoDao.add(varArquivo13);
            
		/* option subnet-mask */
                VariavelArquivo varArquivo14 = new VariavelArquivo();
                ArquivoConfiguracao arq14 = new ArquivoConfiguracao();
                VariavelGlobal varGlobal14 = new VariavelGlobal();
                arq14.setId(5L);
                varGlobal14.setId(14L);
                varArquivo14.setArquivoConfiguracao(arq14);
                varArquivo14.setVariavelGlobal(varGlobal14);
                varArquivo14.setServidor(servidor);
                varArquivo14.setDsCampo("");
                variavelArquivoDao.add(varArquivo14);
                
		/* option broadcast-address */
                VariavelArquivo varArquivo15 = new VariavelArquivo();
                ArquivoConfiguracao arq15 = new ArquivoConfiguracao();
                VariavelGlobal varGlobal15 = new VariavelGlobal();
                arq15.setId(5L);
                varGlobal15.setId(15L);
                varArquivo15.setArquivoConfiguracao(arq15);
                varArquivo15.setVariavelGlobal(varGlobal15);
                varArquivo15.setServidor(servidor);
                varArquivo15.setDsCampo("");
                variavelArquivoDao.add(varArquivo15);

		/* option routers */
                VariavelArquivo varArquivo16 = new VariavelArquivo();
                ArquivoConfiguracao arq16 = new ArquivoConfiguracao();
                VariavelGlobal varGlobal16 = new VariavelGlobal();
                arq16.setId(5L);
                varGlobal16.setId(16L);
                varArquivo16.setArquivoConfiguracao(arq16);
                varArquivo16.setVariavelGlobal(varGlobal16);
                varArquivo16.setServidor(servidor);
                varArquivo16.setDsCampo("");
                variavelArquivoDao.add(varArquivo16);

		/* option domain naming server */
                VariavelArquivo varArquivo17 = new VariavelArquivo();
                ArquivoConfiguracao arq17 = new ArquivoConfiguracao();
                VariavelGlobal varGlobal17 = new VariavelGlobal();
                arq17.setId(5L);
                varGlobal17.setId(17L);
                varArquivo17.setArquivoConfiguracao(arq17);
                varArquivo17.setVariavelGlobal(varGlobal17);
                varArquivo17.setServidor(servidor);
                varArquivo17.setDsCampo("");
                variavelArquivoDao.add(varArquivo17);

		/* option domain-name */
                VariavelArquivo varArquivo18 = new VariavelArquivo();
                ArquivoConfiguracao arq18 = new ArquivoConfiguracao();
                VariavelGlobal varGlobal18 = new VariavelGlobal();
                arq18.setId(5L);
                varGlobal18.setId(18L);
                varArquivo18.setArquivoConfiguracao(arq18);
                varArquivo18.setVariavelGlobal(varGlobal18);
                varArquivo18.setServidor(servidor);
                varArquivo18.setDsCampo("");
                variavelArquivoDao.add(varArquivo18);

		/* subnet */
                VariavelArquivo varArquivo19 = new VariavelArquivo();
                ArquivoConfiguracao arq19 = new ArquivoConfiguracao();
                VariavelGlobal varGlobal19 = new VariavelGlobal();
                arq19.setId(5L);
                varGlobal19.setId(19L);
                varArquivo19.setArquivoConfiguracao(arq19);
                varArquivo19.setVariavelGlobal(varGlobal19);
                varArquivo19.setServidor(servidor);
                varArquivo19.setDsCampo("");
                variavelArquivoDao.add(varArquivo19);

		/* netmask */
                VariavelArquivo varArquivo20 = new VariavelArquivo();
                ArquivoConfiguracao arq20 = new ArquivoConfiguracao();
                VariavelGlobal varGlobal20 = new VariavelGlobal();
                arq20.setId(5L);
                varGlobal20.setId(20L);
                varArquivo20.setArquivoConfiguracao(arq20);
                varArquivo20.setVariavelGlobal(varGlobal20);
                varArquivo20.setServidor(servidor);
                varArquivo20.setDsCampo("");
                variavelArquivoDao.add(varArquivo20);

		/* range */
                VariavelArquivo varArquivo21 = new VariavelArquivo();
                ArquivoConfiguracao arq21 = new ArquivoConfiguracao();
                VariavelGlobal varGlobal21 = new VariavelGlobal();
                arq21.setId(5L);
                varGlobal21.setId(21L);
                varArquivo21.setArquivoConfiguracao(arq21);
                varArquivo21.setVariavelGlobal(varGlobal21);
                varArquivo21.setServidor(servidor);
                varArquivo21.setDsCampo("");
                variavelArquivoDao.add(varArquivo21);

		/* ip fix */
                VariavelArquivo varArquivo22 = new VariavelArquivo();
                ArquivoConfiguracao arq22 = new ArquivoConfiguracao();
                VariavelGlobal varGlobal22 = new VariavelGlobal();
                arq22.setId(5L);
                varGlobal22.setId(22L);
                varArquivo22.setArquivoConfiguracao(arq22);
                varArquivo22.setVariavelGlobal(varGlobal22);
                varArquivo22.setServidor(servidor);
                varArquivo22.setDsCampo("");
                variavelArquivoDao.add(varArquivo22);

        /* Firewall */
            /* firewall.sh */
                
		/* firewall_redirect */
                VariavelArquivo varArquivo23 = new VariavelArquivo();
                ArquivoConfiguracao arq23 = new ArquivoConfiguracao();
                VariavelGlobal varGlobal23 = new VariavelGlobal();
                arq23.setId(6L);
                varGlobal23.setId(23L);
                varArquivo23.setArquivoConfiguracao(arq23);
                varArquivo23.setVariavelGlobal(varGlobal23);
                varArquivo23.setServidor(servidor);
                varArquivo23.setDsCampo("");
                variavelArquivoDao.add(varArquivo23);

		/* firewall_interface */
                VariavelArquivo varArquivo24 = new VariavelArquivo();
                ArquivoConfiguracao arq24 = new ArquivoConfiguracao();
                VariavelGlobal varGlobal24 = new VariavelGlobal();
                arq24.setId(6L);
                varGlobal24.setId(24L);
                varArquivo24.setArquivoConfiguracao(arq24);
                varArquivo24.setVariavelGlobal(varGlobal24);
                varArquivo24.setServidor(servidor);
                varArquivo24.setDsCampo("");
                variavelArquivoDao.add(varArquivo24);

		/* firewall_redeinterna */
                VariavelArquivo varArquivo25 = new VariavelArquivo();
                ArquivoConfiguracao arq25 = new ArquivoConfiguracao();
                VariavelGlobal varGlobal25 = new VariavelGlobal();
                arq25.setId(6L);
                varGlobal25.setId(25L);
                varArquivo25.setArquivoConfiguracao(arq25);
                varArquivo25.setVariavelGlobal(varGlobal25);
                varArquivo25.setServidor(servidor);
                varArquivo25.setDsCampo("");
                variavelArquivoDao.add(varArquivo25);

		/* firewall_ipexterno */
                VariavelArquivo varArquivo26 = new VariavelArquivo();
                ArquivoConfiguracao arq26 = new ArquivoConfiguracao();
                VariavelGlobal varGlobal26 = new VariavelGlobal();
                arq26.setId(6L);
                varGlobal26.setId(26L);
                varArquivo26.setArquivoConfiguracao(arq26);
                varArquivo26.setVariavelGlobal(varGlobal26);
                varArquivo26.setServidor(servidor);
                varArquivo26.setDsCampo("");
                variavelArquivoDao.add(varArquivo26);

		/* firewall_portadestino */
                VariavelArquivo varArquivo27 = new VariavelArquivo();
                ArquivoConfiguracao arq27 = new ArquivoConfiguracao();
                VariavelGlobal varGlobal27 = new VariavelGlobal();
                arq27.setId(6L);
                varGlobal27.setId(27L);
                varArquivo27.setArquivoConfiguracao(arq27);
                varArquivo27.setVariavelGlobal(varGlobal27);
                varArquivo27.setServidor(servidor);
                varArquivo27.setDsCampo("");
                variavelArquivoDao.add(varArquivo27);

        /* Rede */
            /* interfaces */
                
		/* allow-hotplug internet */
                VariavelArquivo varArquivo28 = new VariavelArquivo();
                ArquivoConfiguracao arq28 = new ArquivoConfiguracao();
                VariavelGlobal varGlobal28 = new VariavelGlobal();
                arq28.setId(7L);
                varGlobal28.setId(28L);
                varArquivo28.setArquivoConfiguracao(arq28);
                varArquivo28.setVariavelGlobal(varGlobal28);
                varArquivo28.setServidor(servidor);
                varArquivo28.setDsCampo("");
                variavelArquivoDao.add(varArquivo28);

		/* iface internet */
                VariavelArquivo varArquivo29 = new VariavelArquivo();
                ArquivoConfiguracao arq29 = new ArquivoConfiguracao();
                VariavelGlobal varGlobal29 = new VariavelGlobal();
                arq29.setId(7L);
                varGlobal29.setId(29L);
                varArquivo29.setArquivoConfiguracao(arq29);
                varArquivo29.setVariavelGlobal(varGlobal29);
                varArquivo29.setServidor(servidor);
                varArquivo29.setDsCampo("");
                variavelArquivoDao.add(varArquivo29);

		/* address internet */
                VariavelArquivo varArquivo30 = new VariavelArquivo();
                ArquivoConfiguracao arq30 = new ArquivoConfiguracao();
                VariavelGlobal varGlobal30 = new VariavelGlobal();
                arq30.setId(7L);
                varGlobal30.setId(30L);
                varArquivo30.setArquivoConfiguracao(arq30);
                varArquivo30.setVariavelGlobal(varGlobal30);
                varArquivo30.setServidor(servidor);
                varArquivo30.setDsCampo("");
                variavelArquivoDao.add(varArquivo30);

		/* netmask internet */
                VariavelArquivo varArquivo31 = new VariavelArquivo();
                ArquivoConfiguracao arq31 = new ArquivoConfiguracao();
                VariavelGlobal varGlobal31 = new VariavelGlobal();
                arq31.setId(7L);
                varGlobal31.setId(31L);
                varArquivo31.setArquivoConfiguracao(arq31);
                varArquivo31.setVariavelGlobal(varGlobal31);
                varArquivo31.setServidor(servidor);
                varArquivo31.setDsCampo("");
                variavelArquivoDao.add(varArquivo31);

		/* gateway internet */
                VariavelArquivo varArquivo32 = new VariavelArquivo();
                ArquivoConfiguracao arq32 = new ArquivoConfiguracao();
                VariavelGlobal varGlobal32 = new VariavelGlobal();
                arq32.setId(7L);
                varGlobal32.setId(32L);
                varArquivo32.setArquivoConfiguracao(arq32);
                varArquivo32.setVariavelGlobal(varGlobal32);
                varArquivo32.setServidor(servidor);
                varArquivo32.setDsCampo("");
                variavelArquivoDao.add(varArquivo32);

		/* allow-hotplug rede interna */
                VariavelArquivo varArquivo33 = new VariavelArquivo();
                ArquivoConfiguracao arq33 = new ArquivoConfiguracao();
                VariavelGlobal varGlobal33 = new VariavelGlobal();
                arq33.setId(7L);
                varGlobal33.setId(33L);
                varArquivo33.setArquivoConfiguracao(arq33);
                varArquivo33.setVariavelGlobal(varGlobal33);
                varArquivo33.setServidor(servidor);
                varArquivo33.setDsCampo("");
                variavelArquivoDao.add(varArquivo33);

		/* iface rede interna */
                VariavelArquivo varArquivo34 = new VariavelArquivo();
                ArquivoConfiguracao arq34 = new ArquivoConfiguracao();
                VariavelGlobal varGlobal34 = new VariavelGlobal();
                arq34.setId(7L);
                varGlobal34.setId(34L);
                varArquivo34.setArquivoConfiguracao(arq34);
                varArquivo34.setVariavelGlobal(varGlobal34);
                varArquivo34.setServidor(servidor);
                varArquivo34.setDsCampo("");
                variavelArquivoDao.add(varArquivo34);

		/* address rede interna */
                VariavelArquivo varArquivo35 = new VariavelArquivo();
                ArquivoConfiguracao arq35 = new ArquivoConfiguracao();
                VariavelGlobal varGlobal35 = new VariavelGlobal();
                arq35.setId(7L);
                varGlobal35.setId(35L);
                varArquivo35.setArquivoConfiguracao(arq35);
                varArquivo35.setVariavelGlobal(varGlobal35);
                varArquivo35.setServidor(servidor);
                varArquivo35.setDsCampo("");
                variavelArquivoDao.add(varArquivo35);

		/* netmask rede interna */
                VariavelArquivo varArquivo36 = new VariavelArquivo();
                ArquivoConfiguracao arq36 = new ArquivoConfiguracao();
                VariavelGlobal varGlobal36 = new VariavelGlobal();
                arq36.setId(7L);
                varGlobal36.setId(36L);
                varArquivo36.setArquivoConfiguracao(arq36);
                varArquivo36.setVariavelGlobal(varGlobal36);
                varArquivo36.setServidor(servidor);
                varArquivo36.setDsCampo("");
                variavelArquivoDao.add(varArquivo36);

		/* network rede interna */
                VariavelArquivo varArquivo37 = new VariavelArquivo();
                ArquivoConfiguracao arq37 = new ArquivoConfiguracao();
                VariavelGlobal varGlobal37 = new VariavelGlobal();
                arq37.setId(7L);
                varGlobal37.setId(37L);
                varArquivo37.setArquivoConfiguracao(arq37);
                varArquivo37.setVariavelGlobal(varGlobal37);
                varArquivo37.setServidor(servidor);
                varArquivo37.setDsCampo("");
                variavelArquivoDao.add(varArquivo37);

		/* broadcast rede interna */
                VariavelArquivo varArquivo38 = new VariavelArquivo();
                ArquivoConfiguracao arq38 = new ArquivoConfiguracao();
                VariavelGlobal varGlobal38 = new VariavelGlobal();
                arq38.setId(7L);
                varGlobal38.setId(38L);
                varArquivo38.setArquivoConfiguracao(arq38);
                varArquivo38.setVariavelGlobal(varGlobal38);
                varArquivo38.setServidor(servidor);
                varArquivo38.setDsCampo("");
                variavelArquivoDao.add(varArquivo38);
                
        /* Apache 2 */
            /* apache.conf */        
                
                /* Timeout */
                VariavelArquivo varArquivo39 = new VariavelArquivo();
                ArquivoConfiguracao arq39 = new ArquivoConfiguracao();
                VariavelGlobal varGlobal39 = new VariavelGlobal();
                arq39.setId(9L);
                varGlobal39.setId(39L);
                varArquivo39.setArquivoConfiguracao(arq39);
                varArquivo39.setVariavelGlobal(varGlobal39);
                varArquivo39.setServidor(servidor);
                varArquivo39.setDsCampo("");
                variavelArquivoDao.add(varArquivo39);
                
                /* KeepAlive */
                VariavelArquivo varArquivo40 = new VariavelArquivo();
                ArquivoConfiguracao arq40 = new ArquivoConfiguracao();
                VariavelGlobal varGlobal40 = new VariavelGlobal();
                arq40.setId(9L);
                varGlobal40.setId(40L);
                varArquivo40.setArquivoConfiguracao(arq40);
                varArquivo40.setVariavelGlobal(varGlobal40);
                varArquivo40.setServidor(servidor);
                varArquivo40.setDsCampo("");
                variavelArquivoDao.add(varArquivo40);
                
                /* MaxKeepAliveRequest */
                VariavelArquivo varArquivo41 = new VariavelArquivo();
                ArquivoConfiguracao arq41 = new ArquivoConfiguracao();
                VariavelGlobal varGlobal41 = new VariavelGlobal();
                arq41.setId(9L);
                varGlobal41.setId(41L);
                varArquivo41.setArquivoConfiguracao(arq41);
                varArquivo41.setVariavelGlobal(varGlobal41);
                varArquivo41.setServidor(servidor);
                varArquivo41.setDsCampo("");
                variavelArquivoDao.add(varArquivo41);
                
                /* KeepAliveTimeout */
                VariavelArquivo varArquivo42 = new VariavelArquivo();
                ArquivoConfiguracao arq42 = new ArquivoConfiguracao();
                VariavelGlobal varGlobal42 = new VariavelGlobal();
                arq42.setId(9L);
                varGlobal42.setId(42L);
                varArquivo42.setArquivoConfiguracao(arq42);
                varArquivo42.setVariavelGlobal(varGlobal42);
                varArquivo42.setServidor(servidor);
                varArquivo42.setDsCampo("");
                variavelArquivoDao.add(varArquivo42);
                
                /* KeepAliveTimeout */
                VariavelArquivo varArquivo43 = new VariavelArquivo();
                ArquivoConfiguracao arq43 = new ArquivoConfiguracao();
                VariavelGlobal varGlobal43 = new VariavelGlobal();
                arq43.setId(9L);
                varGlobal43.setId(43L);
                varArquivo43.setArquivoConfiguracao(arq43);
                varArquivo43.setVariavelGlobal(varGlobal43);
                varArquivo43.setServidor(servidor);
                varArquivo43.setDsCampo("");
                variavelArquivoDao.add(varArquivo43);
                
            /* ports.conf */
                
                /* NameVirtualHost */
                VariavelArquivo varArquivo44 = new VariavelArquivo();
                ArquivoConfiguracao arq44 = new ArquivoConfiguracao();
                VariavelGlobal varGlobal44 = new VariavelGlobal();
                arq44.setId(10L);
                varGlobal44.setId(44L);
                varArquivo44.setArquivoConfiguracao(arq44);
                varArquivo44.setVariavelGlobal(varGlobal44);
                varArquivo44.setServidor(servidor);
                varArquivo44.setDsCampo("");
                variavelArquivoDao.add(varArquivo44);
                
                /* Listen */
                VariavelArquivo varArquivo45 = new VariavelArquivo();
                ArquivoConfiguracao arq45 = new ArquivoConfiguracao();
                VariavelGlobal varGlobal45 = new VariavelGlobal();
                arq45.setId(10L);
                varGlobal45.setId(45L);
                varArquivo45.setArquivoConfiguracao(arq45);
                varArquivo45.setVariavelGlobal(varGlobal45);
                varArquivo45.setServidor(servidor);
                varArquivo45.setDsCampo("");
                variavelArquivoDao.add(varArquivo45);

        /* Sarg */
            /* crontab */
                VariavelArquivo varArquivo46 = new VariavelArquivo();
                ArquivoConfiguracao arq46 = new ArquivoConfiguracao();
                VariavelGlobal varGlobal46 = new VariavelGlobal();
                arq46.setId(11L);
                varGlobal46.setId(46L);
                varArquivo46.setArquivoConfiguracao(arq46);
                varArquivo46.setVariavelGlobal(varGlobal46);
                varArquivo46.setServidor(servidor);
                varArquivo46.setDsCampo("");
                variavelArquivoDao.add(varArquivo46);
    }

}
