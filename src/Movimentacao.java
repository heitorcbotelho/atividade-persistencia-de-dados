import java.time.LocalTime;

public class Movimentacao {
    private Cliente cliente;
    private String tipoTransacao;
    private String descricao;
    private double valorOperacao;
    private int contOperacoes;
    private double saqueDiario;

    public Movimentacao(Cliente cliente) {
        this.cliente = cliente;
        this.saqueDiario = 0; // reseta diariamente no sistema real
    }

    public void operacao(String tipoTransacao, double valorOperacao) {
        if (contOperacoes >= 10) {
            System.out.println("Limite de operações diárias atingido.");
            return;
        }

        setTipoTransacao(tipoTransacao);

        switch (tipoTransacao.toLowerCase()) {
            case "pix":
                realizarPix(valorOperacao);
                break;

            case "saque":
                realizarSaque(valorOperacao);
                break;

            case "depósito":
                cliente.depositar(valorOperacao);
                break;

            case "pagamento":
                realizarPagamento(valorOperacao);
                break;

            default:
                System.out.println("Tipo de transação inválido.");
                return;
        }

        contOperacoes++;
        if (cliente.getSaldo() < 100) {
            System.out.println("Alerta: Saldo abaixo de R$100,00.");
        }
    }

    private void realizarPix(double valorOperacao) {
        LocalTime inicio = LocalTime.of(6, 0);
        LocalTime fim = LocalTime.of(22, 0);
        LocalTime agora = LocalTime.now();

        if (agora.isBefore(inicio) || agora.isAfter(fim)) {
            System.out.println("Operações Pix são permitidas entre 06:00 e 22:00");
            return;
        }
        if (valorOperacao > 300) {
            System.out.println("O limite de operações Pix é de R$300,00");
            return;
        }
        if (cliente.debitar(valorOperacao + 5)) {
            System.out.println("Pix realizado, tarifa de operação: R$5,00");
        }
    }

    private void realizarSaque(double valorOperacao) {
        if (saqueDiario + valorOperacao > 5000) {
            System.out.println("Limite de saque diário atingido.");
            return;
        }
        if (cliente.debitar(valorOperacao + 2)) {
            saqueDiario += valorOperacao;
            System.out.println("Saque realizado, tarifa de operação: R$2,00");
        }
    }

    private void realizarPagamento(double valorOperacao) {
        if (cliente.debitar(valorOperacao + 5)) {
            System.out.println("Pagamento realizado, tarifa de operação: R$5,00");
        }
    }

    public String getTipoTransacao() {
        return tipoTransacao;
    }

    public void setTipoTransacao(String tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }
}
