package servicos;

import java.util.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import entidades.*;

public class Relatorios {

    public static void gerarMenuRelatorios(ArrayList<Paciente> pacientes, ArrayList<Medico> medicos,
                                           ArrayList<Consulta> consultas, ArrayList<Internacao> internacoes,
                                           ArrayList<PlanoSaude> planos, Scanner sc) {
        int opcao = -1;
        while(opcao != 0) {
            System.out.println("\n--- Menu Relatórios ---");
            System.out.println("1. Pacientes cadastrados");
            System.out.println("2. Médicos cadastrados");
            System.out.println("3. Consultas futuras e passadas");
            System.out.println("4. Pacientes internados no momento");
            System.out.println("5. Estatísticas gerais");
            System.out.println("6. Pessoas por plano de saúde");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch(opcao) {
                case 1: listarPacientes(pacientes); break;
                case 2: listarMedicos(medicos); break;
                case 3: listarConsultas(consultas, sc); break;
                case 4: listarInternados(internacoes); break;
                case 5: gerarEstatisticas(medicos, consultas); break;
                case 6: pessoasPorPlano(planos, pacientes); break;
                case 0: break;
                default: System.out.println("Opção inválida!"); break;
            }
        }
    }

    private static void listarPacientes(ArrayList<Paciente> pacientes) {
        System.out.println("\n--- Pacientes Cadastrados ---");
        for(Paciente p : pacientes) {
            System.out.println("Nome: " + p.getNome() + ", CPF: " + p.getCpf() + ", Idade: " + p.getIdade());
            System.out.println("Histórico Consultas: " + p.getHistoricoConsultas().size() + " consultas");
            System.out.println("Histórico Internações: " + p.getHistoricoInternacoes().size() + " internações\n");
        }
    }

    private static void listarMedicos(ArrayList<Medico> medicos) {
        System.out.println("\n--- Médicos Cadastrados ---");
        for(Medico m : medicos) {
            System.out.println("Nome: " + m.getNome() + ", CRM: " + m.getCrm() + ", Especialidade: " + m.getEspecialidade());
            System.out.println("Consultas realizadas: " + m.getAgenda().size() + "\n");
        }
    }

    private static void listarConsultas(ArrayList<Consulta> consultas, Scanner sc) {
        System.out.println("\n--- Consultas ---");
        System.out.println("Filtrar por: 1-Paciente 2-Médico 3-Especialidade 0-Sem filtro");
        int filtro = sc.nextInt();
        sc.nextLine();

        for(Consulta c : consultas) {
            boolean exibir = false;
            switch(filtro) {
                case 0: exibir = true; break;
                case 1:
                    System.out.print("Digite CPF do paciente: ");
                    String cpf = sc.nextLine();
                    exibir = c.getPaciente().getCpf().equals(cpf); break;
                case 2:
                    System.out.print("Digite CRM do médico: ");
                    String crm = sc.nextLine();
                    exibir = c.getMedico().getCrm().equals(crm); break;
                case 3:
                    System.out.print("Digite especialidade: ");
                    String esp = sc.nextLine();
                    exibir = c.getMedico().getEspecialidade().equalsIgnoreCase(esp); break;
            }
            if(exibir) {
                System.out.println("Paciente: " + c.getPaciente().getNome() +
                        ", Médico: " + c.getMedico().getNome() +
                        ", Data/Hora: " + c.getDataHora().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")) +
                        ", Status: " + c.getStatus());
            }
        }
    }

    private static void listarInternados(ArrayList<Internacao> internacoes) {
        System.out.println("\n--- Pacientes Internados ---");
        for(Internacao i : internacoes) {
            if(i.getStatus().equals("Ativa")) {
                Duration dur = Duration.between(i.getDataEntrada(), LocalDateTime.now());
                long dias = dur.toDays();
                System.out.println("Paciente: " + i.getPaciente().getNome() +
                        ", Quarto: " + i.getQuarto() +
                        ", Dias de internação: " + dias);
            }
        }
    }

    private static void gerarEstatisticas(ArrayList<Medico> medicos, ArrayList<Consulta> consultas) {
        System.out.println("\n--- Estatísticas Gerais ---");

        Medico top = null;
        int max = 0;
        for(Medico m : medicos) {
            int atendimentos = 0;
            for(Consulta c : consultas) {
                if(c.getMedico().equals(m)) atendimentos++;
            }
            if(atendimentos > max) {
                max = atendimentos;
                top = m;
            }
        }
        if(top != null) System.out.println("Médico que mais atendeu: " + top.getNome() + " (" + max + " consultas)");

        Map<String, Integer> contagemEsp = new HashMap<>();
        for(Consulta c : consultas) {
            contagemEsp.put(c.getMedico().getEspecialidade(), contagemEsp.getOrDefault(c.getMedico().getEspecialidade(),0)+1);
        }
        String topEsp = contagemEsp.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse("Nenhuma");
        System.out.println("Especialidade mais procurada: " + topEsp);
    }

    private static void pessoasPorPlano(ArrayList<PlanoSaude> planos, ArrayList<Paciente> pacientes) {
        System.out.println("\n--- Pessoas por Plano de Saúde ---");
        for(PlanoSaude p : planos) {
            long count = pacientes.stream().filter(pa -> pa instanceof PacienteEspecial && ((PacienteEspecial)pa).getPlano().equals(p)).count();
            System.out.println("Plano: " + p.getNome() + ", Quantidade de pessoas: " + count);
        }
    }
}
