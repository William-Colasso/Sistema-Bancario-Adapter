public class SistemaPix {
    public void pagarComPix(String chavePix, double valor) throws InterruptedException {
        System.out.println("Enviando R$ " + valor + " para a chave Pix: " + chavePix + "...");
        Thread.sleep(500);
        System.out.print(".");
        Thread.sleep(500);
        System.out.print(".");
        Thread.sleep(500);
        System.out.print(".");
        Thread.sleep(500);
        System.out.println(".");
        Thread.sleep(500);
        System.out.println("Pix enviado!\n\n");
    }
}
