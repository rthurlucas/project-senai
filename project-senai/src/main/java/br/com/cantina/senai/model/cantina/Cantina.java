package br.com.cantina.senai.model.cantina;

import br.com.cantina.senai.model.produto.Produto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cantina")
public class Cantina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cantina")
    private Long id;
    @JoinColumn(name = "id_produto")
    @ManyToOne(fetch = FetchType.LAZY)
    private Produto idProduto;
    private boolean disponivel = true;
}
