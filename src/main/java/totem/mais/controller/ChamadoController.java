package totem.mais.controller;

import org.springframework.stereotype.Component;
import totem.mais.business.ChamadoBusiness;
import totem.mais.infrastructure.entities.Chamado;

@Component
public class ChamadoController {
    
    private final ChamadoBusiness business;

    public ChamadoController(ChamadoBusiness business) {
        this.business = business;
    }

    public Chamado abrirChamado(String nome, String setor, String problema) {
        return business.processarNovoChamado(nome, setor, problema);
    }
}