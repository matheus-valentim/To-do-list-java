package todolist.demo.controller;

import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import todolist.demo.dto.UsuarioRecordDto;
import todolist.demo.model.UsuarioModel;
import todolist.demo.repository.UsuarioRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RestController
public class UsuarioController {
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @GetMapping("/user")
    public ResponseEntity<List<UsuarioModel>> GetAllUsers(){

        return ResponseEntity.status(HttpStatus.OK).body(usuarioRepository.findAll());
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<Object> getOneUser(@PathVariable(value = "id") UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(usuarioRepository.findById(id));
    }
    @DeleteMapping("/user/{id}")
    public ResponseEntity<Object> DeleteUser(@PathVariable(value = "id")UUID id){
        usuarioRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("deletado");
    }
    @PutMapping("user/{id}")
    public ResponseEntity<Object> PutUser(@PathVariable(value = "id")UUID id, @RequestBody @Valid
                                            UsuarioRecordDto usuarioRecordDto){
        Optional <UsuarioModel> user = usuarioRepository.findById(id);
        if (user.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não existe.");
        }
        BeanUtils.copyProperties(usuarioRecordDto, user.get());
        return ResponseEntity.status(HttpStatus.OK).body(usuarioRepository.save(user.get()));
    }
}