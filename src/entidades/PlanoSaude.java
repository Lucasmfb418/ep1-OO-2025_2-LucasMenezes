package entidades;

import java.util.HashMap;

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

    public HashMap<String, Double> getDescontos(){
        return descontos;
    }
}