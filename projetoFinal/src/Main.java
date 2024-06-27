import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static ArrayList<Produto> produtos = new ArrayList<>(); // Lista de produtos
    private static ArrayList<Pedido> pedidos = new ArrayList<>(); // Lista de pedidos

    private static Scanner scanner = new Scanner(System.in);

    private static final String ARQUIVO_PRODUTOS = "produtos.txt"; // Arquivo de produtos
    private static final String ARQUIVO_PEDIDOS = "pedidos.txt"; // Arquivo de pedidos

    private static int proximoNumeroPedido = 1; // Variável de controle

    public static void main(String[] args) throws Exception {

        carregarProdutos();
        carregarPedidos();

        while (true) {
            System.out.println("# Menu principal #");
            System.out.println("[1] Produtos");
            System.out.println("[2] Pedidos");
            System.out.println("[3] Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    menuProdutos();
                    break;
                case 2:
                    menuPedidos();
                    break;
                case 3:
                    salvarProdutos();
                    salvarPedidos();
                    System.out.println("Saindo...");
                    return;
                default:
                    System.out.println("Opção inválida! Tente novamente...");
                    break;
            }
        }
    }

    /**
     ** Carrega os produtos a partir de um arquivo.
     * 
     * @throws IOException se ocorrer um erro ao carregar os produtos do arquivo.
     */
    private static void carregarProdutos() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_PRODUTOS))) { // Tenta abrir o arquivo
            String linha;
            while ((linha = reader.readLine()) != null) { // Lê cada linha do arquivo
                String[] partes = linha.split(";"); // Divide a linha em partes
                int codigo = Integer.parseInt(partes[0]); // Converte o código para inteiro
                String nome = partes[1]; // Pega o nome do produto
                double preco = Double.parseDouble(partes[2]); // Pega o preço e converte o preço para double
                int quantidade = Integer.parseInt(partes[3]); // Pega a quantidade e converte a quantidade para inteiro
                produtos.add(new Produto(codigo, nome, preco, quantidade)); // Adiciona o produto à lista
                Produto.setUltimoCodigo(codigo); // Atualiza o último código
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar produtos: " + e.getMessage());
        }
    }

    /*
     * Salva os produtos em um arquivo.
     */
    private static void salvarProdutos() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_PRODUTOS))) { // Tenta abrir o arquivo
            for (Produto produto : produtos) { // Para cada produto na lista de produtos
                writer.write(produto.getCodigoProduto() + ";" + produto.getNomeProduto() + ";" 
                        + produto.getPrecoUnitario() + ";" + produto.getQuantidadeEstoque()); // Escreve os dados do produto no arquivo
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar produtos: " + e.getMessage());
        }
    }

    /**
     ** Exibe o menu de produtos e permite ao usuário realizar diversas operações
     ** relacionadas aos produtos.
     ** As opções disponíveis são:
     ** [1] Incluir produto
     ** [2] Editar quantidade em estoque de um produto
     ** [3] Editar preço unitário de um produto
     ** [4] Excluir produto
     ** [5] Listar produtos
     ** [6] Voltar ao menu principal
     * 
     ** O usuário deve escolher uma opção digitando o número correspondente.
     * 
     * @return void
     */
    private static void menuProdutos() {
        while (true) { 
            System.out.println("# Produtos #");
            System.out.println("[1] Incluir produto");
            System.out.println("[2] Editar quantidade estoque produto");
            System.out.println("[3] Editar preço unitário produto");
            System.out.println("[4] Excluir produto");
            System.out.println("[5] Listagem produtos");
            System.out.println("[6] Voltar ao menu principal");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    incluirProduto();
                    break;
                case 2:
                    editarQuantidadeEstoqueProduto();
                    break;
                case 3:
                    editarPrecoUnitarioProduto();
                    break;
                case 4:
                    excluirProduto();
                    break;
                case 5:
                    listarProdutos();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Opção inválida! Tente novamente...");
                    break;
            }
        }
    }

    /**
     ** Inclui um novo produto na lista de produtos.
     * 
     ** Solicita ao usuário o nome, preço unitário e quantidade em estoque do
     ** produto.
     ** Em seguida, cria um novo objeto Produto com os dados informados e adiciona-o
     ** à lista de produtos.
     * 
     * @return void
     */
    private static void incluirProduto() { 
        System.out.print("Nome do produto: ");
        String nome = scanner.nextLine();

        System.out.print("Preço unitário do produto: ");
        double preco = scanner.nextDouble();

        System.out.print("Quantidade em estoque: ");
        int quantidade = scanner.nextInt();

        produtos.add(new Produto(nome, preco, quantidade)); // Adiciona o produto à lista
        System.out.println("Produto incluído com sucesso!");
    }

    /**
     ** Método responsável por editar a quantidade em estoque de um produto.
     * 
     ** Solicita ao usuário o código do produto e a nova quantidade em estoque.
     ** Em seguida, busca o produto pelo código informado e, caso seja encontrado,
     ** atualiza a quantidade em estoque do produto com o valor informado.
     * 
     * @return void
     */
    public static void editarQuantidadeEstoqueProduto() {
        System.out.print("Codigo do produto: ");
        int codigo = scanner.nextInt();
        Produto produto = encontrarProduto(codigo);
        if (produto != null) { // Se o produto for encontrado
            System.out.print("Nova quantidaade em estoque: ");
            int quantidade = scanner.nextInt();
            produto.setQuantidadeEstoque(quantidade);
            System.out.println("Quantidade em estoque do produto alterada com sucesso!");
        } else {
            System.out.println("Produto não encontrado!");
        }
    }

    /**
     ** Método para encontrar um produto pelo código.
     * 
     * @param codigo O código do produto a ser encontrado.
     * @return O objeto Produto correspondente ao código informado, ou null caso não
     *         seja encontrado.
     */
    private static Produto encontrarProduto(int codigo) {
        for (Produto produto : produtos) {
            if (produto.getCodigoProduto() == codigo) {
                return produto;
            }
        }
        return null;
    }

    /*
     * Método responsável por editar o preço unitário de um produto.
     * 
     * Solicita ao usuário o código do produto e o novo preço unitário.
     * Em seguida, busca o produto com o código informado e, caso seja encontrado,
     * atualiza o preço unitário do produto com o novo valor informado.
     * Caso o produto não seja encontrado, exibe uma mensagem de erro.
     */
    private static void editarPrecoUnitarioProduto() {
        System.out.print("Código do produto: ");
        int codigo = scanner.nextInt();
        Produto produto = encontrarProduto(codigo);
        if (produto != null) { 
            System.out.print("Novo preço unitário: ");
            double preco = scanner.nextDouble();
            produto.setPrecoUnitario(preco); 
            System.out.println("Preço atualizado com sucesso!");
        } else {
            System.out.println("Produto não encontrado!");
        }
    }

    /**
     ** Exclui um produto com base no código informado.
     * 
     * @param codigo o código do produto a ser excluído
     */
    private static void excluirProduto() {
        System.out.print("Código do produto: ");
        int codigo = scanner.nextInt();
        Produto produto = encontrarProduto(codigo);
        if (produto != null) {
            produtos.remove(produto);
            System.out.println("Produto excluído com sucesso!");
        } else {
            System.out.println("Produto não encontrado!");
        }
    }

    /*
     * Lista todos os produtos cadastrados.
     * 
     * Se não houver nenhum produto cadastrado, exibe a mensagem
     * "Nenhum produto cadastrado."
     * Caso contrário, exibe uma tabela com o código, nome, preço unitário e
     * quantidade em estoque de cada produto.
     * No final, exibe o valor total do estoque.
     */
    private static void listarProdutos() {
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
        } else {
            System.out
                    .println("Código  Nome                                         Preço unitário  Quantidade estoque");
            System.out
                    .println("---------------------------------------------------------------------------------------");
            double valorTotalEstoque = 0.0;
            for (Produto produto : produtos) {
                System.out.printf("%-7d %-48s %12.1f %18d%n",
                        produto.getCodigoProduto(), produto.getNomeProduto(), produto.getPrecoUnitario(),
                        produto.getQuantidadeEstoque());
                valorTotalEstoque += produto.getPrecoUnitario() * produto.getQuantidadeEstoque();
            }
            System.out
                    .println("---------------------------------------------------------------------------------------");
            System.out.printf("Valor total estoque: %.2f%n", valorTotalEstoque);
        }
    }

    /**
     ** Exibe o menu de pedidos e permite ao usuário escolher uma opção.
     * 
     ** Opções disponíveis:
     ** [1] Novo pedido
     ** [2] Listagem pedidos
     ** [3] Voltar ao menu principal
     * 
     * @return void
     */
    private static void menuPedidos() {
        while (true) {
            System.out.println("# Pedidos #");
            System.out.println("[1] Novo pedido");
            System.out.println("[2] Listagem pedidos");
            System.out.println("[3] Voltar ao menu principal");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    novoPedido();
                    break;
                case 2:
                    listarPedidos();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Opção inválida! Tente novamente...");
                    break;
            }
        }
    }

    /*
     * Cria um novo pedido.
     * 
     * Este método permite ao usuário criar um novo pedido, adicionando produtos ao pedido até que seja inserido o código 0.
     * O método solicita ao usuário o código do produto e a quantidade desejada. Em seguida, verifica se o produto está disponível em estoque e adiciona o produto ao pedido.
     * Após a conclusão do pedido, exibe uma mensagem informando que o pedido foi registrado com sucesso.
     */
    private static void novoPedido() {
        Pedido novoPedido = new Pedido(proximoNumeroPedido++, -1, 0.0, 0);

        while(true) {
            System.out.print("Código do produto (0 para sair): ");
            int codigoProduto = scanner.nextInt();
            if (codigoProduto == 0) {
                break;
            }

            Produto produto = encontrarProduto(codigoProduto);
            if (produto == null) {
                System.out.println("Produto não encontrado!");
                continue;
            }

            System.out.print("Quantidade: ");
            int quantidade = scanner.nextInt();
            if (quantidade > produto.getQuantidadeEstoque()) {
                System.out.println("Quantidade insuficiente em estoque!");
                continue;
            }

            double precoUnitario = produto.getPrecoUnitario();
            novoPedido = new Pedido(novoPedido.getNumeroPedido(), codigoProduto, precoUnitario, quantidade);
            pedidos.add(novoPedido);
            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - quantidade);

            System.out.println("Produto adicionado ao pedido!");
        }

        System.out.println("Pedido registrado com sucesso!");
    }

    /*
     * Lista todos os pedidos, exibindo as informações de cada pedido, incluindo os produtos, preços unitários e quantidades.
     */
    private static void listarPedidos() {
        ArrayList<Integer> numerosPedidosListados = new ArrayList<>();

        for (Pedido pedido : pedidos) {
            if (!numerosPedidosListados.contains(pedido.getNumeroPedido())) {
                numerosPedidosListados.add(pedido.getNumeroPedido());

                System.out.printf("Pedido número: %d%n", pedido.getNumeroPedido());
                System.out.printf("%-50s %-20s %-20s%n", "Produto", "Preço unitário", "Quantidade");
                System.out.println(
                        "--------------------------------------------------------------------------------------");

                double totalPedido = 0;

                for (Pedido itemPedido : pedidos) {
                    if (itemPedido.getNumeroPedido() == pedido.getNumeroPedido()) {
                        Produto produto = encontrarProduto(itemPedido.getCodigoProduto());
                        if (produto != null) {
                            double subtotal = itemPedido.getPrecoUnitario() * itemPedido.getQuantidade();
                            System.out.printf("%-50s %-20.2f %-20d%n", produto.getNomeProduto(),
                                    itemPedido.getPrecoUnitario(), itemPedido.getQuantidade());
                            totalPedido += subtotal;
                        }
                    }
                }

                System.out.println(
                        "--------------------------------------------------------------------------------------");
                System.out.printf("Total do pedido: %.2f%n%n", totalPedido);
            }
        }
    }

    /**
     ** Carrega os pedidos a partir de um arquivo de texto.
     * 
     * @throws IOException se ocorrer um erro ao carregar os pedidos
     */
    private static void carregarPedidos() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_PEDIDOS))) { // Tenta abrir o arquivo
            String linha;
            while ((linha = reader.readLine()) != null) { // Lê cada linha do arquivo
                String[] partes = linha.split(";"); // Divide a linha em partes
                int numeroPedido = Integer.parseInt(partes[0]); // Pega o número do pedido
                int codigoProduto = Integer.parseInt(partes[1]); // Pega o código do produto
                double precoUnitario = Double.parseDouble(partes[2]); // Pega o preço unitário
                int quantidade = Integer.parseInt(partes[3]); // Pega a quantidade
                pedidos.add(new Pedido(numeroPedido, codigoProduto, precoUnitario, quantidade)); // Adiciona o pedido à lista
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar pedidos: " + e.getMessage());
        }
    }

    /**
        ** Salva os pedidos em um arquivo de texto.
        * 
        * @throws IOException se ocorrer um erro ao salvar os pedidos
        */
    private static void salvarPedidos() { 
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("pedidos.txt"))) { // Tenta abrir o arquivo
            for (Pedido pedido : pedidos) { // Para cada pedido na lista de pedidos
                writer.write(pedido.getNumeroPedido() + ";" + pedido.getCodigoProduto() + ";" 
                        + pedido.getPrecoUnitario() + ";" + pedido.getQuantidade()); // Escreve os dados do pedido no arquivo
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar pedidos: " + e.getMessage());
        }
    }
}
