package entidades;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Medico extends Pessoa {
    private String crm;
    private String especialidade;
    private double custo;
    private ArrayList<Consulta> agenda;

    public Medico(String nome, String crm, String especialidade, double custo){
        super(nome, null, 0);
        this.crm = crm;
        this.especialidade = especialidade;
        this.custo = custo;
        this.agenda = new ArrayList<>();
    }

    public String getCrm(){
        return crm;
    }

    public String getEspecialidade(){
        return especialidade;
    }

    public double getCusto(){
        return custo;
    }

   public List<Consulta> getAgenda(){
        return Collections.unmodifiableList(agenda);
    }

    public void adicionarConsulta(Consulta c){
        agenda.add(c);
    }
}
