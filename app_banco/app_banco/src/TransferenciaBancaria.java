public class TransferenciaBancaria implements Pagamento {
    @Override
    public void processarPagamento(double valor, String detalhes) throws InterruptedException {
        System.out.println("Pagamento via Transferência Bancária:");
        System.out.println("Enviando R$ " + valor + " para a conta destinatária: " + detalhes + "...");
        Thread.sleep(500);
        System.out.print(".");
        Thread.sleep(500);
        System.out.print(".");
        Thread.sleep(500);
        System.out.print(".");
        Thread.sleep(500);
        System.out.println(".");
        Thread.sleep(500);
        System.out.println("Transferência enviada!\n\n");
    }
}
