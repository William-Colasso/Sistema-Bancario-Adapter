public class PagamentoBoleto implements Pagamento {
    @Override
    public void processarPagamento(double valor, String detalhes) throws InterruptedException {
        System.out.println("Pagamento via Boleto:");
        System.out.println("Enviando R$ " + valor + " para o boleto cujo o código é: " + detalhes + "...");
        Thread.sleep(500);
        System.out.print(".");
        Thread.sleep(500);
        System.out.print(".");
        Thread.sleep(500);
        System.out.print(".");
        Thread.sleep(500);
        System.out.println(".");
        Thread.sleep(500);
        System.out.println("Pagamento de boleto realizado!\n\n");
    }
}
