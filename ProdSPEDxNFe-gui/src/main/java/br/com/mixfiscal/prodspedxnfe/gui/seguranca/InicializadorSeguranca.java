package br.com.mixfiscal.prodspedxnfe.gui.seguranca;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

public class InicializadorSeguranca extends AbstractSecurityWebApplicationInitializer {
    public InicializadorSeguranca() {
        super(ConfiguracaoSeguranca.class);
    }
}
