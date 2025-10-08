package servicos;

import java.util.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import entidades.*;

public class Relatorios {

    public static void listarPacientes(ArrayList<Paciente> pacientes) {
        System.out.println("\n--- Pacientes Cadastrados ---");
        for(Paciente p : pacientes) {
            System.out.println("Nome: " + p.getNome() + ", CPF: " + p.getCpf() + ", Idade: " + p.getIdade());
            System.out.println("Histórico Consultas: " + p.getHistoricoConsultas().size() + " consultas");
            System.out.println("Histórico Internações: " + p.getHistoricoInternacoes().size() + " internações\n");
        }
    }

    public static void listarMedicos(ArrayList<Medico> medicos) {
        System.out.println("\n--- Médicos Cadastrados ---");
        for(Medico m : medicos) {
            System.out.println("Nome: " + m.getNome() + ", CRM: " + m.getCrm() + ", Especialidade: " + m.getEspecialidade());
            System.out.println("Consultas realizadas: " + m.getAgenda().size() + "\n");
        }
    }

    public static void listarConsultas(ArrayList<Consulta> consultas, Scanner sc) {
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

    public static void listarInternados(ArrayList<Internacao> internacoes) {
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

    public static void gerarEstatisticas(ArrayList<Medico> medicos, ArrayList<Consulta> consultas) {
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

    public static void pessoasPorPlano(ArrayList<PlanoSaude> planos, ArrayList<Paciente> pacientes) {
        System.out.println("\n--- Pessoas por Plano de Saúde ---");
        for(PlanoSaude p : planos) {
            long count = pacientes.stream().filter(pa -> pa instanceof PacienteEspecial && ((PacienteEspecial)pa).getPlano().equals(p)).count();
            double totalEconomizado = 0;
            for (Paciente pac : pacientes) {
                if (pac instanceof PacienteEspecial && ((PacienteEspecial) pac).getPlano().equals(p)) {
                    for (Consulta c : pac.getHistoricoConsultas()) {
                        totalEconomizado += c.getValorEconomizado();
                    }
                }
            }

            System.out.println("Plano: " + p.getNome());
            System.out.println("  - Quantidade de pessoas: " + count);
            System.out.printf("  - Total economizado pelos usuários: R$ %.2f\n\n", totalEconomizado);
        }
    }

    public static void gerarEstatisticasAvancadas(ArrayList<Internacao> internacoes, ArrayList<Medico> medicos) {
        System.out.println("\n--- Estatísticas Avançadas ---");

        long totalDias = 0;
        int internacoesEncerradas = 0;
        for (Internacao i : internacoes) {
            if (i.getStatus().equals("Encerrada") && i.getDataSaida() != null) {
                Duration duracao = Duration.between(i.getDataEntrada(), i.getDataSaida());
                totalDias += duracao.toDays();
                internacoesEncerradas++;
            }
        }

        if (internacoesEncerradas > 0) {
            double mediaDias = (double) totalDias / internacoesEncerradas;
            System.out.printf("Tempo médio de internação (dias): %.2f\n", mediaDias);
        } else {
            System.out.println("Tempo médio de internação (dias): Não há internações encerradas para calcular.");
        }

        System.out.println("\nOcupação atual por especialidade (pacientes internados):");
        Map<String, Integer> ocupacaoPorEspecialidade = new HashMap<>();
        for (Internacao i : internacoes) {
            if (i.getStatus().equals("Ativa")) {
                String especialidade = i.getMedicoResponsavel().getEspecialidade();
                ocupacaoPorEspecialidade.put(especialidade, ocupacaoPorEspecialidade.getOrDefault(especialidade, 0) + 1);
            }
        }

        if (ocupacaoPorEspecialidade.isEmpty()) {
            System.out.println("  - Nenhum paciente internado no momento.");
        } else {
            for (Map.Entry<String, Integer> entry : ocupacaoPorEspecialidade.entrySet()) {
                System.out.println("  - " + entry.getKey() + ": " + entry.getValue() + " paciente(s)");
            }
        }
    }
}
