// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setSaldo(1000);
        movimentacao.operacao("pix", 100);
    }
}