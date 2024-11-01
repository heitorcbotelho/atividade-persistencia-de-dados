import java.time.LocalTime;
import java.util.Date;
import java.util.InputMismatchException;

public class Movimentacao {
    private String nomeCorrentista;
    private String cpfCorrentista;
    private String tipoTransacao;
    private String descricao;
    private Date dataTransacao;
    private double valorOperacao;
    private double saldo;
    private int contOperacoes;
    private double saque;

    public double getSaque() {
        return saque;
    }

    public void setSaque(double saque) {
        this.saque = saque;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        if (saldo < 0) {
            System.out.println("O saldo está negativo");
            this.saldo = 1; // Define um valor mínimo para evitar saldo negativo
        } else {
            this.saldo = saldo;
        }
    }

    public String getNomeCorrentista() {
        return nomeCorrentista;
    }

    public void setNomeCorrentista(String nomeCorrentista) {
        this.nomeCorrentista = nomeCorrentista;
    }

    public String getCpfCorrentista() {
        return cpfCorrentista;
    }

    public void setCpfCorrentista(String cpfCorrentista) {
        if (isCPF(cpfCorrentista)) {
            this.cpfCorrentista = cpfCorrentista;
        } else {
            System.out.println("CPF inválido.");
        }
    }

    public String getTipoTransacao() {
        return tipoTransacao;
    }

    public void setTipoTransacao(String tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataTransacao() {
        return dataTransacao;
    }

    public void setDataTransacao(Date dataTransacao) {
        this.dataTransacao = dataTransacao;
    }

    public double getValorOperacao() {
        return valorOperacao;
    }

    public void setValorOperacao(double valorOperacao) {
        this.valorOperacao = valorOperacao;
    }

    public int getContOperacoes() {
        return contOperacoes;
    }

    public void setContOperacoes(int contOperacoes) {
        this.contOperacoes = contOperacoes;
    }

    public boolean verificarSaldo(double valorOperacao) {
        if (saldo < valorOperacao) {
            System.out.println("Saldo insuficiente");
            return false;
        }
        return true;
    }

    private boolean isCPF(String CPF) {
        if (CPF.equals("00000000000") ||
                CPF.equals("11111111111") ||
                CPF.equals("22222222222") ||
                CPF.equals("33333333333") ||
                CPF.equals("44444444444") ||
                CPF.equals("55555555555") ||
                CPF.equals("66666666666") ||
                CPF.equals("77777777777") ||
                CPF.equals("88888888888") ||
                CPF.equals("99999999999") ||
                (CPF.length() != 11)) {
            return false;
        }

        char dig10, dig11;
        int sm, i, r, num, peso;

        try {
            sm = 0;
            peso = 10;
            for (i = 0; i < 9; i++) {
                num = CPF.charAt(i) - 48;
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            dig10 = (r == 10 || r == 11) ? '0' : (char) (r + 48);

            sm = 0;
            peso = 11;
            for (i = 0; i < 10; i++) {
                num = CPF.charAt(i) - 48;
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            dig11 = (r == 10 || r == 11) ? '0' : (char) (r + 48);

            return (dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10));
        } catch (InputMismatchException erro) {
            return false;
        }
    }

    public void operacao(String tipoTransacao, double valorOperacao) {
        if (contOperacoes <= 10) {
            setTipoTransacao(tipoTransacao);
            switch (tipoTransacao.toLowerCase()) {
                case "pix":
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
                    if (verificarSaldo(valorOperacao + 5)) {
                        setSaldo(getSaldo() - valorOperacao - 5);
                        System.out.println("Pix realizado, tarifa da operação: R$5,00");
                    } else {
                        System.out.println("Saldo insuficiente para realizar o Pix.");
                    }
                    break;

                case "saque":
                    if (verificarSaldo(valorOperacao + 2)) {
                        if (getSaque() <= 5000) {
                            setSaldo(getSaldo() - valorOperacao - 2);
                            System.out.println("Saque realizado, tarifa da operação: R$2,00");
                            setSaque(getSaque() + valorOperacao);
                        } else {
                            System.out.println("Limite de saque diário atingido.");
                        }
                    } else {
                        System.out.println("Saldo insuficiente para realizar o saque.");
                    }
                    break;

                case "depósito":
                    setSaldo(getSaldo() + valorOperacao);
                    System.out.println("Depósito realizado com sucesso.");
                    break;

                case "pagamento":
                    if (verificarSaldo(valorOperacao + 5)) {
                        setSaldo(getSaldo() - valorOperacao - 5);
                        System.out.println("Pagamento realizado, tarifa da operação: R$5,00");
                    } else {
                        System.out.println("Saldo insuficiente para realizar o pagamento.");
                    }
                    break;

                default:
                    System.out.println("Tipo de transação inválido.");
                    return;
            }

            setContOperacoes(getContOperacoes() + 1);
            if (getSaldo() < 100) {
                System.out.println("Saldo abaixo de R$100,00");
            }

        } else {
            System.out.println("Limite de operações diárias atingido.");
        }
    }
}
