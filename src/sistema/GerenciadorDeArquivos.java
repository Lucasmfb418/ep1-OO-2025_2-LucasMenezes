package sistema;

import entidades.*;

import java.util.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GerenciadorDeArquivos {

    private static final DateTimeFormatter FORMATADOR_DATA_HORA = DateTimeFormatter.ofPattern("yyyy-MM-dd");

public static ArrayList<Paciente> carregarPacientes(String cam, ArrayList<PlanoSaude> planos) {
    ArrayList<Paciente> lista = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(cam))) {
        String linha;
        while((linha = br.readLine()) != null) {
            String[] dados = linha.split(";");
            String tipo = dados[0];
            String nome = dados[1];
            String cpf = dados[2];
            int idade = Integer.parseInt(dados[3]);
            if(tipo.equals("Especial")) {
                String nomePlano = dados[4];
                PlanoSaude planoEncontrado = planos.stream()
                                                  .filter(pl -> pl.getNome().equals(nomePlano))
                                                  .findFirst()
                                                  .orElse(null);
                lista.add(new PacienteEspecial(nome, cpf, idade, planoEncontrado));
            } else {
                lista.add(new Paciente(nome, cpf, idade));
            }
        }
    } catch (IOException e) {
        System.out.println("Arquivo de pacientes não encontrado, será criado ao salvar.");
    }
    return lista;
}

