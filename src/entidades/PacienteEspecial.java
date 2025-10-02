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
        if (plano !=  null) {
            descontoPlano = plano.getDesconto(consulta.getMedico().getEspecialidade());
        }
        double descontoIdoso = getIdade() >= 60 ? consulta.getMedico().getCusto() * 0.1 : 0;
        return descontoPlano + descontoIdoso;
    }
}
