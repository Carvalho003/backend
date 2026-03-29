package school.sptech.EncantoPersonalizados.infrastructure.dto.usuario;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

public class ListUserDTO {

    @Schema(description =  "Id do usuário", example = "1")
    private Integer id;

    @Schema(description = "Nome do usuário", example = "João da Silva")
    private String nome;

    @Schema(description = "Cpf do usuário", example = "111.111.111-11")
    private String cpf;

    @Schema(description = "Email do usuário", example = "email@.com")
    private String email;
    private LocalDate dataNasc;

    @Schema(description = "Cargo de usuário", example = "Gerente")
    private String cargo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(LocalDate dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
}
