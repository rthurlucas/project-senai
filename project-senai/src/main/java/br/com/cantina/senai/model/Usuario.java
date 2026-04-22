package br.com.cantina.senai.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cpf;
    private String telefone;

    @Enumerated
    public TipoUsuario tipoUsuario;

    public Usuario(Usuario usuario) {

    }

    public void excluir(){
        this.nome = "Unknow";
    }
}
