package entidades;

import java.util.ArrayList;

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

    public void adicionarInternacao(Internacao i) {
        historicoInternacoes.add(i);
    }

    public ArrayList<Consulta> getHistoricoConsultas() {
        return historicoConsultas;
    }

    public ArrayList<Internacao> getHistoricoInternacoes() {
        return historicoInternacoes;
    }

    public double calcularDesconto(Consulta consulta) {
        // Paciente comum não tem desconto
        if(this.getIdade() >= 60) {
            return consulta.getMedico().getCusto() * 0.1; // 10% de desconto para idosos
        }
        return 0;
    }
}