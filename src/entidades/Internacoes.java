package entidades;

import java.time.LocalDateTime;

public class Internacoes {
    private Paciente paciente;
    private Medico medicoResponsavel;
    private LocalDateTime dataEntrada;
    private LocalDateTime dataSaida;
    private String quarto;
    private double custo;
    private String status;

    public Internacoes(Paciente paciente, Medico medico, LocalDateTime entrada, LocalDateTime saida, String quarto, double custo) {
        this.paciente = paciente;
        this.medicoResponsavel = medico;
        this.dataEntrada = entrada;
        this.dataSaida = saida;
        this.quarto = quarto;
        this.custo = custo;
        this.status = "Ativa";
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public Medico getMedicoResponsavel() {
        return medicoResponsavel;
    }

    public LocalDateTime getDataEntrada() {
        return dataEntrada;
    }

    public LocalDateTime getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(LocalDateTime dataSaida) {
        this.dataSaida = dataSaida;
    }

    public String getQuarto() {
        return quarto;
    }

    public double getCusto() {
        return custo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
