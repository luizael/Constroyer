package br.com.mixfiscal.prodspedxnfe.dao.own;

import br.com.mixfiscal.prodspedxnfe.dao.util.ConstroyerHibernateUtil;
import br.com.mixfiscal.prodspedxnfe.domain.own.ClassificacaoTributaria;
import br.com.mixfiscal.prodspedxnfe.domain.own.ClassificacaoTributariaProduto;
import br.com.mixfiscal.prodspedxnfe.domain.own.IcmsEntrada;
import br.com.mixfiscal.prodspedxnfe.domain.own.IcmsSaida;
import br.com.mixfiscal.prodspedxnfe.domain.own.Cliente;
import br.com.mixfiscal.prodspedxnfe.domain.own.custom.ClassificacaoTributariaProdutoCustom;
import br.com.mixfiscal.prodspedxnfe.util.StringUtil;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import java.util.List;
import org.junit.Test;

public class AtualizarClassificacaoTributariaTeste {

    @Test
    public void PercorrerPlanoExelParaSalvarNoBDTeste() {
        String line = "";
        ClassificacaoTributaria clTributaria = null;
        ClassificacaoTributariaDAO clTribDao = new ClassificacaoTributariaDAO();

        int cont = 0;
        try {
            ConstroyerHibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
            String caminho_arquivo_xls = "D:\\LULU SOFTWARES\\MIX_FISCAL\\ProdSPEDxNFe\\trunk-old\\DOCUMENTOS\\documentacao\\exemplos\\CASTELI\\produtos_mxf.csv";
            BufferedReader br = new BufferedReader(new FileReader(caminho_arquivo_xls));
            while ((line = br.readLine()) != null) {
                cont++;
                if (cont > 2) {
                    Cliente cli = new Cliente();
                    cli.setCnpj("05587850000151");
                    
                    clTributaria = new ClassificacaoTributaria();
                    String[] row = line.split(";");
                    carregarObjetoDaImportacaoDoExelCompletoTest(clTributaria, row);
                    clTributaria.setCliente(cli);
                    clTribDao.salvar(clTributaria);
                }
            }
            ConstroyerHibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
            // System.out.println(row[0] + " - " + row[1]); }
        } catch (FileNotFoundException fe) {
            ConstroyerHibernateUtil.getSessionFactory().getCurrentSession().getTransaction().rollback();
            System.out.println(fe.getMessage());
        } catch (IOException Ie) {
            ConstroyerHibernateUtil.getSessionFactory().getCurrentSession().getTransaction().rollback();
            System.out.println(Ie.getMessage());
        } catch (NumberFormatException Ne) {
            ConstroyerHibernateUtil.getSessionFactory().getCurrentSession().getTransaction().rollback();
            System.out.println(Ne.getMessage());
        } catch (Exception e) {
            ConstroyerHibernateUtil.getSessionFactory().getCurrentSession().getTransaction().rollback();
            System.out.println("Erro ao ler e gravar" + e.getMessage() + "" + clTribDao);
        }
    }

    @Test
    public void excluir() {
        /*ClassificacaoTributaria cl = new ClassificacaoTributaria();
        ClassificacaoTributariaDAO clDao = new ClassificacaoTributariaDAO();
        ClassificacaoTributariaProdutoCustomDAO clProdCustomDao = new ClassificacaoTributariaProdutoCustomDAO();
        ClassificacaoTributariaProduto prod = new ClassificacaoTributariaProduto();
        ClassificacaoTributariaProdutoDAO prodDao = new ClassificacaoTributariaProdutoDAO();
        List<ClassificacaoTributariaProdutoCustom> lista = new ArrayList<>();

        IcmsEntradaCustomDAO icmsEntradaDao = new IcmsEntradaCustomDAO();
        IcmsSaidaCustomDAO icmsSaidaDao = new IcmsSaidaCustomDAO();
        String cnpj = "18765188000124";

        try {
            ConstroyerHibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
            lista = clProdCustomDao.listarParaExcluirCustom(cnpj);
            ConstroyerHibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
        } catch (Exception ex) {
            ConstroyerHibernateUtil.getSessionFactory().getCurrentSession().getTransaction().rollback();
        }
        try {
            ConstroyerHibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
            prodDao.excluirCustom(cnpj);
            ConstroyerHibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
        } catch (Exception ex) {
            ConstroyerHibernateUtil.getSessionFactory().getCurrentSession().getTransaction().rollback();
        }
        try {
            ConstroyerHibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
            clDao.excluirCustom(cnpj);
            ConstroyerHibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
        } catch (Exception ex) {
            ConstroyerHibernateUtil.getSessionFactory().getCurrentSession().getTransaction().rollback();
        }*/
    }

