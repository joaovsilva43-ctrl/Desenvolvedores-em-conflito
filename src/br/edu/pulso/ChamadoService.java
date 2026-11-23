package br.edu.pulso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ChamadoService {
    private static final List<Chamado> DADOS = Arrays.asList(
        new Chamado(101, "Impressora parou de imprimir", "Infraestrutura", "Alta", "Aberto"),
        new Chamado(102, "Atualizar cadastro de fornecedor", "Administrativo", "Baixa", "Concluído"),
        new Chamado(103, "Tela de acesso apresenta erro", "Sistemas", "Alta", "Em andamento"),
        new Chamado(104, "Wi-Fi do laboratório instável", "Infraestrutura", "Média", "Aberto"),
        new Chamado(105, "Ajustar relatório de vendas", "Sistemas", "Média", "Concluído"),
        new Chamado(106, "Novo colaborador sem acesso", "Administrativo", "Alta", "Aberto"),
        new Chamado(107, "Computador não inicia", "Infraestrutura", "Média", "Em andamento"),
        new Chamado(108, "Trocar mouse da recepção", "Administrativo", "Baixa", "Aberto")
    );

    public List<Chamado> listar(Map<String, String> parametros) {
        List<Chamado> resultado = new ArrayList<>(DADOS);
        String status = parametros.getOrDefault("status", "");
        if (!status.isEmpty()) {
            resultado.removeIf(c -> !c.getStatus().equals(status));
        }
        return resultado;
    }

    public List<Chamado> todos() {
        return new ArrayList<>(DADOS);
    }
}
