package entidades;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PlanoSaude{
    private String nome;
    private HashMap<String, Double> descontos;
    private boolean planoEspecialDeInternacao;


    public PlanoSaude(String nome){
        this.nome = nome;
        this.descontos = new HashMap<>();
        this.planoEspecialDeInternacao = false;
    }

    public boolean isPlanoEspecialDeInternacao() {
        return planoEspecialDeInternacao;
    }

    public void setPlanoEspecialDeInternacao(boolean planoEspecialDeInternacao) {
        this.planoEspecialDeInternacao = planoEspecialDeInternacao;
    }

    public String getNome() {
        return nome;
    }
    
    public void adicionarDesconto(String especialidade, double desconto){
        descontos.put(especialidade, desconto);
    }

    public double getDesconto(String especialidade){
        return descontos.getOrDefault(especialidade, 0.0);
    }

     public Map<String, Double> getDescontos(){
        return Collections.unmodifiableMap(descontos);
    }
}