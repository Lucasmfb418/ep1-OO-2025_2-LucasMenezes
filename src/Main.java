

import java.util.Scanner;
import servicos.Hospital;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Hospital hospital = new Hospital();

        hospital.carregarDados();

        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n=== Sistema Hospitalar ===");
            System.out.println("1. Cadastrar Paciente");
            System.out.println("2. Cadastrar Médico");
            System.out.println("3. Cadastrar Plano de Saúde");
            System.out.println("4. Agendar Consulta");
            System.out.println("5. Concluir Consulta / Registrar Diagnóstico");
            System.out.println("6. Internações");
            System.out.println("7. Relatórios");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch(opcao) {
                case 1:
                    hospital.cadastrarPaciente(scanner);
                    break;
                case 2:
                    hospital.cadastrarMedico(scanner);
                    break;
                case 3:
                    hospital.cadastrarPlano(scanner);
                    break;
                case 4:
                    hospital.agendarConsulta(scanner);
                    break;
                case 5:
                    hospital.concluirConsulta(scanner);
                    break;
                case 6:
                    hospital.gerenciarInternacoes(scanner);
                    break;
                case 7:
                    hospital.gerarRelatorios(scanner);
                    break;
                case 0:
                    System.out.println("Saindo do sistema...");
                    hospital.salvarDados();
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }

        scanner.close();
    }
}
