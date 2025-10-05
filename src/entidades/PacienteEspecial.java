package entidades;

public class PacienteEspecial extends Paciente {
    private PlanoSaude plano;

    public PacienteEspecial(String nome, String cpf, int idade, PlanoSaude plano){
        super(nome, cpf, idade);
        this.plano = plano;
    }

    public PlanoSaude getPlano(){
        return plano;
    }

    @Override
    public double calcularDesconto(Consulta consulta) {
        double descontoPlano = 0;
        double custoConsulta = consulta.getMedico().getCusto(); // Pega o custo total

        if (plano !=  null) {
            double percentualDesconto = plano.getDesconto(consulta.getMedico().getEspecialidade());
            descontoPlano = custoConsulta * (percentualDesconto / 100.0);
        }
        double descontoIdoso = getIdade() >= 60 ? custoConsulta * 0.1 : 0;
        return descontoPlano + descontoIdoso;
    }
}
