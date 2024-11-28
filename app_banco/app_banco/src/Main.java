import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);

        // Exibir opções de pagamento
        System.out.println("Selecione o método de pagamento:");
        System.out.println("1. Transferência Bancária");
        System.out.println("2. Boleto");
        System.out.println("3. PIX");
        int opcao = scanner.nextInt();
        scanner.nextLine(); // Limpar buffer

        // Coletar informações comuns
        System.out.print("Digite o valor do pagamento: ");
        double valor = scanner.nextDouble();
        scanner.nextLine(); // Limpar buffer

        Pagamento pagamento;

        switch (opcao) {
            case 1 -> {
                System.out.print("Digite o número da conta de destino: ");
                String conta = scanner.nextLine();
                pagamento = new TransferenciaBancaria();
                pagamento.processarPagamento(valor, conta);
            }
            case 2 -> {
                System.out.print("Digite o código do boleto: ");
                String boleto = scanner.nextLine();
                pagamento = new PagamentoBoleto();
                pagamento.processarPagamento(valor, boleto);
            }
            case 3 -> {
                System.out.print("Digite a chave PIX: ");
                String pix = scanner.nextLine();
                SistemaPix sistemaPix = new SistemaPix();
                pagamento = new AdapterPix(sistemaPix);
                pagamento.processarPagamento(valor, pix);
            }
            default -> System.out.println("Opção inválida!");
        }

        scanner.close();
    }
}
