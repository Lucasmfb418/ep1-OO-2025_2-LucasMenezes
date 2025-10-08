

import java.util.Scanner;
import servicos.Hospital;

public class Main {

    private static void exibirMenuPrincipal() {
        System.out.println("\n*************************************************");
        System.out.println("*        SISTEMA DE GERENCIAMENTO HOSPITALAR      *");
        System.out.println("***************************************************");
        System.out.println("|                                                 |");
        System.out.println("| 1. Cadastros                                    |");
        System.out.println("| 2. Agendamento e Consultas                      |");
        System.out.println("| 3. Gerenciar Internações                        |");
        System.out.println("| 4. Exibir Relatórios                            |");
        System.out.println("|                                                 |");
        System.out.println("| 0. Sair do Sistema                              |");
        System.out.println("***************************************************");
        System.out.print("--> Escolha uma opção: ");
    }

    private static void exibirSubMenuCadastros() {
        System.out.println("\n*************************************************");
        System.out.println("*                MENU DE CADASTROS                *");
        System.out.println("***************************************************");
        System.out.println("|                                                 |");
        System.out.println("| 1. Cadastrar Paciente                           |");
        System.out.println("| 2. Cadastrar Médico                             |");
        System.out.println("| 3. Cadastrar Plano de Saúde                     |");
        System.out.println("|                                                 |");
        System.out.println("| 0. Voltar ao Menu Principal                     |");
        System.out.println("***************************************************");
        System.out.print("--> Escolha uma opção: ");
    }

    private static void exibirSubMenuConsultas() {
        System.out.println("\n*************************************************");
        System.out.println("*                MENU DE CONSULTAS                *");
        System.out.println("***************************************************");
        System.out.println("|                                                 |");
        System.out.println("| 1. Agendar Nova Consulta                        |");
        System.out.println("| 2. Concluir Consulta / Registrar Diagnóstico    |");
        System.out.println("|                                                 |");
        System.out.println("| 0. Voltar ao Menu Principal                     |");
        System.out.println("***************************************************");
        System.out.print("--> Escolha uma opção: ");
    }

    private static void exibirSubMenuInternacoes() {
        System.out.println("\n*************************************************");
        System.out.println("*               MENU DE INTERNAÇÕES               *");
        System.out.println("***************************************************");
        System.out.println("|                                                 |");
        System.out.println("| 1. Registrar Nova Internação                    |");
        System.out.println("| 2. Encerrar Internação (Dar alta)               |");
        System.out.println("| 3. Cancelar Internação                          |");
        System.out.println("|                                                 |");
        System.out.println("| 0. Voltar ao Menu Principal                     |");
        System.out.println("***************************************************");
        System.out.print("--> Escolha uma opção: ");
    }

    private static void exibirSubMenuRelatorios() {
        System.out.println("\n*************************************************");
        System.out.println("*                MENU DE RELATÓRIOS               *");
        System.out.println("***************************************************");
        System.out.println("|                                                 |");
        System.out.println("| 1. Listar Pacientes Cadastrados                 |");
        System.out.println("| 2. Listar Médicos Cadastrados                   |");
        System.out.println("| 3. Listar Consultas                             |");
        System.out.println("| 4. Listar Pacientes Internados                  |");
        System.out.println("| 5. Estatísticas Gerais                          |");
        System.out.println("| 6. Análise por Plano de Saúde                   |");
        System.out.println("| 7. Estatísticas Avançadas                       |");
        System.out.println("|                                                 |");
        System.out.println("| 0. Voltar ao Menu Principal                     |");
        System.out.println("***************************************************");
        System.out.print("--> Escolha uma opção: ");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Hospital hospital = new Hospital();
        hospital.carregarDados();

        int opcaoPrincipal = -1;
        while (opcaoPrincipal != 0) {
            exibirMenuPrincipal();
            try {
                opcaoPrincipal = Integer.parseInt(scanner.nextLine());
                switch (opcaoPrincipal) {
                    case 1: gerenciarMenuCadastros(scanner, hospital); break;
                    case 2: gerenciarMenuConsultas(scanner, hospital); break;
                    case 3: gerenciarMenuInternacoes(scanner, hospital); break;
                    case 4: gerenciarMenuRelatorios(scanner, hospital); break;
                    case 0: System.out.println("\nSaindo do sistema..."); break;
                    default: System.out.println("\nATENÇÃO: Opção inválida!");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nERRO: Digite apenas um dos NÚMEROS do menu.");
            }
        }
        scanner.close();
        System.out.println("Programa finalizado.");
    }

    private static void gerenciarMenuCadastros(Scanner scanner, Hospital hospital) {
        int opcao = -1;
        while (opcao != 0) {
            exibirSubMenuCadastros();
            try {
                opcao = Integer.parseInt(scanner.nextLine());
                switch (opcao) {
                    case 1: hospital.cadastrarPaciente(scanner); break;
                    case 2: hospital.cadastrarMedico(scanner); break;
                    case 3: hospital.cadastrarPlano(scanner); break;
                    case 0: break;
                    default: System.out.println("\nATENÇÃO: Opção inválida!");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nERRO: Digite apenas um dos NÚMEROS do menu.");
            }
        }
    }

    private static void gerenciarMenuConsultas(Scanner scanner, Hospital hospital) {
        int opcao = -1;
        while (opcao != 0) {
            exibirSubMenuConsultas();
            try {
                opcao = Integer.parseInt(scanner.nextLine());
                switch (opcao) {
                    case 1: hospital.agendarConsulta(scanner); break;
                    case 2: hospital.concluirConsulta(scanner); break;
                    case 0: break;
                    default: System.out.println("\nATENÇÃO: Opção inválida!");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nERRO: Digite apenas um dos NÚMEROS do menu.");
            }
        }
    }

    private static void gerenciarMenuInternacoes(Scanner scanner, Hospital hospital) {
        int opcao = -1;
        while (opcao != 0) {
            exibirSubMenuInternacoes();
            try {
                opcao = Integer.parseInt(scanner.nextLine());
                switch (opcao) {
                    case 1: hospital.registrarInternacao(scanner); break;
                    case 2: hospital.encerrarInternacao(scanner); break;
                    case 3: hospital.cancelarInternacao(scanner); break;
                    case 0: break;
                    default: System.out.println("\nATENÇÃO: Opção inválida!");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nERRO: Digite apenas um dos NÚMEROS do menu.");
            }
        }
    }

     private static void gerenciarMenuRelatorios(Scanner scanner, Hospital hospital) {
        int opcao = -1;
        while (opcao != 0) {
            exibirSubMenuRelatorios();
            try {
                opcao = Integer.parseInt(scanner.nextLine());
                switch (opcao) {
                    case 1: hospital.relatorioListarPacientes(); break;
                    case 2: hospital.relatorioListarMedicos(); break;
                    case 3: hospital.relatorioListarConsultas(scanner); break;
                    case 4: hospital.relatorioListarInternados(); break;
                    case 5: hospital.relatorioEstatisticasGerais(); break;
                    case 6: hospital.relatorioPessoasPorPlano(); break;
                    case 7: hospital.relatorioEstatisticasAvancadas(); break;
                    case 0: break;
                    default: System.out.println("\nATENÇÃO: Opção inválida!");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nERRO: Digite apenas um dos NÚMEROS do menu.");
            }
        }
    }
}