package entidades;

import java.time.LocalDateTime;

public class Consulta{
    private Paciente paciente;
    private Medico medico;
    private LocalDateTime dataHora;
    private String local;
    private String status;
    private String diagnostico;
    private String prescricao;

    public Consulta(Paciente paciente, Medico medico, LocalDateTime dataHora, String local){
        this.paciente = paciente;
        this.medico = medico;
        this.dataHora = dataHora;
        this.local = local;
        this.status = "Agenda";
        this.diagnostico = "";
        this.prescricao = "";
    }

    public Paciente getPaciente(){
        return paciente;
    }

    public Medico getMedico(){
        return medico;
    }

    public LocalDateTime getDataHora(){
        return dataHora;
    }

    public String getLocal(){
        return local;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getPrescricao() {
        return prescricao;
    }

    public void setPrescricao(String prescricao) {
        this.prescricao = prescricao;
    }
}
