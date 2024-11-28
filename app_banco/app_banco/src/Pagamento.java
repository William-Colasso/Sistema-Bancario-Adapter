public interface Pagamento {
    void processarPagamento(double valor, String detalhes) throws InterruptedException;
}
