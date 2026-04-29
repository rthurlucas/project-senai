package br.com.cantina.senai.model.produto;

import jakarta.persistence.*;

@Entity
@Table(name = "produto")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProduto;
    private String descricaoProduto;
    private String nomeProduto;

    public Produto(Long idProduto, String descricaoProduto, String nomeProduto) {
        this.idProduto = idProduto;
        this.descricaoProduto = descricaoProduto;
        this.nomeProduto = nomeProduto;
    }

    public Produto() {

    }

    public Produto(Produto produto) {
    }

    public Long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Long idProduto) {
        this.idProduto = idProduto;
    }

    public String getDescricaoProduto() {
        return descricaoProduto;
    }

    public void setDescricaoProduto(String descricaoProduto) {
        this.descricaoProduto = descricaoProduto;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }
}
