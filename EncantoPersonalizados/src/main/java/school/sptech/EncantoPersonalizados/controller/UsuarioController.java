package school.sptech.EncantoPersonalizados.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import school.sptech.EncantoPersonalizados.dto.produto.ProdutoMapper;
import school.sptech.EncantoPersonalizados.dto.usuario.UsuarioMapper;
import school.sptech.EncantoPersonalizados.dto.usuario.UsuarioRequestDTO;
import school.sptech.EncantoPersonalizados.dto.usuario.UsuarioResponseDTO;
import school.sptech.EncantoPersonalizados.entities.Produto;
import school.sptech.EncantoPersonalizados.entities.Usuario;
import school.sptech.EncantoPersonalizados.service.UsuarioService;

import java.io.IOException;
import java.util.List;


    @RestController
    @RequestMapping("/usuarios")
    public class UsuarioController {

        private final UsuarioService service;
        private final UsuarioService usuarioService;

        @Operation(description = "Lista usuário por ID")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Retorna o usuário",
            content = @Content(schema = @Schema(implementation = UsuarioResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Não encontra o usuário")
        })
        @GetMapping("/{id}")
        public ResponseEntity<UsuarioResponseDTO> getById(
                @Parameter(description = "Id para encontrar o usuário", example = "1")
                @PathVariable Integer id
        ){
            UsuarioResponseDTO dto = service.getById(id);
            if(dto == null) {
                return ResponseEntity.status(404).build();
            }
            return ResponseEntity.status(200).body(dto);
        }

        @Operation(description = "Listar todos os usuários")
        @ApiResponses({
                @ApiResponse(responseCode = "200", description = "Retorna uma lista de usuários (Aceita filtros por status, cargo e nome)",
                content = @Content(array = @ArraySchema(schema =  @Schema(implementation = UsuarioResponseDTO.class)))),
                @ApiResponse(responseCode = "204", description = "Não encontrou usuários")
        })
        @GetMapping
        @SecurityRequirement(name = "Bearer")
        public ResponseEntity<Page<UsuarioResponseDTO>> get(
                @RequestParam(required = false) String search,
                @RequestParam(required = false) String cargo,
                @RequestParam(required = false) Boolean status,
                @RequestParam(defaultValue = "0") int page
        ){
            Page<Usuario> resposta = service.get(search, cargo, status, page);
            if(!resposta.isEmpty()){
                return ResponseEntity.status(200).body(resposta.map(UsuarioMapper::toResponseDTO));
            }
            return ResponseEntity.status(204).build();
        }

        @Operation
        @ApiResponse(responseCode = "201", description = "Cria um usuário",
        content = @Content(mediaType = "application/json"))
        @PostMapping
        @SecurityRequirement(name = "Bearer")
        public ResponseEntity<UsuarioResponseDTO> store(@RequestBody @Valid UsuarioRequestDTO usuarioRequestDTO){

            Usuario usuario = UsuarioMapper.toEntity(usuarioRequestDTO);

            UsuarioResponseDTO responseDto = service.store(usuario);

            return ResponseEntity.status(201).body(responseDto);

        }

        @Operation(description = "Atualizar usuário")
        @ApiResponses({
                @ApiResponse(responseCode = "200", description = "Sucesso ao atualizar",
                        content =  @Content(schema = @Schema(implementation = UsuarioResponseDTO.class))),
                @ApiResponse(responseCode = "404", description = "Não encontra o usuário")
        })
        @PutMapping("/{id}")
        public ResponseEntity<UsuarioResponseDTO> update(

                @RequestBody Usuario usuario,
                @Parameter(description = "Id do usuário", example = "1")
                @PathVariable Integer id
        ){
            UsuarioResponseDTO dto = service.update(usuario, id);
            if(dto == null) return ResponseEntity.status(404).build();
            return ResponseEntity.status(200).body(dto);
        }

        @Operation(description = "Atualizar usuário")
        @ApiResponses({
                @ApiResponse(responseCode = "204", description = "Sucesso ao desativar"),
                @ApiResponse(responseCode = "404", description = "Não encontra o usuário")
        })
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> destroy(
                @Parameter(description = "Id do usuário", example = "1")
                @PathVariable Integer id
        ){
            if(service.destroy(id)){
                return ResponseEntity.status(204).build();
            };
            return ResponseEntity.status(404).build();
        }

        public UsuarioController(UsuarioService service, UsuarioService usuarioService) {
            this.service = service;
            this.usuarioService = usuarioService;
        }

        @GetMapping("/ordenadosPorNome")
        public ResponseEntity<List<Usuario>> getUsuariosOrdenados() {
            List<Usuario> usuariosOrdenados = usuarioService.getUsuariosComMergeSortPorNome();

            if (usuariosOrdenados.isEmpty()) {
                return ResponseEntity.status(204).build();
            }

            return ResponseEntity.status(200).body(usuariosOrdenados);
        }

        @Operation(description = "Mudar a senha")
        @ApiResponses({
                @ApiResponse(responseCode = "200", description = "Senha alterada com sucesso"),
                @ApiResponse(responseCode = "400", description = "Erro na validação de dados"),
                @ApiResponse(responseCode = "401", description = "Senha atual incorreta"),
                @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
        })
        @PatchMapping("/{id}")
        public ResponseEntity<Void> updatePassword(
                @Parameter(description = "Id do usuário", example = "1")
                @PathVariable Integer id,
                @Parameter(description = "Senha nova do usuário", example = "Senha123!@")
                @RequestParam String newPassword,
                @Parameter(description = "Senha antiga do usuário", example = "Senha123!@")
                @RequestParam String oldPassword
        ){
                try {
                    UsuarioResponseDTO dto = service.updatePassword(oldPassword, newPassword, id);

                    if (dto == null) {
                        return  ResponseEntity.status(404).build();
                    }

                    return ResponseEntity.status(200).build();
                } catch (RuntimeException e) {
                    if (e.getMessage().equals("Senha atual incorreta")) {
                        return ResponseEntity.status(401).build();
                    }

                    if (e.getMessage().equals("A nova senha não pode ser igual a atual")) {
                        return ResponseEntity.status(400).build();
                    }

                    return ResponseEntity.status(500).build();
                }

        }

        @Operation(description = "Inserção de foto")
        @ApiResponses({
                @ApiResponse(responseCode = "201", description = "Foto salva com sucesso"),
                @ApiResponse(responseCode = "400", description = "Parâmetros incorretos"),
                @ApiResponse(responseCode = "404", description = "Funcionário não encontrado")
        })
        @PostMapping("/foto/{id}")
        public ResponseEntity<UsuarioResponseDTO> storeFoto(
                @PathVariable Integer id,
                @RequestParam("foto") MultipartFile file
        ) throws IOException {
            Usuario usuario = service.storeFoto(id, file);

            return ResponseEntity.status(201).body(UsuarioMapper.toResponseDTO(usuario));
        };

        @Operation(description = "Deletar foto")
        @ApiResponses({
                @ApiResponse(responseCode = "204", description = "Foto deletada com sucesso"),
                @ApiResponse(responseCode = "404", description = "Foto não encontrado"),
                @ApiResponse(responseCode = "400", description = "Parâmetros incorretos")
        })
        @DeleteMapping("/foto/{id}")
        public ResponseEntity<Void> deleteFoto(
                @PathVariable Integer id
        ) throws IOException {
            service.deleteFoto(id);
            return ResponseEntity.status(204).build();
        }
    }

