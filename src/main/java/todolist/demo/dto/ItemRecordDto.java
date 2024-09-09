package todolist.demo.dto;

import jakarta.validation.constraints.NotBlank;

public record ItemRecordDto(@NotBlank String tarefa, @NotBlank String nome) {
}
