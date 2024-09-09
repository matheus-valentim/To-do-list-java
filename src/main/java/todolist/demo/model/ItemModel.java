package todolist.demo.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name ="tarefas")
public class ItemModel implements Serializable {
    private static final long serialVersionUId= 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  UUID id;
    private  String tarefa;
    private  String nome;

    public UUID getId(){
        return id;
    }
    public String getTarefa(){
        return tarefa;
    }
    public String getNome(){
        return nome;
    }
    public void setTarefa(String tarefa){
        this.tarefa = tarefa;
    }
    public void setNome(String nome){
        this.nome = nome;
    }
    public void setId(UUID id){
        this.id = id;
    }
}

