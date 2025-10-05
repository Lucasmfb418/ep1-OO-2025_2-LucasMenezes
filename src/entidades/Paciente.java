package entidades;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Paciente extends Pessoa {
    private ArrayList<Consulta> historicoConsultas;
    private ArrayList<Internacao> historicoInternacoes;

    public Paciente(String nome, String cpf, int idade) {
        super(nome, cpf, idade);
        this.historicoConsultas = new ArrayList<>();
        this.historicoInternacoes = new ArrayList<>();
    }

    public void adicionarConsulta(Consulta c) {
        historicoConsultas.add(c);
    }

    public void adicionarInternacoes(Internacao i) {
        historicoInternacoes.add(i);
    }

    public List<Consulta> getHistoricoConsultas() {
        return Collections.unmodifiableList(historicoConsultas);
    }

    public List<Internacao> getHistoricoInternacoes() {
        return Collections.unmodifiableList(historicoInternacoes);
    }

    public double calcularDesconto(Consulta consulta) {
        if(this.getIdade() >= 60) {
            return consulta.getMedico().getCusto() * 0.1;
        }
        return 0;
    }
}