package todolist.demo.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationDto (@NotBlank String nome,@NotBlank String senha){
}
