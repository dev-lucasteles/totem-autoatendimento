package totem.mais.controller;

import org.springframework.stereotype.Component;
import totem.mais.business.ChamadoBusiness;
import totem.mais.infrastructure.entities.Chamado;
import java.util.List;

@Component
public class ChamadoController {

    private final ChamadoBusiness business;

    public ChamadoController(ChamadoBusiness business) {
        this.business = business;
    }

    public Chamado abrirChamado(String nome, String setor, String problema) {
        return business.processarNovoChamado(nome, setor, problema);
    }

    public List<Chamado> buscarChamadosAbertos() {
        return business.buscarChamadosAbertos();
    }

    public void resolverChamado(Long id) {
        business.resolverChamado(id);
    }


}