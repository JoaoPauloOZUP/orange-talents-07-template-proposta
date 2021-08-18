package br.com.zupacademy.joao.propostas.model;

import br.com.zupacademy.joao.propostas.validator.documento.CPFOuCNPJ;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
public class Proposta {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull @CPFOuCNPJ
    private String documento;

    @NotBlank
    @NotNull
    @Email
    private String email;

    @NotBlank
    @NotNull
    private String nomeProposta;

    // Para não criar uma string somente com rua, bairro e número,
    // o @Embedded permite eu criar um objeto para tratar isso e o Hibernate mapeia
    // como campos de uma mesma tabela no banco de dados. Assim mantenho as coisas orquestradas!
    @Embedded
    @AttributeOverrides({
            // Motivo desta escolha é porque meu database está permitindo que estes campos aceitem valores nulos
            @AttributeOverride(name = "nomeRua", column = @Column(nullable = false)),
            @AttributeOverride(name = "numero", column = @Column(nullable = false)),
            @AttributeOverride(name = "nomeBairro", column = @Column(nullable = false))
    })
    private Endereco endereco;

    @PositiveOrZero @NotNull
    private Double salario;

    /**
     * @deprecated construtor padrão para o hibernate
     * */
    @Deprecated
    private Proposta() {}

    public Proposta(@NotNull String documento,
                    @NotBlank @Email String email,
                    @NotBlank String nomeProposta,
                    @NotNull Endereco edereco,
                    @Positive @NotNull Double salario) {

        this.documento = documento;
        this.email = email;
        this.nomeProposta = nomeProposta;
        this.endereco = edereco;
        this.salario = salario;
    }

    // Como o retorno 201 requer o ID do recurso, e isso também é solicitado pela feature,
    // após persistir no banco o objeto passa a ser gerenciado pelo o contexto da JPA e então tomo seu ID para retornar
    public Long getId() {
        return id;
    }

    public String getNomeProposta(){
        return nomeProposta;
    }

    public Double getSalario() {
        return salario;
    }
}
