package br.com.mixfiscal.prodspedxnfe.domain.wsmix;

import java.io.Serializable;
import java.util.Objects;
import com.google.gson.annotations.SerializedName;

public class Produto implements Serializable {
    private static final long serialVersionUID = -6620479549140089817L;
    
    private String codigoProduto;
    private String ean;
    @SerializedName("pis_cofins")
    private PisCofins pisCofins;
    @SerializedName("icms_entrada")
    private IcmsEntrada icmsEntrada;
     @SerializedName("icms_saida")    
    private IcmsSaida icmsSaida;  
     
    public String getCodigoProduto() {
        return codigoProduto;
    }

    public void setCodigoProduto(String codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }
    
    public PisCofins getPisCofins() {
        return pisCofins;
    }

    public void setPisCofins(PisCofins pisCofins) {
        this.pisCofins = pisCofins;
    }
    
    public IcmsEntrada getIcmsEntrada() {
        return icmsEntrada;
    }

    public void setIcmsEntrada(IcmsEntrada icmsEntrada) {
        this.icmsEntrada = icmsEntrada;
    }
    
    public IcmsSaida getIcmsSaida() {
        return icmsSaida;
    }

    public void setIcmsSaida(IcmsSaida icmsSaida) {
        this.icmsSaida = icmsSaida;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.codigoProduto);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Produto other = (Produto) obj;
        if (!Objects.equals(this.codigoProduto, other.codigoProduto)) {
            return false;
        }
        if (!Objects.equals(this.ean, other.ean)) {
            return false;
        }
        return true;
    }
    
}
