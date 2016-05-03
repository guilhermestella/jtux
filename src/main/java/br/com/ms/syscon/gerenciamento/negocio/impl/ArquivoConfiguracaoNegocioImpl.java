/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ms.syscon.gerenciamento.negocio.impl;

import br.com.ms.syscon.gerenciamento.bean.MonitorarServidorBean;
import br.com.ms.syscon.gerenciamento.dao.ArquivoConfiguracaoDao;
import br.com.ms.syscon.gerenciamento.dominio.ArquivoConfiguracao;
import br.com.ms.syscon.gerenciamento.dominio.Servidor;
import br.com.ms.syscon.gerenciamento.dominio.VariavelArquivo;
import br.com.ms.syscon.gerenciamento.negocio.ArquivoConfiguracaoNegocio;
import br.com.ms.syscon.util.dominio.Arquivo;
import br.com.ms.syscon.util.validation.NegocioException;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class ArquivoConfiguracaoNegocioImpl implements ArquivoConfiguracaoNegocio {

    @Inject private ArquivoConfiguracaoDao arquivoConfiguracaoDao;
    
    @Override
    public void cadastrar(ArquivoConfiguracao entity) {
        arquivoConfiguracaoDao.add(entity);
    }

    @Override
    public void alterar(ArquivoConfiguracao entity) {
        arquivoConfiguracaoDao.update(entity);
    }

    @Override
    public void excluir(ArquivoConfiguracao entity) {
        arquivoConfiguracaoDao.remove(entity);
    }

    @Override
    public ArquivoConfiguracao pesquisar(Long Key) {
        return arquivoConfiguracaoDao.find(Key);
    }

    @Override
    public List<ArquivoConfiguracao> listar() {
        return arquivoConfiguracaoDao.list();
    }

    @Override
    public void enviarArquivoConfiguracao(Session session, String nmLocalArquivo, Arquivo arquivo) {
        try {
            Channel channel = session.openChannel("sftp");
            
            System.out.println("Abrindo Canal SFTP.");
            channel.connect();
            System.out.println("Conectado no Canal.");
            ChannelSftp channelSftp = (ChannelSftp)channel;
            channelSftp.cd(nmLocalArquivo);
            System.out.println("Acessando: " + nmLocalArquivo);
            channelSftp.put(new FileInputStream(arquivo), arquivo.getName());
            System.out.println("Incluído Arquivo: " + arquivo.getName());

        } catch (JSchException | SftpException | FileNotFoundException ex) {
            Logger.getLogger(ServidorNegocioImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new NegocioException("Erro ao Enviar Arquivo: " + ex);
        }
    }
    
    @Override
    public void montarBackupArquivo(Path arquivoOriginal, Path arquivoBackup) {
        System.out.println("Montando Backup");
        try {
            Files.copy(arquivoOriginal, arquivoBackup);
        } catch (IOException ex) {
            Logger.getLogger(ArquivoConfiguracaoNegocioImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Arquivo montarArquivoSquidConf(ArquivoConfiguracao arquivoConfiguracao, Servidor servidor) {
        Map<String,String>variaveis = new HashMap<String,String>();
        for ( VariavelArquivo var : servidor.getListVariavelArquivo() ){
            System.out.println("ID: " + var.getVariavelGlobal().getNmDescricao() + " Valor: " + var.getDsCampo());
            variaveis.put(var.getVariavelGlobal().getNmDescricao(), var.getDsCampo());
        }
        
        try {
            Arquivo arquivo = new Arquivo(arquivoConfiguracao.getNmDescricao());
            arquivo.setNmLocalArquivoRemoto(arquivoConfiguracao.getNmLocalArquivo());
            
            PrintWriter writer = new PrintWriter(arquivo);
            
            writer.println("## PORTA ##");
            writer.println("http_port " + variaveis.get("http_port") +" transparent");
            writer.println();
            writer.println("visible_hostname " + variaveis.get("visible_hostname"));
            writer.println("cache_mgr " + variaveis.get("cache_mgr"));
            writer.println("error_directory " + variaveis.get("error_directory"));
            writer.println("hierarchy_stoplist cgi-bin ?");
            writer.println("cache_mem " + variaveis.get("cache_mem"));
            writer.println("maximum_object_size_in_memory " + variaveis.get("maximum_object_size_in_memory"));
            writer.println("maximum_object_size " + variaveis.get("maximum_object_size"));
            writer.println("cache_dir ufs /var/spool/squid3 2040 16 256");
            writer.println();
            writer.println("refresh_pattern ^ftp: 360 20% 10080");
            writer.println("refresh_pattern -i (/cgi-bin/|\\?) 0 0% 0");
            writer.println("refresh_pattern . 0 20% 4320");
            writer.println();
            writer.println("access_log /var/log/squid3/access.log");
            writer.println();
            writer.println("acl localhost src 127.0.0.1/32");
            writer.println("acl localnet src 192.168.254.0/24");
            writer.println();
            writer.println("#############################################");
            writer.println("## PAGINA DE ERRO EM PORTUGUES");
            writer.println("error_directory /usr/share/squid3/errors/pt-br");
            writer.println("#############################################");
            writer.println();
            writer.println("# A ACL abaixo barra download de arquivos com extensÃµexe mp3 wma wmv mpg avi asf");
            writer.println("acl block_arq urlpath_regex -i " + variaveis.get("acl block_arq urlpath_regex -i"));
            writer.println();
            writer.println("#QUEM");
            writer.println("acl g_full_access src \"/etc/squid3/lib/g_full_access\"");
            writer.println("acl g_mid_access  src \"/etc/squid3/lib/g_mid_access\"");
            writer.println("acl g_no_access   src \"/etc/squid3/lib/g_no_access\"");
            writer.println();
            writer.println("#O QUE");
            writer.println("acl url_bloqueado url_regex  -i  \"/etc/squid3/lib/url_bloqueado\"");
            writer.println("acl url_liberado  url_regex  -i  \"/etc/squid3/lib/url_liberado\"");
            writer.println();
            writer.println("#QUEM PODE O QUE");
            writer.println("http_access allow g_full_access");
            writer.println("http_access deny  g_mid_access   url_bloqueado");
            writer.println("http_access deny  all");
            writer.println();
            writer.println("acl purge method PURGE");
            writer.println("http_access allow purge localhost");
            writer.println("http_access deny purge");
            writer.println();
            writer.println("acl Safe_ports port 21    # ftp");
            writer.println("acl Safe_ports port 70    # gopher");
            writer.println("acl Safe_ports port 80    # http");
            writer.println("acl Safe_ports port 210    # wais");
            writer.println("acl Safe_ports port 280    # http-mgmt");
            writer.println("acl Safe_ports port 443    # https");
            writer.println("acl Safe_ports port 488    # gss-http");
            writer.println("acl Safe_ports port 563    # mntps");
            writer.println("acl Safe_ports port 591    # filemaker");
            writer.println("acl Safe_ports port 633    # cups");
            writer.println("acl Safe_ports port 777    # multiling http");
            writer.println("acl Safe_ports port 873    # rsync");
            writer.println("acl Safe_ports port 901    # swat");
            writer.println("acl Safe_ports port 1025-65535    # unregistered ports");
            writer.println("http_access deny !Safe_ports");
            writer.println();
            writer.println("acl connect method CONNECT");
            writer.println("acl ssl_ports port 443    # https");
            writer.println("acl ssl_ports port 563    # mntps");
            writer.println("acl ssl_ports port 873    # rsync");
            writer.println("http_access deny connect !SSL_ports");

            writer.close();
            return arquivo;
        } 
      catch (FileNotFoundException ex) {
            Logger.getLogger(MonitorarServidorBean.class.getName()).log(Level.SEVERE, null, ex);
      }
        return null;
    }

    @Override
    public Arquivo montarArquivoGFullAccess(ArquivoConfiguracao arquivoConfiguracao, Servidor servidor) {
        Map<String,String>variaveis = new HashMap<String,String>();
        for ( VariavelArquivo var : servidor.getListVariavelArquivo() ){
            System.out.println("ID: " + var.getVariavelGlobal().getNmDescricao() + " Valor: " + var.getDsCampo());
            variaveis.put(var.getVariavelGlobal().getNmDescricao(), var.getDsCampo());
        }
        
        try {
            Arquivo arquivo = new Arquivo(arquivoConfiguracao.getNmDescricao());
            arquivo.setNmLocalArquivoRemoto(arquivoConfiguracao.getNmLocalArquivo());
            
            PrintWriter writer = new PrintWriter(arquivo);
            
            String stringGroupFullAccess = variaveis.get("group_full_access");
            String stringUrls[] = stringGroupFullAccess.split(";");
            for ( String linha : stringUrls ){
                try{
                    writer.println(linha);
                }catch(RuntimeException e){
                    throw new NegocioException("Erro na Regra de Group Full Access! Siga o Padrão da Descrição.");
                }
            }

            writer.close();
            return arquivo;
        } 
      catch (FileNotFoundException ex) {
            Logger.getLogger(MonitorarServidorBean.class.getName()).log(Level.SEVERE, null, ex);
      }
        return null;
    }

    @Override
    public Arquivo montarArquivoGMidAccess(ArquivoConfiguracao arquivoConfiguracao, Servidor servidor) {
        Map<String,String>variaveis = new HashMap<String,String>();
        for ( VariavelArquivo var : servidor.getListVariavelArquivo() ){
            System.out.println("ID: " + var.getVariavelGlobal().getNmDescricao() + " Valor: " + var.getDsCampo());
            variaveis.put(var.getVariavelGlobal().getNmDescricao(), var.getDsCampo());
        }
        
        try {
            Arquivo arquivo = new Arquivo(arquivoConfiguracao.getNmDescricao());
            arquivo.setNmLocalArquivoRemoto(arquivoConfiguracao.getNmLocalArquivo());
            
            PrintWriter writer = new PrintWriter(arquivo);

            String stringGroupMidAccess = variaveis.get("group_mid_access");
            String stringUrls[] = stringGroupMidAccess.split(";");
            for ( String linha : stringUrls ){
                try{
                    writer.println(linha);
                }catch(RuntimeException e){
                    throw new NegocioException("Erro na Regra de Group Mid Access! Siga o Padrão da Descrição.");
                }
            }

            writer.close();
            return arquivo;
        } 
      catch (FileNotFoundException ex) {
            Logger.getLogger(MonitorarServidorBean.class.getName()).log(Level.SEVERE, null, ex);
      }
        return null;
    }

    @Override
    public Arquivo montarArquivoGNoAccess(ArquivoConfiguracao arquivoConfiguracao, Servidor servidor) {
        Map<String,String>variaveis = new HashMap<String,String>();
        for ( VariavelArquivo var : servidor.getListVariavelArquivo() ){
            System.out.println("ID: " + var.getVariavelGlobal().getNmDescricao() + " Valor: " + var.getDsCampo());
            variaveis.put(var.getVariavelGlobal().getNmDescricao(), var.getDsCampo());
        }
        
        try {
            Arquivo arquivo = new Arquivo(arquivoConfiguracao.getNmDescricao());
            arquivo.setNmLocalArquivoRemoto(arquivoConfiguracao.getNmLocalArquivo());
            
            PrintWriter writer = new PrintWriter(arquivo);
            
            String stringGroupNoAccess = variaveis.get("group_no_access");
            String stringUrls[] = stringGroupNoAccess.split(";");
            for ( String linha : stringUrls ){
                try{
                    writer.println(linha);
                }catch(RuntimeException e){
                    throw new NegocioException("Erro na Regra de Group No Access! Siga o Padrão da Descrição.");
                }
            }

            writer.close();
            return arquivo;
        } 
      catch (FileNotFoundException ex) {
            Logger.getLogger(MonitorarServidorBean.class.getName()).log(Level.SEVERE, null, ex);
      }
        return null;
    }

    @Override
    public Arquivo montarArquivoDHCPConf(ArquivoConfiguracao arquivoConfiguracao, Servidor servidor) {
        Map<String,String>variaveis = new HashMap<String,String>();
        for ( VariavelArquivo var : servidor.getListVariavelArquivo() ){
            System.out.println("ID: " + var.getVariavelGlobal().getNmDescricao() + " Valor: " + var.getDsCampo());
            variaveis.put(var.getVariavelGlobal().getNmDescricao(), var.getDsCampo());
        }
        
        try {
            Arquivo arquivo = new Arquivo(arquivoConfiguracao.getNmDescricao());
            arquivo.setNmLocalArquivoRemoto(arquivoConfiguracao.getNmLocalArquivo());
            
            PrintWriter writer = new PrintWriter(arquivo);

            writer.println("authoritative;");
            writer.println("default-lease-time "+ variaveis.get("default-lease-time") +";");
            writer.println("max-lease-time "+ variaveis.get("max-lease-time") +";");
            writer.println("option subnet-mask "+ variaveis.get("option subnet-mask") +";");
            writer.println("option broadcast-address "+ variaveis.get("option broadcast-address") +";");
            writer.println("option routers "+ variaveis.get("option routers") +";");
            writer.println("option domain-name-servers "+ variaveis.get("option domain naming server") +";");
            writer.println();
            writer.println("option domain-name \""+ variaveis.get("option domain-name") +"\";");
            writer.println("subnet "+ variaveis.get("subnet") +" netmask "+ variaveis.get("netmask") +" {range "+ variaveis.get("range") +";}");
            writer.println();
            
            
            
            String stringIpfix = variaveis.get("ip fix");
            String ipfixLinha[] = stringIpfix.split(";");
            
            
            for ( String linha : ipfixLinha ){
                try{
                    String[] ipfixElemento = linha.split("-");
                    String host = ipfixElemento[0];
                    String mac = ipfixElemento[1];
                    String ip = ipfixElemento[2];
                    writer.println("host "+ host +"{hardware ethernet "+ mac +"; fixed-address "+ ip +";}");
                }catch(RuntimeException e){
                    throw new NegocioException("Erro na Regra de Vínculo de Host-Mac-Ip! Siga o Padrão da Descrição.");
                }
            }
            
            
            writer.close();
            return arquivo;
        } 
      catch (FileNotFoundException ex) {
            Logger.getLogger(MonitorarServidorBean.class.getName()).log(Level.SEVERE, null, ex);
      }
        return null;
    }

    @Override
    public Arquivo montarArquivoFirewallSh(ArquivoConfiguracao arquivoConfiguracao, Servidor servidor) {
        Map<String,String>variaveis = new HashMap<String,String>();
        for ( VariavelArquivo var : servidor.getListVariavelArquivo() ){
            System.out.println("ID: " + var.getVariavelGlobal().getNmDescricao() + " Valor: " + var.getDsCampo());
            variaveis.put(var.getVariavelGlobal().getNmDescricao(), var.getDsCampo());
        }
        
        try {
            Arquivo arquivo = new Arquivo(arquivoConfiguracao.getNmDescricao());
            arquivo.setNmLocalArquivoRemoto(arquivoConfiguracao.getNmLocalArquivo());
            
            PrintWriter writer = new PrintWriter(arquivo);

            writer.println("#!/bin/bash");
            writer.println();
            writer.println("######## REGRAS DE FIREWALL ######");
            writer.println("echo Habilitando Firewall...");
            writer.println();
            writer.println("####### LIMPANDO TODAS AS REGRAS IPTABLES ############");
            writer.println("/sbin/iptables -F");
            writer.println("/sbin/iptables -X");
            writer.println("/sbin/iptables -t nat -F");
            writer.println("/sbin/iptables -t nat -X");
            writer.println("/sbin/iptables -t mangle -F");
            writer.println("/sbin/iptables -t mangle -X");
            writer.println("/sbin/iptables -L -n");
            writer.println();
            writer.println("###### ATIVA O SISTEMA DE ROTEAMENTO DE PACOTES #########");
            writer.println("echo 1 > /proc/sys/net/ipv4/ip_forward");
            writer.println();
            writer.println("####### ATIVA O MODO DE MASQUERADE ######################");
            writer.println("#iptables -t nat -A POSTROUTING -o eth0 -j MASQUERADE");
            writer.println();
            writer.println("########### Logando input, output,forward ###############");
            writer.println("#iptables -A INPUT -j LOG");
            writer.println("#iptables -A OUTPUT -j LOG");
            writer.println("#iptables -A FORWARD -j LOG");
            writer.println();
            writer.println("########### IPs SEM PROXY ################################");
            writer.println("# REDE INTERNA");
            writer.println("#iptables -t nat -I PREROUTING -s 192.168.0.0/16 -p tcp --dport 80 -j ACCEPT");
            writer.println();
            writer.println("######################## MASQUERADE ############################");
            writer.println("#iptables -t nat -A POSTROUTING -s 192.168.0.0/16 -j MASQUERADE");
            writer.println();
            writer.println("########### FIM DE REGRA IPS SEM PROXY ###################");
            writer.println();
            writer.println("################################## REDIRECIONAMENTO DE PORTA ################################");
            writer.println("################################## REDIRECIONAMENTO DE PORTA ################################");
            writer.println("#############################################################################################");
            writer.println();
            writer.println();
            
                String stringFirewallRedirect = variaveis.get("firewall_redirect");
                String firewallRedirectLinha[] = stringFirewallRedirect.split(";");
                for ( String linha : firewallRedirectLinha ){
                    try{
                        String[] firewallRedirectElemento = linha.split("-");
                        String porta = firewallRedirectElemento[0];
                        String ip = firewallRedirectElemento[1];
                        writer.println("iptables -t nat -A PREROUTING -i " + variaveis.get("firewall_interface") + " -p tcp  --dport " + porta + " -j DNAT --to " + ip + "");
                    }catch(RuntimeException e){
                        throw new NegocioException("Erro na Regra de Redirecionamento de Porta! Siga o Padrão da Descrição.");
                    }
                }

            writer.println();
            writer.println();
            writer.println("################################## FIM REDIRECIONAMENTO DE PORTA#############################");
            writer.println();
            writer.println();
            writer.println("#################################### PROTECAO CONTRA ATAQUES ###################################");
            writer.println();
            writer.println("#################################### PROTECAO CONTRA IP SPOOFING ###############################");
            writer.println("echo 1 > /proc/sys/net/ipv4/conf/all/rp_filter");
            writer.println();
            writer.println("########################### DROPA PACOTES TCP INDESEJAVEIS #####################################");
            writer.println("iptables -A FORWARD -p tcp ! --syn -m state --state NEW -j LOG --log-level 6 --log-prefix \"FIREWALL: NEW sem syn: \"");
            writer.println("iptables -A FORWARD -p tcp ! --syn -m state --state NEW -j DROP");
            writer.println();
            writer.println("########################### DROPA PACOTES MAL FORMADOS ##########################################");
            writer.println("iptables -A INPUT -i eth0 -m unclean -j LOG --log-level 6 --log-prefix \"FIREWALL: pacote mal formado: \"");
            writer.println("iptables -A INPUT -i eth0 -m unclean -j DROP");
            writer.println();
            writer.println("################################### PROTECAO CONTRA TRINOO ######################################");
            writer.println("iptables -N TRINOO");
            writer.println("iptables -A TRINOO -m limit --limit 15/m -j LOG --log-level 6 --log-prefix \"FIREWALL: trinoo: \"");
            writer.println("iptables -A TRINOO -j DROP");
            writer.println("iptables -A INPUT -p TCP -i eth0 --dport 27444 -j TRINOO");
            writer.println("iptables -A INPUT -p TCP -i eth0 --dport 27665 -j TRINOO");
            writer.println("iptables -A INPUT -p TCP -i eth0 --dport 31335 -j TRINOO");
            writer.println("iptables -A INPUT -p TCP -i eth0 --dport 34555 -j TRINOO");
            writer.println("iptables -A INPUT -p TCP -i eth0 --dport 35555 -j TRINOO");
            writer.println();
            writer.println("################################# PROTECAO CONTRA TROJANS ######################################");
            writer.println("iptables -N TROJAN");
            writer.println("iptables -A TROJAN -m limit --limit 15/m -j LOG --log-level 6 --log-prefix \"FIREWALL: trojan: \"");
            writer.println("iptables -A TROJAN -j DROP");
            writer.println("iptables -A INPUT -p TCP -i eth0 --dport 666 -j TROJAN");
            writer.println("iptables -A INPUT -p TCP -i eth0 --dport 666 -j TROJAN");
            writer.println("iptables -A INPUT -p TCP -i eth0 --dport 4000 -j TROJAN");
            writer.println("iptables -A INPUT -p TCP -i eth0 --dport 6000 -j TROJAN");
            writer.println("iptables -A INPUT -p TCP -i eth0 --dport 6006 -j TROJAN");
            writer.println("iptables -A INPUT -p TCP -i eth0 --dport 16660 -j TROJAN");
            writer.println();
            writer.println("############################### PROTECAO CONTRA WORMS ##########################################");
            writer.println("iptables -A FORWARD -p tcp --dport 135 -i eth1 -j REJECT");
            writer.println();
            writer.println("################################## PROTECAO CONTRA SYN-FLOOD ###################################");
            writer.println("iptables -A FORWARD -p tcp --syn -m limit --limit 2/s -j ACCEPT");
            writer.println();
            writer.println("################################# PROTECAO CONTRA PING DA MORTE ################################");
            writer.println("iptables -A FORWARD -p icmp --icmp-type echo-request -m limit --limit 1/s -j ACCEPT");
            writer.println();
            writer.println("############################### PROTECAO CONTRA PORT SCANNERS ##################################");
            writer.println("iptables -N SCANNER");
            writer.println("iptables -A SCANNER -m limit --limit 15/m -j LOG --log-level 6 --log-prefix \"FIREWALL: port scanner: \"");
            writer.println("iptables -A SCANNER -j DROP");
            writer.println("iptables -A INPUT -p tcp --tcp-flags ALL FIN,URG,PSH -i eth0 -j SCANNER");
            writer.println("iptables -A INPUT -p tcp --tcp-flags ALL NONE -i eth0 -j SCANNER");
            writer.println("iptables -A INPUT -p tcp --tcp-flags ALL ALL -i eth0 -j SCANNER");
            writer.println("iptables -A INPUT -p tcp --tcp-flags ALL FIN,SYN -i eth0 -j SCANNER");
            writer.println("iptables -A INPUT -p tcp --tcp-flags ALL SYN,RST,ACK,FIN,URG -i eth0 -j SCANNER");
            writer.println("iptables -A INPUT -p tcp --tcp-flags SYN,RST SYN,RST -i eth0 -j SCANNER");
            writer.println("iptables -A INPUT -p tcp --tcp-flags SYN,FIN SYN,FIN -i eth0 -j SCANNER");
            writer.println("###############################################################################################");
            writer.println("iptables -t nat -A POSTROUTING -o " + variaveis.get("firewall_interface") + " -j MASQUERADE");
            writer.println("iptables -t nat -A PREROUTING -s " + variaveis.get("firewall_redeinterna") + " -p tcp --dport 80 -j REDIRECT --to-port " + variaveis.get("firewall_portadestino") + "");
            writer.println("iptables -t nat -A POSTROUTING -s " + variaveis.get("firewall_redeinterna") + " -o eth0 -j SNAT --to " + variaveis.get("firewall_ipexterno") + "");
            writer.println("iptables -A INPUT -i eth0 -p tcp --dport 111 -j DROP");
            writer.println("iptables -A INPUT -i eth0 -p tcp --dport 60661 -j DROP");

            writer.close();
            return arquivo;
        } 
      catch (FileNotFoundException ex) {
            Logger.getLogger(MonitorarServidorBean.class.getName()).log(Level.SEVERE, null, ex);
      }
        return null;
    }

    @Override
    public Arquivo montarArquivoInterfaces(ArquivoConfiguracao arquivoConfiguracao, Servidor servidor) {
        Map<String,String>variaveis = new HashMap<String,String>();
        for ( VariavelArquivo var : servidor.getListVariavelArquivo() ){
            System.out.println("ID: " + var.getVariavelGlobal().getNmDescricao() + " Valor: " + var.getDsCampo());
            variaveis.put(var.getVariavelGlobal().getNmDescricao(), var.getDsCampo());
        }
        
        if ( !(variaveis.get("iface internet").toLowerCase().equals("dhcp") || variaveis.get("iface internet").toLowerCase().equals("static")) ){
            throw new NegocioException("iface internet tem que ser dhcp ou static!");
        }
        
        if ( !(variaveis.get("iface rede interna").toLowerCase().equals("dhcp") || variaveis.get("iface rede interna").toLowerCase().equals("static")) ){
            throw new NegocioException("iface internet tem que ser dhcp ou static!");
        }
        
        try {
            Arquivo arquivo = new Arquivo(arquivoConfiguracao.getNmDescricao());
            arquivo.setNmLocalArquivoRemoto(arquivoConfiguracao.getNmLocalArquivo());
            
            PrintWriter writer = new PrintWriter(arquivo);

            writer.println("# This file describes the network interfaces available on your syste");
            writer.println("# and how to activate them. For more information, see interfaces(5).");
            writer.println();
            writer.println("# The loopback network interface");
            writer.println("auto lo");
            writer.println("iface lo inet loopback");
            writer.println();
            writer.println("# interface de internet");
            writer.println("allow-hotplug " + variaveis.get("allow-hotplug internet") + "");
            writer.println("iface " + variaveis.get("allow-hotplug internet") + " inet " + variaveis.get("iface internet") + "");
            if ( variaveis.get("iface internet").toLowerCase().equals("static") ){
                writer.println("address " + variaveis.get("address internet") + "");
                writer.println("netmask " + variaveis.get("netmask internet") + "");
                if ( variaveis.get("gateway internet").isEmpty() || variaveis.get("gateway internet").equals("") )
                    writer.println("");
                else
                    writer.println("gateway " + variaveis.get("gateway internet") + "");
            }
            writer.println();
            writer.println();
            writer.println("# interface de rede interna");
            writer.println("allow-hotplug " + variaveis.get("allow-hotplug rede interna") + "");
            writer.println("iface " + variaveis.get("allow-hotplug rede interna") + " inet " + variaveis.get("iface rede interna") + "");
            if ( variaveis.get("iface rede interna").toLowerCase().equals("static") ){
                writer.println("address " + variaveis.get("address rede interna") + "");
                writer.println("netmask " + variaveis.get("netmask rede interna") + "");
                writer.println("network " + variaveis.get("network rede interna") + "");
                writer.println("broadcast " + variaveis.get("broadcast rede interna") + "");
            }
            
            writer.close();
            return arquivo;
        } 
      catch (FileNotFoundException ex) {
            Logger.getLogger(MonitorarServidorBean.class.getName()).log(Level.SEVERE, null, ex);
      }
        return null;
    }

    @Override
    public Arquivo montarArquivoSourcesList(ArquivoConfiguracao arquivoConfiguracao, Servidor servidor) {
        Map<String,String>variaveis = new HashMap<String,String>();
        for ( VariavelArquivo var : servidor.getListVariavelArquivo() ){
            System.out.println("ID: " + var.getVariavelGlobal().getNmDescricao() + " Valor: " + var.getDsCampo());
            variaveis.put(var.getVariavelGlobal().getNmDescricao(), var.getDsCampo());
        }
        
        try {
            Arquivo arquivo = new Arquivo(arquivoConfiguracao.getNmDescricao());
            arquivo.setNmLocalArquivoRemoto(arquivoConfiguracao.getNmLocalArquivo());
            
            PrintWriter writer = new PrintWriter(arquivo);

            writer.println("### Repositórios padrão.");
            writer.println("deb http://http.debian.net/debian/ jessie main non-free contrib");
            writer.println("#deb-src http://http.debian.net/debian/ jessie main non-free contrib");
            writer.println("");
            writer.println("### Atualizações de Segurança.");
            writer.println("deb http://security.debian.org/ jessie/updates main contrib non-free");
            writer.println("#deb-src http://security.debian.org/ jessie/updates main contrib non-free");
            writer.println("");
            writer.println("### Volatile (softwares atualizados com frequência, Clamav, etc)");
            writer.println("deb http://http.debian.net/debian/ jessie-updates main contrib non-free");
            writer.println("#deb-src http://http.debian.net/debian/ jessie-updates main contrib non-free");
            writer.println("");
            writer.println("### Backports (no momento)");
            writer.println("deb http://http.debian.net/debian/ jessie-backports main contrib non-free");
            writer.println("#deb-src http://http.debian.net/debian/ jessie-backports main contrib non-free");
            writer.println("");

            writer.close();
            return arquivo;
        } 
      catch (FileNotFoundException ex) {
            Logger.getLogger(MonitorarServidorBean.class.getName()).log(Level.SEVERE, null, ex);
      }
        return null;
    }

    @Override
    public Arquivo montarArquivoApacheConf(ArquivoConfiguracao arquivoConfiguracao, Servidor servidor) {
        Map<String,String>variaveis = new HashMap<String,String>();
        for ( VariavelArquivo var : servidor.getListVariavelArquivo() ){
            System.out.println("ID: " + var.getVariavelGlobal().getNmDescricao() + " Valor: " + var.getDsCampo());
            variaveis.put(var.getVariavelGlobal().getNmDescricao(), var.getDsCampo());
        }

        try {
            Arquivo arquivo = new Arquivo(arquivoConfiguracao.getNmDescricao());
            arquivo.setNmLocalArquivoRemoto(arquivoConfiguracao.getNmLocalArquivo());
            
            PrintWriter writer = new PrintWriter(arquivo);

            writer.println("#LockFile ${APACHE_LOCK_DIR}/accept.lock");
            writer.println();
            writer.println("PidFile ${APACHE_PID_FILE}");
            writer.println();
            writer.println("Timeout " + variaveis.get("Timeout") + "");
            writer.println();
            writer.println("KeepAlive " + variaveis.get("KeepAlive") + "");
            writer.println();
            writer.println("MaxKeepAliveRequests " + variaveis.get("MaxKeepAliveRequest") + "");
            writer.println();
            writer.println("KeepAliveTimeout " + variaveis.get("KeepAliveTimeout") + "");
            writer.println();
            writer.println("<IfModule mpm_prefork_module>");
            writer.println("    StartServers          5");
            writer.println("    MinSpareServers       5");
            writer.println("    MaxSpareServers      10");
            writer.println("    MaxClients          150");
            writer.println("    MaxRequestsPerChild   0");
            writer.println("</IfModule>");
            writer.println();
            writer.println("<IfModule mpm_worker_module>");
            writer.println("    StartServers          2");
            writer.println("    MinSpareThreads      25");
            writer.println("    MaxSpareThreads      75 ");
            writer.println("    ThreadLimit          64");
            writer.println("    ThreadsPerChild      25");
            writer.println("    MaxClients          150");
            writer.println("    MaxRequestsPerChild   0");
            writer.println("</IfModule>");
            writer.println();
            writer.println("<IfModule mpm_event_module>");
            writer.println("    StartServers          2");
            writer.println("    MinSpareThreads      25");
            writer.println("    MaxSpareThreads      75");
            writer.println("    ThreadLimit          64");
            writer.println("    ThreadsPerChild      25");
            writer.println("    MaxClients          150");
            writer.println("    MaxRequestsPerChild   0");
            writer.println("</IfModule>");
            writer.println();
            writer.println("# These need to be set in /etc/apache2/envvars");
            writer.println("User ${APACHE_RUN_USER}");
            writer.println("Group ${APACHE_RUN_GROUP}");
            writer.println();
            writer.println("AccessFileName .htaccess");
            writer.println();
            writer.println("<Files ~ \"^\\.ht\">");
            writer.println("    Order allow,deny");
            writer.println("    Deny from all");
            writer.println("    Satisfy all");
            writer.println("</Files>");
            writer.println();
            writer.println("DefaultType None");
            writer.println();
            writer.println("HostnameLookups Off");
            writer.println();
            writer.println("ErrorLog ${APACHE_LOG_DIR}/error.log");
            writer.println();
            writer.println("LogLevel warn");
            writer.println();
            writer.println("# Include module configuration:");
            writer.println("Include mods-enabled/*.load");
            writer.println("Include mods-enabled/*.conf");
            writer.println();
            writer.println("# Include list of ports to listen on and which to use for name based vhosts");
            writer.println("Include ports.conf");
            writer.println();
            writer.println("LogFormat \"%v:%p %h %l %u %t \\\"%r\\\" %>s %O \\\"%{Referer}i\\\" \\\"%{User-Agent}i\\\"\" vhost_combined");
            writer.println("LogFormat \"%h %l %u %t \\\"%r\\\" %>s %O \\\"%{Referer}i\\\" \\\"%{User-Agent}i\\\"\" combined");
            writer.println("LogFormat \"%h %l %u %t \\\"%r\\\" %>s %O\" common");
            writer.println("LogFormat \"%{Referer}i -> %U\" referer");
            writer.println("LogFormat \"%{User-agent}i\" agent");
            writer.println();
            writer.println("# Include generic snippets of statements");
            writer.println("Include conf-enabled/");
            writer.println();
            writer.println("# Include the virtual host configurations:");
            writer.println("Include sites-enabled/");
            writer.println();
            writer.println("ServerRoot \"/etc/apache2/\"");
            writer.println();
            writer.println("ServerName " + variaveis.get("ServerName") + "");

            writer.close();
            return arquivo;
        } 
      catch (FileNotFoundException ex) {
            Logger.getLogger(MonitorarServidorBean.class.getName()).log(Level.SEVERE, null, ex);
      }
        return null;
    }

    @Override
    public Arquivo montarArquivoPortsConf(ArquivoConfiguracao arquivoConfiguracao, Servidor servidor) {
        Map<String,String>variaveis = new HashMap<String,String>();
        for ( VariavelArquivo var : servidor.getListVariavelArquivo() ){
            System.out.println("ID: " + var.getVariavelGlobal().getNmDescricao() + " Valor: " + var.getDsCampo());
            variaveis.put(var.getVariavelGlobal().getNmDescricao(), var.getDsCampo());
        }

        try {
            Arquivo arquivo = new Arquivo(arquivoConfiguracao.getNmDescricao());
            arquivo.setNmLocalArquivoRemoto(arquivoConfiguracao.getNmLocalArquivo());
            
            PrintWriter writer = new PrintWriter(arquivo);

            writer.println("# If you just change the port or add more ports here, you will likely also");
            writer.println("# have to change the VirtualHost statement in");
            writer.println("# /etc/apache2/sites-enabled/000-default");
            writer.println("# This is also true if you have upgraded from before 2.2.9-3 (i.e. from");
            writer.println("# Debian etch). See /usr/share/doc/apache2.2-common/NEWS.Debian.gz and");
            writer.println("# README.Debian.gz");
            writer.println("");
            writer.println("NameVirtualHost " + variaveis.get("NameVirtualHost") + "");
            writer.println("Listen " + variaveis.get("Listen") + "");
            writer.println("");
            writer.println("<IfModule mod_ssl.c>");
            writer.println("    # If you add NameVirtualHost *:443 here, you will also have to change");
            writer.println("    # the VirtualHost statement in /etc/apache2/sites-available/default-ssl");
            writer.println("    # to <VirtualHost *:443>");
            writer.println("    # Server Name Indication for SSL named virtual hosts is currently not");
            writer.println("    # supported by MSIE on Windows XP.");
            writer.println("    Listen 443");
            writer.println("</IfModule>");
            writer.println("");
            writer.println("<IfModule mod_gnutls.c>");
            writer.println("    Listen 443");
            writer.println("</IfModule>");

            writer.close();
            return arquivo;
        } 
      catch (FileNotFoundException ex) {
            Logger.getLogger(MonitorarServidorBean.class.getName()).log(Level.SEVERE, null, ex);
      }
        return null;
    }

    @Override
    public Arquivo montarArquivoCrontab(ArquivoConfiguracao arquivoConfiguracao, Servidor servidor) {
        Map<String,String>variaveis = new HashMap<String,String>();
        for ( VariavelArquivo var : servidor.getListVariavelArquivo() ){
            System.out.println("ID: " + var.getVariavelGlobal().getNmDescricao() + " Valor: " + var.getDsCampo());
            variaveis.put(var.getVariavelGlobal().getNmDescricao(), var.getDsCampo());
        }

        try {
            Arquivo arquivo = new Arquivo(arquivoConfiguracao.getNmDescricao());
            arquivo.setNmLocalArquivoRemoto(arquivoConfiguracao.getNmLocalArquivo());
            
            PrintWriter writer = new PrintWriter(arquivo);

            writer.println("# /etc/crontab: system-wide crontab");
            writer.println("# Unlike any other crontab you don't have to run the `crontab'");
            writer.println("# command to install the new version when you edit this file");
            writer.println("# and files in /etc/cron.d. These files also have username fields,");
            writer.println("# that none of the other crontabs do.");
            writer.println("");
            writer.println("SHELL=/bin/sh");
            writer.println("PATH=/usr/local/sbin:/usr/local/bin:/sbin:/bin:/usr/sbin:/usr/bin");
            writer.println("");
            writer.println("# m h dom mon dow user	command");
            writer.println("17 *	* * *	root    cd / && run-parts --report /etc/cron.hourly");
            writer.println("25 6	* * *	root	test -x /usr/sbin/anacron || ( cd / && run-parts --report /etc/cron.daily )");
            writer.println("47 6	* * 7	root	test -x /usr/sbin/anacron || ( cd / && run-parts --report /etc/cron.weekly )");
            writer.println("52 6	1 * *	root	test -x /usr/sbin/anacron || ( cd / && run-parts --report /etc/cron.monthly )");
            writer.println("#");
            writer.println("");
            writer.println("00 " + variaveis.get("cron_hora") + "   * * *   root    sarg");

            writer.close();
            return arquivo;
        } 
      catch (FileNotFoundException ex) {
            Logger.getLogger(MonitorarServidorBean.class.getName()).log(Level.SEVERE, null, ex);
      }
        return null;
    }

    @Override
    public Arquivo montarArquivoUrlLiberado(ArquivoConfiguracao arquivoConfiguracao, Servidor servidor) {
        Map<String,String>variaveis = new HashMap<String,String>();
        for ( VariavelArquivo var : servidor.getListVariavelArquivo() ){
            System.out.println("ID: " + var.getVariavelGlobal().getNmDescricao() + " Valor: " + var.getDsCampo());
            variaveis.put(var.getVariavelGlobal().getNmDescricao(), var.getDsCampo());
        }

        try {
            Arquivo arquivo = new Arquivo(arquivoConfiguracao.getNmDescricao());
            arquivo.setNmLocalArquivoRemoto(arquivoConfiguracao.getNmLocalArquivo());
            
            PrintWriter writer = new PrintWriter(arquivo);

            String stringUrlLiberado = variaveis.get("url_liberado");
            String stringUrls[] = stringUrlLiberado.split(";");
            for ( String linha : stringUrls ){
                try{
                    writer.println(linha);
                }catch(RuntimeException e){
                    throw new NegocioException("Erro na Regra de URL's Liberadas! Siga o Padrão da Descrição.");
                }
            }

            writer.close();
            return arquivo;
        } 
      catch (FileNotFoundException ex) {
            Logger.getLogger(MonitorarServidorBean.class.getName()).log(Level.SEVERE, null, ex);
      }
        return null;
    }

    @Override
    public Arquivo montarArquivoUrlBloqueado(ArquivoConfiguracao arquivoConfiguracao, Servidor servidor) {
        Map<String,String>variaveis = new HashMap<String,String>();
        for ( VariavelArquivo var : servidor.getListVariavelArquivo() ){
            System.out.println("ID: " + var.getVariavelGlobal().getNmDescricao() + " Valor: " + var.getDsCampo());
            variaveis.put(var.getVariavelGlobal().getNmDescricao(), var.getDsCampo());
        }

        try {
            Arquivo arquivo = new Arquivo(arquivoConfiguracao.getNmDescricao());
            arquivo.setNmLocalArquivoRemoto(arquivoConfiguracao.getNmLocalArquivo());
            
            PrintWriter writer = new PrintWriter(arquivo);

            String stringUrlBloqueado = variaveis.get("url_bloqueado");
            String stringUrls[] = stringUrlBloqueado.split(";");
            for ( String linha : stringUrls ){
                try{
                    writer.println(linha);
                }catch(RuntimeException e){
                    throw new NegocioException("Erro na Regra de URL's Bloqueadas! Siga o Padrão da Descrição.");
                }
            }

            writer.close();
            return arquivo;
        } 
      catch (FileNotFoundException ex) {
            Logger.getLogger(MonitorarServidorBean.class.getName()).log(Level.SEVERE, null, ex);
      }
        return null;
    }

}
