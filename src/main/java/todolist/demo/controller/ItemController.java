package todolist.demo.controller;

import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import todolist.demo.dto.ItemRecordDto;
import todolist.demo.model.ItemModel;
import todolist.demo.model.UsuarioModel;
import todolist.demo.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class ItemController {
    @Autowired
    ItemRepository itemRepository;
    @GetMapping("/tarefas")
    public ResponseEntity<List<ItemModel>> getAllTarefas(){
        return ResponseEntity.status(HttpStatus.OK).body(itemRepository.findAll());
    }
    @PostMapping("/tarefas")
    public ResponseEntity<ItemModel> SaveItem(@RequestBody @Valid ItemRecordDto itemRecordDto){
        var itemModel = new ItemModel();
        BeanUtils.copyProperties(itemRecordDto,itemModel );
        return ResponseEntity.status(HttpStatus.CREATED).body(itemRepository.save(itemModel));
    }
    @DeleteMapping("/tarefas/{id}")
    public ResponseEntity <Object> DeletarItem(@PathVariable(value = "id")UUID id){
        itemRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Objeto deletado");
    }
    @GetMapping("/tarefas/{id}")
    public ResponseEntity<Object> PegarUmItem(@PathVariable(value = "id")UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(itemRepository.findById(id));
    }
    @PutMapping("/tarefas/{id}")
    public ResponseEntity<Object> AtualizarItem(@RequestBody @Valid ItemRecordDto itemRecordDto ,@PathVariable(value = "id")UUID id){
        Optional<ItemModel> tarefa = itemRepository.findById(id);
        if (tarefa.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não existe.");
        }
        BeanUtils.copyProperties(itemRecordDto, tarefa.get());
        return ResponseEntity.status(HttpStatus.OK).body(itemRepository.save(tarefa.get()));
    }
}

