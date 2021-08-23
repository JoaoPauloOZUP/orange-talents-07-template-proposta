package br.com.zupacademy.joao.propostas.model;

import br.com.zupacademy.joao.propostas.controller.proposta.clients.dto.avaliacaofinanceira.AvaliacaoFinanceiraResponse;
import br.com.zupacademy.joao.propostas.controller.proposta.clients.dto.cartao.CartaoResponse;
import br.com.zupacademy.joao.propostas.controller.proposta.utils.EstadoAvaliacao;
import br.com.zupacademy.joao.propostas.controller.proposta.utils.EstadoCartao;
import br.com.zupacademy.joao.propostas.validator.documento.CPFOuCNPJ;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
public class Proposta {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull @CPFOuCNPJ
    @Column(unique = true)
    private String documento;

    @NotBlank
    @NotNull
    @Email
    private String email;

    @NotBlank
    @NotNull
    private String nome;

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

    @NotNull
    @Enumerated(EnumType.STRING)
    private EstadoAvaliacao estadoAvaliacao = EstadoAvaliacao.NAO_ELEGIVEL;

    @Enumerated(EnumType.STRING)
    private EstadoCartao estadoCartao;

    @OneToOne(cascade = CascadeType.MERGE)
    private Cartao cartao;

    /**
     * @deprecated construtor padrão para o hibernate
     * */
    @Deprecated
    private Proposta() {}

    public Proposta(@NotNull String documento,
                    @NotBlank @Email String email,
                    @NotBlank String nome,
                    @NotNull Endereco edereco,
                    @Positive @NotNull Double salario) {

        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = edereco;
        this.salario = salario;
    }

    public void atualizarEstadoDaAvaliacao(AvaliacaoFinanceiraResponse response) {
        this.estadoAvaliacao = response.getResultadoSolicitacao();

        if(isElegivel()) {
            this.estadoCartao = EstadoCartao.AGUARDANDO_CARTAO;
        }
    }

    public boolean isElegivel() {
        return estadoAvaliacao.equals(EstadoAvaliacao.ELEGIVEL);
    }

    public void incluirCartao(Cartao cartao) {
        this.cartao = cartao;
        alterarEstadoCartaoParaObtido();
    }

    private void alterarEstadoCartaoParaObtido() {
        this.estadoCartao = EstadoCartao.CARTAO_OBTIDO;
    }

    // Como o retorno 201 requer o ID do recurso, e isso também é solicitado pela feature,
    // após persistir no banco o objeto passa a ser gerenciado pelo o contexto da JPA e então tomo seu ID para retornar
    public Long getId() {
        return id;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome(){
        return nome;
    }

    public Double getSalario() {
        return salario;
    }

    public EstadoAvaliacao getEstadoAvaliacao() {
        return estadoAvaliacao;
    }
}