    public void carregarObjetoDaImportacaoDoExelCompletoTest(ClassificacaoTributaria objCT, String[] row) {
        try {
            ClassificacaoTributariaProduto produto = new ClassificacaoTributariaProduto();
            IcmsEntrada icmsEntrada = new IcmsEntrada();
            IcmsSaida icmsSaida = new IcmsSaida();

            try {
                produto.setEan(StringUtil.isNullOrEmpty(row[1]) ? "campo vazio" : row[1]);
            } catch (Exception ex) {
            }
            try {
                produto.setDescricao(row[2].isEmpty() ? "campo vazio" : row[2]);
            } catch (Exception ex) {
            }

            try {
                objCT.getProduto().getPisCofins().setNcm(row[9].isEmpty() ? "campo vazio" : row[9]);
            } catch (Exception ex) {
            }
            try {
                objCT.getProduto().getPisCofins().setNcmEx(row[10].isEmpty() ? "campo vazio" : row[10]);
            } catch (Exception ex) {
            }
            try {
                objCT.getProduto().getPisCofins().setCodNaturezaReceita(row[11].isEmpty() ? "campo vazio" : row[11]);
            } catch (Exception ex) {
            }

            try {
                objCT.getProduto().getPisCofins().setPisCstE(row[12].isEmpty() ? "campo vazio" : row[12]);
            } catch (Exception ex) {
            }
            try {
                objCT.getProduto().getPisCofins().setPisCstS(row[13].isEmpty() ? "campo vazio" : row[13]);
            } catch (Exception ex) {
            }

            try {
                objCT.getProduto().getPisCofins().setPisAlqE(row[14].isEmpty() ? "campo vazio" : row[14]);
            } catch (Exception ex) {
            }
            try {
                objCT.getProduto().getPisCofins().setPisAlqS(row[15].isEmpty() ? "campo vazio" : row[15]);
            } catch (Exception ex) {
            }

            try {
                objCT.getProduto().getPisCofins().setCofinsAlqE(row[16].isEmpty() ? "campo vazio" : row[16]);
            } catch (Exception ex) {
            }
            try {
                objCT.getProduto().getPisCofins().setCofinsAlqS(row[17].isEmpty() ? "campo vazio" : row[17]);
            } catch (Exception ex) {
            }

            try {
                icmsSaida.setSacCst(row[19].isEmpty() ? "campo vazio" : row[19]);
            } catch (Exception ex) {
            }
            try {
                icmsSaida.setSacAlq(row[20].isEmpty() ? "campo vazio" : row[20]);
            } catch (Exception ex) {
            }
            try {
                icmsSaida.setSacAlqst(row[21].isEmpty() ? "campo vazio" : row[21]);
            } catch (Exception ex) {
            }

            try {
                icmsSaida.setSacRbc(row[22].isEmpty() ? "campo vazio" : row[22]);
            } catch (Exception ex) {
            }
            try {
                icmsSaida.setSacRbcst(row[23].isEmpty() ? "campo vazio" : row[23]);
            } catch (Exception ex) {
            }

            try {
                icmsSaida.setSasCst(row[24].isEmpty() ? "campo vazio" : row[24]);
            } catch (Exception ex) {
            }

            try {
                icmsSaida.setSasAlq(row[25].isEmpty() ? "campo vazio" : row[25]);
            } catch (Exception ex) {
            }
            try {
                icmsSaida.setSasAlqst(row[26].isEmpty() ? "campo vazio" : row[26]);
            } catch (Exception ex) {
            }

            try {
                icmsSaida.setSasRbc(row[27].isEmpty() ? "campo vazio" : row[27]);
            } catch (Exception ex) {
            }
            try {
                icmsSaida.setSasRbcst(row[28].isEmpty() ? "campo vazio" : row[28]);
            } catch (Exception ex) {
            }

            try {
                icmsSaida.setSvcCst(row[29].isEmpty() ? "campo vazio" : row[29]);
            } catch (Exception ex) {
            }
            try {
                icmsSaida.setSvcAlq(row[30].isEmpty() ? "campo vazio" : row[30]);
            } catch (Exception ex) {
            }
            try {
                icmsSaida.setSvcAlqst(row[31].isEmpty() ? "campo vazio" : row[31]);
            } catch (Exception ex) {
            }

            try {
                icmsSaida.setSvcRbc(row[32].isEmpty() ? "campo vazio" : row[32]);
            } catch (Exception ex) {
            }
            try {
                icmsSaida.setSvcRbcst(row[33].isEmpty() ? "campo vazio" : row[33]);
            } catch (Exception ex) {
            }

            try {
                icmsSaida.setSncCst(row[34].isEmpty() ? "campo vazio" : row[34].toString());
            } catch (Exception ex) {
            }
            try {
                icmsSaida.setSncAlq(row[35].isEmpty() ? "campo vazio" : row[35].toString());
            } catch (Exception ex) {
            }
            try {
                icmsSaida.setSncAlqst(row[36].isEmpty() ? "campo vazio" : row[36].toString());
            } catch (Exception ex) {
            }

            try {
                icmsSaida.setSncRbc(row[37].isEmpty() ? "campo vazio" : row[37].toString());
            } catch (Exception ex) {
            }
            try {
                icmsSaida.setSncRbcst(row[38].isEmpty() ? "campo vazio" : row[38].toString());
            } catch (Exception ex) {
            }

            try {
                icmsSaida.setFundamentoLegal(row[40].isEmpty() ? "campo vazio" : row[40].toString());
            } catch (Exception ex) {
            }
            try {
                icmsSaida.setProduto(produto);
            } catch (Exception ex) {
            }
            ;
            try {
                icmsEntrada.setEiCst(row[41].isEmpty() ? "campo vazio" : row[41].toString());
            } catch (Exception ex) {
                objCT.getProduto().getIcmsEntrada().setEiCst("0");
            }
            try {
                icmsEntrada.setEiAlq(row[42].isEmpty() ? "campo vazio" : row[42].toString());
            } catch (Exception ex) {
                objCT.getProduto().getIcmsEntrada().setEiAlq("0");
            }
            try {
                icmsEntrada.setEiAlqst(row[43].isEmpty() ? "campo vazio" : row[43].toString());
            } catch (Exception ex) {
            }

            try {
                icmsEntrada.setEiRbc(row[44].isEmpty() ? "campo vazio" : row[44].toString());
            } catch (Exception ex) {
            }
            try {
                icmsEntrada.setEiRbcst(row[45].isEmpty() ? "campo vazio" : row[45].toString());
            } catch (Exception ex) {
            }

            try {
                icmsEntrada.setEdCst(row[46].isEmpty() ? "campo vazio" : row[46].toString());
            } catch (Exception ex) {
            }

            try {
                icmsEntrada.setEdAlq(row[47].isEmpty() ? "campo vazio" : row[47].toString());
            } catch (Exception ex) {
            }
            try {
                icmsEntrada.setEdAlqst(row[48].isEmpty() ? "campo vazio" : row[48].toString());
            } catch (Exception ex) {
            }

            try {
                icmsEntrada.setEdRbc(row[49].isEmpty() ? "campo vazio" : row[49].toString());
            } catch (Exception ex) {
            }
            try {
                icmsEntrada.setEdRbcst(row[50].isEmpty() ? "campo vazio" : row[50].toString());
            } catch (Exception ex) {
            }

            try {
                icmsEntrada.setEsCst(row[51].isEmpty() ? "campo vazio" : row[51].toString());
            } catch (Exception ex) {
            }
            try {
                icmsEntrada.setEsAlq(row[52].isEmpty() ? "campo vazio" : row[52].toString());
            } catch (Exception ex) {
            }
            try {
                icmsEntrada.setEsAlqst(row[53].isEmpty() ? "campo vazio" : row[53].toString());
            } catch (Exception ex) {
            }

            try {
                icmsEntrada.setEsRbc(row[54].isEmpty() ? "campo vazio" : row[54].toString());
            } catch (Exception ex) {
            }
            try {
                icmsEntrada.setEsRbcst(row[55].isEmpty() ? "campo vazio" : row[55].toString());
            } catch (Exception ex) {
            }

            try {
                icmsEntrada.setNfiCst(row[56].isEmpty() ? "campo vazio" : row[56].toString());
            } catch (Exception ex) {
            }
            try {
                icmsEntrada.setNfdCst(row[57].isEmpty() ? "campo vazio" : row[57].toString());
            } catch (Exception ex) {
            }
            try {
                icmsEntrada.setNfsCsosn(row[58].isEmpty() ? "campo vazio" : row[58].toString());
            } catch (Exception ex) {
            }

            try {
                icmsEntrada.setNfAlq(row[59].isEmpty() ? "campo vazio" : row[59].toString());
            } catch (Exception ex) {
            }

            try {
                icmsEntrada.setFundamentoLegal(row[39].isEmpty() ? "campo vazio" : row[39].toString());
            } catch (Exception ex) {
            }
            try {
                icmsEntrada.setTipoMva(row[60].isEmpty() ? "campo vazio" : row[60].toString());
            } catch (Exception ex) {
            }
            try {
                icmsEntrada.setMva(row[61].isEmpty() ? "campo vazio" : row[61].toString());
            } catch (Exception ex) {
            }
            try {
                icmsEntrada.setMvaDataIni(row[63].isEmpty() ? "campo vazio" : row[63].toString());
            } catch (Exception ex) {
            }
            try {
                icmsEntrada.setMvaDataFim(row[64].isEmpty() ? "campo vazio" : row[64].toString());
            } catch (Exception ex) {
            }
            try {
                icmsEntrada.setCreditoOutorgado(row[65].isEmpty() ? "campo vazio" : row[65].toString());
            } catch (Exception ex) {
            }
            try {
                icmsEntrada.setProduto(produto);
            } catch (Exception ex) {
            }
            ;

            Cliente cli = new Cliente();
            cli.setCnpj("05587850000151");
            
            objCT.setCliente(cli);            
            objCT.setProduto(produto);
            objCT.getProduto().setIcmsEntrada(icmsEntrada);
            objCT.getProduto().setIcmsSaida(icmsSaida);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void salvarCustomTeste(ClassificacaoTributaria cl) {
        Serializable idGerado = 0;
        ClassificacaoTributariaDAO classificacaoTribDAO = null;
        try {
            classificacaoTribDAO = new ClassificacaoTributariaDAO();
        } catch (Exception ex) {
            String re = ex.getMessage();
        }
        try {
            // ConstroyerHibernateUtil.beginTransaction();
            if (cl != null) {
                idGerado = classificacaoTribDAO.salvar(cl);
            }

            // if(produto != null)
            // idGerado = classificacaoTribDAO.salvar(produto);
            // ConstroyerHibernateUtil.commitCurrentTransaction();
        } catch (Exception ex) {
            // ConstroyerHibernateUtil.rollbackCurrentTransaction();
        }
    }

    @Test
    public void testarSelecionarCnpj() {
        Cliente cliente = new Cliente();
        cliente.setCnpj("27579252000173");
        ClienteDAO clienteDao = new ClienteDAO();
        try {
            cliente = clienteDao.selecionarUm(cliente);
            System.out.println("" + cliente.getNome());
        } catch (Exception ex) {
            System.out.println("" + ex.getMessage());
        }
    }
}
