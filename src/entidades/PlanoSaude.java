package entidades;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PlanoSaude{
    private String nome;
    private HashMap<String, Double> descontos;

    public PlanoSaude(String nome){
        this.nome = nome;
        this.descontos = new HashMap<>();
    }

    public String getNome(){
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