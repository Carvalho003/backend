package school.sptech.EncantoPersonalizados.infrastructure.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.EncantoPersonalizados.infrastructure.dto.usuario.LoginRequestDTO;
import school.sptech.EncantoPersonalizados.infrastructure.dto.usuario.UserTokenDTO;
import school.sptech.EncantoPersonalizados.infrastructure.dto.usuario.UsuarioMapper;
import school.sptech.EncantoPersonalizados.core.domain.Usuario;
import school.sptech.EncantoPersonalizados.core.application.usecase.login.LoginUseCase;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final LoginUseCase loginUseCase;

    public LoginController(LoginUseCase loginUseCase) {
        this.loginUseCase = loginUseCase;
    }

    @Operation(description = "Autenticar usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sucesso ao autenticar"),
            @ApiResponse(responseCode = "404", description = "Falha ao autenticar")
    })
    @PostMapping()
    public ResponseEntity<UserTokenDTO> login(@RequestBody LoginRequestDTO loginRequestDTO){

        final Usuario usuario = UsuarioMapper.of(loginRequestDTO);

        boolean rememberMe = loginRequestDTO.getRememberMe();

        UserTokenDTO userTokenDTO = loginUseCase.validateLogin(usuario.getEmail(), usuario.getPassword(), rememberMe);

        if(userTokenDTO == null){
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(userTokenDTO);

    }

    @Operation(description = "Atualizar senha do usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Sucesso ao atualizar"),
            @ApiResponse(responseCode = "404", description = "Não encontra o usuário")
    })
    @PatchMapping("update/{id}/password")
    public ResponseEntity<Void> updatePassword(@PathVariable Integer id,
                                               @RequestBody Usuario usuario){
        if(loginUseCase.updatePassword(usuario, id)){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }
}
