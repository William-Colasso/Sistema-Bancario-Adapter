public class AdapterPix implements Pagamento {
    private final SistemaPix sistemaPix;

    public AdapterPix(SistemaPix sistemaPix) {
        this.sistemaPix = sistemaPix;
    }

    @Override
    public void processarPagamento(double valor, String numeroCartao) throws InterruptedException {
        sistemaPix.pagarComPix(numeroCartao, valor);
    }
}
