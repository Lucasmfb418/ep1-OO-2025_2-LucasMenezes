package servicos;

import java.util.ArrayList;
import java.util.Scanner;
import entidades.*;
import sistema.GerenciadorDeArquivos;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.Duration;

public class Hospital {
    private ArrayList<Paciente> pacientes;
    private ArrayList<Medico> medicos;
    private ArrayList<PlanoSaude> planos;
    private ArrayList<Consulta> consultas;
    private ArrayList<Internacao> internacoes;
    
    private final String camPacientes = "data/pacientes.txt";
    private final String camMedicos = "data/medicos.txt";
    private final String camPlanos = "data/planos.txt";
    private final String camConsultas = "data/consultas.txt";
    private final String camInternacoes = "data/internacoes.txt";

    public Hospital(){
        pacientes = new ArrayList<>();
        medicos = new ArrayList<>();
        planos = new ArrayList<>();
        consultas = new ArrayList<>();
        internacoes = new ArrayList<>();
    }

    public void carregarDados(){
        planos = GerenciadorDeArquivos.carregarPlanos(camPlanos);
        pacientes = GerenciadorDeArquivos.carregarPacientes(camPacientes, planos);
        medicos = GerenciadorDeArquivos.carregarMedicos(camMedicos);
        consultas = GerenciadorDeArquivos.carregarConsultas(camConsultas, pacientes, medicos);
        internacoes = GerenciadorDeArquivos.carregarInternacoes(camInternacoes, pacientes, medicos);
    }

    public void salvarDados(){
        GerenciadorDeArquivos.salvarPacientes(camPacientes, pacientes);
        GerenciadorDeArquivos.salvarMedicos(camMedicos, medicos);
        GerenciadorDeArquivos.salvarPlanos(camPlanos, planos);
        GerenciadorDeArquivos.salvarConsultas(camConsultas, consultas);
        GerenciadorDeArquivos.salvarInternacoes(camInternacoes, internacoes);
    }

    public void cadastrarPaciente(Scanner sc) {
        System.out.println("--- Cadastro de Paciente ---");
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("CPF: ");
        String cpf = sc.nextLine();

        int idade = 0;
        boolean entradaValida = false;
        while (!entradaValida) {
            try {
                System.out.print("Idade: ");
                idade = sc.nextInt();
                sc.nextLine();
                entradaValida = true;
            } catch (java.util.InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número para a idade.");
                sc.nextLine();
            }
        }

        System.out.println("O paciente possui um plano de saúde? (s/n): ");
        String temPlano = sc.nextLine();
        
        if(temPlano.equalsIgnoreCase("s")) {
            System.out.println("Planos disponíveis:");
            for(int i=0; i<planos.size(); i++){
                System.out.println(i + ". " + planos.get(i).getNome());
            }
            System.out.print("Escolha o plano pelo índice: ");
            int idx = sc.nextInt();
            sc.nextLine();
            PacienteEspecial p = new PacienteEspecial(nome, cpf, idade, planos.get(idx));
            pacientes.add(p);
            System.out.println("Paciente especial cadastrado com sucesso!");
        } else {
            Paciente p = new Paciente(nome, cpf, idade);
            pacientes.add(p);
            System.out.println("Paciente cadastrado com sucesso!");
        }
    }

    public void cadastrarMedico(Scanner sc) {
        System.out.println("\n--- Cadastro de Médico ---");
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("CRM: ");
        String crm = sc.nextLine();
        System.out.print("Especialidade: ");
        String especialidade = sc.nextLine();
        System.out.print("Custo da consulta: ");
        double custo = sc.nextDouble();
        sc.nextLine();

        Medico m = new Medico(nome, crm, especialidade, custo);
        medicos.add(m);
        System.out.println("Médico cadastrado com sucesso!");
    }

    public void cadastrarPlano(Scanner sc) {
        System.out.println("\n--- Cadastro de Plano de Saúde ---");
        System.out.print("Nome do plano: ");
        String nome = sc.nextLine();
        PlanoSaude plano = new PlanoSaude(nome);
        while (true) {
            System.out.print("Deseja adicionar um desconto para uma especialidade? (s/n): ");
            if (sc.nextLine().equalsIgnoreCase("n")) {
                break;
            }
            System.out.print("Digite a especialidade: ");
            String especialidade = sc.nextLine();
            System.out.print("Digite o percentual de desconto (ex: 20 para 20%): ");
            double desconto = sc.nextDouble();
            sc.nextLine();
            plano.adicionarDesconto(especialidade, desconto);
        }
    
        System.out.print("Este plano oferece internação gratuita por até 7 dias? (s/n): ");
        String resposta = sc.nextLine();
        if (resposta.equalsIgnoreCase("s")) {
            plano.setPlanoEspecialDeInternacao(true);
        }

        planos.add(plano);
        System.out.println("Plano de saúde cadastrado com sucesso!");
    }

