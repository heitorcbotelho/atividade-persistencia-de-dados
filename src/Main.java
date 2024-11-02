import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite o nome do correntista:");
        String nomeCorrentista = scanner.nextLine();

        System.out.println("Digite o CPF do correntista:");
        String cpfCorrentista = scanner.nextLine();

        Cliente cliente = new Cliente(nomeCorrentista, cpfCorrentista);
        Movimentacao movimentacao = new Movimentacao(cliente);

        int opcao;
        do {
            System.out.println("\nEscolha uma operação:");
            System.out.println("[1] Depósito");
            System.out.println("[2] Saque");
            System.out.println("[3] Pagamento");
            System.out.println("[4] Pix");
            System.out.println("[5] Ver Saldo");
            System.out.println("[0] Sair");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    System.out.println("Digite o valor do depósito:");
                    double valorDeposito = scanner.nextDouble();
                    movimentacao.operacao("depósito", valorDeposito);
                    break;
                case 2:
                    System.out.println("Digite o valor do saque:");
                    double valorSaque = scanner.nextDouble();
                    movimentacao.operacao("saque", valorSaque);
                    break;
                case 3:
                    System.out.println("Digite o valor do pagamento:");
                    double valorPagamento = scanner.nextDouble();
                    movimentacao.operacao("pagamento", valorPagamento);
                    break;
                case 4:
                    System.out.println("Digite o valor do Pix:");
                    double valorPix = scanner.nextDouble();
                    movimentacao.operacao("pix", valorPix);
                    break;
                case 5:
                    System.out.println("Saldo atual: R$" + cliente.getSaldo());
                    break;
                case 0:
                    System.out.println("Encerrando...");
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        } while (opcao != 0);

        scanner.close();
    }
}
