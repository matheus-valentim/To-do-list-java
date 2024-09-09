package todolist.demo.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import todolist.demo.dto.AuthenticationDto;
import todolist.demo.dto.LoginResponseDto;
import todolist.demo.dto.UsuarioRecordDto;
import todolist.demo.infra.security.TokenService;
import todolist.demo.model.UsuarioModel;
import todolist.demo.repository.UsuarioRepository;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TokenService tokenService;
    @PostMapping("/login")
    public ResponseEntity login (@RequestBody @Valid AuthenticationDto authenticationDto){
        var userNamePassword = new UsernamePasswordAuthenticationToken(authenticationDto.nome(), authenticationDto.senha());
        var auth = this.authenticationManager.authenticate(userNamePassword);
        var token = tokenService.generateToken((UsuarioModel) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDto(token));
    }
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid UsuarioRecordDto usuarioRecordDto){

        if (usuarioRepository.findByNome(usuarioRecordDto.nome())!=null) {
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario já existe.");
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(usuarioRecordDto.senha());
        UsuarioModel usuarioModel = new UsuarioModel(usuarioRecordDto.email(), usuarioRecordDto.nome(),encryptedPassword,usuarioRecordDto.role());
        this.usuarioRepository.save(usuarioModel);
        return ResponseEntity.status(HttpStatus.OK).body("usuario criado.");
    }
}