    public void agendarConsulta(Scanner sc) {
        System.out.println("\n--- Agendar Consulta ---");

        if (pacientes.isEmpty() || medicos.isEmpty()) {
            System.out.println("É necessário cadastrar pacientes e médicos primeiro!");
            return;
        }

        System.out.println("Pacientes:");
        for (int i = 0; i < pacientes.size(); i++) {
            System.out.println(i + ". " + pacientes.get(i).getNome());
        }

        Paciente pacienteSelecionado = null;
        while (pacienteSelecionado == null) {
            try {
                System.out.print("Escolha o paciente pelo índice: ");
                int idxPaciente = sc.nextInt();
                sc.nextLine();
                if (idxPaciente >= 0 && idxPaciente < pacientes.size()) {
                    pacienteSelecionado = pacientes.get(idxPaciente);
                } else {
                    System.out.println("Índice inválido. Por favor, escolha um número da lista.");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                sc.nextLine();
            }
        }

        System.out.println("Médicos:");
        for (int i = 0; i < medicos.size(); i++) {
            System.out.println(i + ". " + medicos.get(i).getNome() + " (" + medicos.get(i).getEspecialidade() + ")");
        }

        Medico medicoSelecionado = null;
        while (medicoSelecionado == null) {
            try {
                System.out.print("Escolha o médico pelo índice: ");
                int idxMedico = sc.nextInt();
                sc.nextLine();
                if (idxMedico >= 0 && idxMedico < medicos.size()) {
                    medicoSelecionado = medicos.get(idxMedico);
                } else {
                    System.out.println("Índice inválido. Por favor, escolha um número da lista.");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                sc.nextLine();
            }
        }

        System.out.print("Data e hora (dd-MM-yyyy HH:mm): ");
        String dataHoraStr = sc.nextLine();
        LocalDateTime dataHora = LocalDateTime.parse(dataHoraStr, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));

        System.out.print("Local da consulta: ");
        String local = sc.nextLine();

        for (Consulta c : consultas) {
            if (c.getMedico().equals(medicoSelecionado) && c.getDataHora().equals(dataHora)) {
                System.out.println("Erro: Esse médico já possui consulta nesse horário!");
                return;
            }
            if (c.getLocal().equalsIgnoreCase(local) && c.getDataHora().equals(dataHora)) {
                System.out.println("Erro: O local já está ocupado nesse horário!");
                return;
            }
        }

        Consulta consulta = new Consulta(pacienteSelecionado, medicoSelecionado, dataHora, local);
        
        double desconto = pacienteSelecionado.calcularDesconto(consulta);
        consulta.setValorEconomizado(desconto);
    
        if (desconto > 0) {
            System.out.printf("INFO: Desconto de R$ %.2f aplicado com sucesso!\n", desconto);
        }

        consultas.add(consulta);
        pacienteSelecionado.adicionarConsulta(consulta);
        medicoSelecionado.adicionarConsulta(consulta);
        System.out.println("Consulta agendada com sucesso!");
    }

    public void concluirConsulta(Scanner sc) {
        System.out.println("\n--- Concluir Consulta ---");

        ArrayList<Consulta> pendentes = new ArrayList<>();
        for(Consulta c : consultas) {
            if(c.getStatus().equals("Agendada")) {
                pendentes.add(c);
            }
        }

        if(pendentes.isEmpty()) {
            System.out.println("Não há consultas pendentes!");
            return;
        }

        for(int i=0;i<pendentes.size();i++){
            System.out.println(i + ". Paciente: " + pendentes.get(i).getPaciente().getNome() + ", Médico: " + pendentes.get(i).getMedico().getNome() + ", Data: " + pendentes.get(i).getDataHora());
        }

        System.out.print("Escolha a consulta pelo índice: ");
        int idx = sc.nextInt();
        sc.nextLine();

        System.out.print("Diagnóstico: ");
        String diag = sc.nextLine();
        System.out.print("Prescrição de medicamentos: ");
        String presc = sc.nextLine();

        pendentes.get(idx).setDiagnostico(diag);
        pendentes.get(idx).setPrescricao(presc);
        pendentes.get(idx).setStatus("Concluída");
        System.out.println("Consulta concluída com sucesso!");
    }

    public void gerenciarInternacoes(Scanner sc) {
        System.out.println("\n--- Internações ---");
        System.out.println("1. Registrar internação");
        System.out.println("2. Encerrar internação");
        System.out.println("3. Cancelar internação");
        System.out.print("Escolha uma opção: ");
        int opcao = sc.nextInt();
        sc.nextLine();

        switch(opcao) {
            case 1:
                registrarInternacoes(sc);
                break;
            case 2:
                encerrarInternacoes(sc);
                break;
            case 3:
                cancelarInternacoes(sc);
                break;
            default:
                System.out.println("Opção inválida!");
        }
    }

    private void registrarInternacoes(Scanner sc) {
        if(pacientes.isEmpty() || medicos.isEmpty()) {
            System.out.println("É necessário cadastrar pacientes e médicos primeiro!");
            return;
        }

        System.out.println("Pacientes:");
        for(int i=0;i<pacientes.size();i++){
            System.out.println(i + ". " + pacientes.get(i).getNome());
        }
        System.out.print("Escolha o paciente: ");
        int idxPaciente = sc.nextInt();
        sc.nextLine();

        System.out.println("Médicos:");
        for(int i=0;i<medicos.size();i++){
            System.out.println(i + ". " + medicos.get(i).getNome());
        }
        System.out.print("Escolha o médico responsável: ");
        int idxMedico = sc.nextInt();
        sc.nextLine();

        System.out.print("Quarto: ");
        String quarto = sc.nextLine();

        for(Internacao i : internacoes) {
            if(i.getQuarto().equalsIgnoreCase(quarto) && i.getStatus().equals("Ativa")) {
                System.out.println("Erro: Quarto ocupado!");
                return;
            }
        }

        System.out.print("Data de entrada (dd-MM-yyyy HH:mm): ");
        String dtStr = sc.nextLine();
        LocalDateTime entrada = LocalDateTime.parse(dtStr, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));

        System.out.print("Custo da internação: ");
        double custo = sc.nextDouble();
        sc.nextLine();

        Internacao interna = new Internacao(pacientes.get(idxPaciente), medicos.get(idxMedico), entrada, null, quarto, custo);
        internacoes.add(interna);
        System.out.println("Internação registrada com sucesso!");
    }