public static void salvarPacientes(String cam, ArrayList<Paciente> pacientes) {
    try (PrintWriter pw = new PrintWriter(new FileWriter(cam))) {
        for(Paciente p : pacientes) {
            if (p instanceof PacienteEspecial) {
                PacienteEspecial pe = (PacienteEspecial) p;
                pw.println("Especial;" + p.getNome() + ";" + p.getCpf() + ";" + p.getIdade() + ";" + pe.getPlano().getNome());
            } else {
                pw.println("Comum;" + p.getNome() + ";" + p.getCpf() + ";" + p.getIdade());
            }
        }
    } catch (IOException e) {
        System.out.println("Erro ao salvar pacientes: " + e.getMessage());
    }
}

    public static ArrayList<Medico> carregarMedicos(String cam) {
        ArrayList<Medico> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(cam))) {
            String linha;
            while((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                String nome = dados[0];
                String crm = dados[1];
                String especialidade = dados[2];
                double custo = Double.parseDouble(dados[3]);
                lista.add(new Medico(nome, crm, especialidade, custo));
            }
        } catch (IOException e) {
            System.out.println("Arquivo de médicos não encontrado, será criado ao salvar.");
        }
        return lista;
    }

    public static void salvarMedicos(String cam, ArrayList<Medico> medicos) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(cam))) {
            for(Medico m : medicos) {
                pw.println(m.getNome() + ";" + m.getCrm() + ";" + m.getEspecialidade() + ";" + m.getCusto());
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar médicos: " + e.getMessage());
        }
    }

    public static ArrayList<PlanoSaude> carregarPlanos(String cam) {
        ArrayList<PlanoSaude> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(cam))) {
            String linha;
            while((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                PlanoSaude plano = new PlanoSaude(dados[0]);
                for(int i=1;i<dados.length - 1; i +=2){
                    if (i + 1 < dados.length - 1) {
                        String especialidade = dados[i];
                        double desconto = Double.parseDouble(dados[i+1]);
                        plano.adicionarDesconto(especialidade, desconto);
                    }
                }   
                boolean ehEspecial = Boolean.parseBoolean(dados[dados.length - 1]);
                plano.setPlanoEspecialDeInternacao(ehEspecial);
                lista.add(plano);
            }
        } catch (IOException e) {
            System.out.println("Arquivo de planos não encontrado, será criado ao salvar.");
        }
        return lista;
    }

    public static void salvarPlanos(String cam, ArrayList<PlanoSaude> planos) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(cam))) {
            for(PlanoSaude p : planos) {
                StringBuilder sb = new StringBuilder();
                sb.append(p.getNome());
                for(Map.Entry<String, Double> entry : p.getDescontos().entrySet()) {
                    sb.append(";").append(entry.getKey()).append(";").append(entry.getValue());
                }
                sb.append(";").append(p.isPlanoEspecialDeInternacao());
                pw.println(sb.toString());
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar planos: " + e.getMessage());
        }
    }

    public static ArrayList<Consulta> carregarConsultas(String cam, ArrayList<Paciente> pacientes, ArrayList<Medico> medicos) {
        ArrayList<Consulta> lista = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(cam))){
            String linha;
            while((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                String cpfPaciente = dados[0];
                String crmMedico = dados[1];
                LocalDateTime dt = LocalDateTime.parse(dados[2], FORMATADOR_DATA_HORA);
                String local = dados[3];
                String status = dados[4];
                String diag = dados[5];
                String presc = dados[6];

                Paciente p = pacientes.stream().filter(pa -> pa.getCpf().equals(cpfPaciente)).findFirst().orElse(null);
                Medico m = medicos.stream().filter(me -> me.getCrm().equals(crmMedico)).findFirst().orElse(null);
                if(p!=null && m!=null){
                    Consulta c = new Consulta(p, m, dt, local);
                    c.setStatus(status);
                    c.setDiagnostico(diag);
                    c.setPrescricao(presc);
                    lista.add(c);
                }
            }
        } catch(IOException e){
            System.out.println("Arquivo de consultas não encontrado, será criado ao salvar.");
        }
        return lista;
    }

    public static void salvarConsultas(String cam, ArrayList<Consulta> consultas){
        try(PrintWriter pw = new PrintWriter(new FileWriter(cam))){
            for(Consulta c : consultas){
                pw.println(c.getPaciente().getCpf() + ";" + c.getMedico().getCrm() + ";" + c.getDataHora().format(FORMATADOR_DATA_HORA) + ";" + c.getLocal() + ";" + c.getStatus() + ";" + c.getDiagnostico() + ";" + c.getPrescricao());
            }
        } catch(IOException e){
            System.out.println("Erro ao salvar consultas: " + e.getMessage());
        }
    }

    public static ArrayList<Internacao> carregarInternacoes(String cam, ArrayList<Paciente> pacientes, ArrayList<Medico> medicos){
        ArrayList<Internacao> lista = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(cam))){
            String linha;
            while((linha = br.readLine())!=null){
                String[] dados = linha.split(";");
                String cpfPaciente = dados[0];
                String crmMedico = dados[1];
                LocalDateTime entrada = LocalDateTime.parse(dados[2], FORMATADOR_DATA_HORA);
                LocalDateTime saida = dados[3].equals("null") ? null : LocalDateTime.parse(dados[3], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                String quarto = dados[4];
                double custo = Double.parseDouble(dados[5]);
                String status = dados[6];

                Paciente p = pacientes.stream().filter(pa -> pa.getCpf().equals(cpfPaciente)).findFirst().orElse(null);
                Medico m = medicos.stream().filter(me -> me.getCrm().equals(crmMedico)).findFirst().orElse(null);
                if(p!=null && m!=null){
                    Internacao i = new Internacao(p, m, entrada, saida, quarto, custo);
                    i.setStatus(status);
                    lista.add(i);
                }
            }
        } catch(IOException e){
            System.out.println("Arquivo de internações não encontrado, será criado ao salvar.");
        }
        return lista;
    }

    public static void salvarInternacoes(String cam, ArrayList<Internacao> internacoes){
        try(PrintWriter pw = new PrintWriter(new FileWriter(cam))){
            for(Internacao i : internacoes){
                pw.println(i.getPaciente().getCpf() + ";" + i.getMedicoResponsavel().getCrm() + ";" + i.getDataEntrada().format(FORMATADOR_DATA_HORA) + ";" + (i.getDataSaida()==null ? "null" : i.getDataSaida().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))) + ";" + i.getQuarto() + ";" + i.getCusto() + ";" + i.getStatus());
            }
        } catch(IOException e){
            System.out.println("Erro ao salvar internações: " + e.getMessage());
        }
    }
}
