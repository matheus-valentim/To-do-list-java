package todolist.demo.dto;

import jakarta.validation.constraints.NotBlank;
import todolist.demo.Role.UsuarioRole;

public record UsuarioRecordDto(@NotBlank String nome,@NotBlank String email,@NotBlank String senha, UsuarioRole role) {
}