    private void encerrarInternacoes(Scanner sc) {
        ArrayList<Internacao> ativas = new ArrayList<>();
        for(Internacao i : internacoes) {
            if(i.getStatus().equals("Ativa")) ativas.add(i);
        }

        if(ativas.isEmpty()) {
            System.out.println("Não há internações ativas!");
            return;
        }

        for(int i=0;i<ativas.size();i++){
            System.out.println(i + ". Paciente: " + ativas.get(i).getPaciente().getNome() + ", Quarto: " + ativas.get(i).getQuarto());
        }

        System.out.print("Escolha a internação pelo índice: ");
        int idx = sc.nextInt();
        sc.nextLine();

        System.out.print("Data de saída (dd-MM-yyyy HH:mm): ");
        String dtStr = sc.nextLine();
        LocalDateTime saida = LocalDateTime.parse(dtStr, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));

        ativas.get(idx).setDataSaida(saida);
        ativas.get(idx).setStatus("Encerrada");
        Paciente pacienteDaInternacao = ativas.get(idx).getPaciente();
    // Verifica se o paciente é especial
        if (pacienteDaInternacao instanceof PacienteEspecial) {
            PacienteEspecial pe = (PacienteEspecial) pacienteDaInternacao;
            // Verifica se o plano dele é especial
            if (pe.getPlano().isPlanoEspecialDeInternacao()) {
                // Calcula a duração
                Duration duracao = Duration.between(ativas.get(idx).getDataEntrada(), saida);
                long dias = duracao.toDays();
                
                // Se durar menos de 7 dias, o custo é zerado
                if (dias < 7) {
                    System.out.println("INFO: Plano especial ativado! Custo da internação zerado.");
                    ativas.get(idx).setCusto(0.0);
                }
            }
        }
        System.out.println("Internação encerrada com sucesso!");
    }

    private void cancelarInternacoes(Scanner sc) {
        if(internacoes.isEmpty()) {
            System.out.println("Não há internações cadastradas!");
            return;
        }

        for(int i=0;i<internacoes.size();i++){
            System.out.println(i + ". Paciente: " + internacoes.get(i).getPaciente().getNome() + ", Quarto: " + internacoes.get(i).getQuarto() + ", Status: " + internacoes.get(i).getStatus());
        }

        System.out.print("Escolha a internação pelo índice: ");
        int idx = sc.nextInt();
        sc.nextLine();

        internacoes.get(idx).setStatus("Cancelada");
        System.out.println("Internação cancelada com sucesso!");
    }

    public void gerarRelatorios(Scanner sc) {
        Relatorios.gerarMenuRelatorios(pacientes, medicos, consultas, internacoes, planos, sc);
    }
}
