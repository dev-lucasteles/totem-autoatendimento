package totem.mais.business;

import org.springframework.stereotype.Service;
import totem.mais.infrastructure.entities.Chamado;
import totem.mais.infrastructure.repositories.ChamadoRepository;
import java.util.List;

@Service
public class ChamadoBusiness {

    private final ChamadoRepository repository;

    public ChamadoBusiness(ChamadoRepository repository) {
        this.repository = repository;
    }

    public List<Chamado> buscarChamadosAbertos() {
        return repository.findAll();
    }

    // Agora o método retorna o Chamado completo (com o ID gerado)
    public Chamado processarNovoChamado(String nome, String setor, String problema) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("ERRO: O nome do funcionário é obrigatório.");
        }
        if (setor == null || setor.trim().isEmpty()) {
            throw new IllegalArgumentException("ERRO: Selecione o seu setor.");
        }
        if (problema == null || problema.trim().length() < 10) {
            throw new IllegalArgumentException("ERRO: Descreva o problema com pelo menos 10 caracteres.");
        }

        Chamado novoChamado = new Chamado(nome, setor, problema);
        return repository.save(novoChamado);
    }

    public void resolverChamado(Long id) {
        Chamado chamado = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Chamado não encontrado."));

        chamado.setStatus("RESOLVIDO");
        repository.save(chamado);
    }


}