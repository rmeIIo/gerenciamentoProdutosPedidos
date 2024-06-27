public class Produto {
    private static int ultimoCodigo = 0;

    private int codigoProduto;
    private String nomeProduto;
    private double precoUnitario;
    private int quantidadeEstoque;

    public Produto(String nomeProduto, double precoUnitario, int quantidadeEstoque) {
        this.codigoProduto = ++ultimoCodigo;
        this.nomeProduto = nomeProduto;
        this.precoUnitario = precoUnitario;
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public Produto(int codigoProduto, String nomeProduto, double precoUnitario, int quantidadeEstoque) {
        this.codigoProduto = codigoProduto;
        this.nomeProduto = nomeProduto;
        this.precoUnitario = precoUnitario;
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public int getCodigoProduto() {
        return codigoProduto;
    }

    public void setCodigoProduto(int codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(int quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public static void setUltimoCodigo(int ultimoCodigo) {
        Produto.ultimoCodigo = ultimoCodigo;
    }

    public void atualizarEstoque(int quantidadeVendida) {
        this.quantidadeEstoque -= quantidadeVendida;
    }
}
