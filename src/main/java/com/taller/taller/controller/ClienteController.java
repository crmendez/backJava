package com.taller.taller.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.taller.taller.Exception.ResourceNotFoundException;
import com.taller.taller.model.Cliente;
import com.taller.taller.repository.ClienteRepository;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api")
public class ClienteController {
	@Autowired
	private ClienteRepository clienteRepository;
	
	@GetMapping(value = "/clientes") 
	public List<Cliente> getCliente() {
        return clienteRepository.findAll(Sort.by("id"));
        }
	
	@GetMapping(value = "/clientes/{id}") 
	   public Cliente findByCustomerId (@PathVariable Integer id){ 
	         return clienteRepository.findById(id).orElseThrow(()
	        		 -> new ResourceNotFoundException("Cliente "+id+" no encontrado"));
	         }
	
	@PostMapping(value = "/clientes")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Cliente save(@RequestBody Cliente cliente) {
		return clienteRepository.save(cliente);
	 }
	
	@PutMapping(value = "/clientes/{id}")
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	public ResponseEntity<Cliente> updateCliente(@PathVariable Integer id,
			@RequestBody Cliente nuevoCliente){
		
		return clienteRepository.findById(id).map(cliente -> {
			cliente.setNombres(nuevoCliente.getNombres());
			cliente.setApellido(nuevoCliente.getApellido());
			cliente.setDireccion(nuevoCliente.getDireccion());
			cliente.setCod_postal(nuevoCliente.getCod_postal());
			cliente.setTelefono(nuevoCliente.getTelefono());
			clienteRepository.save(cliente);
			return ResponseEntity.ok(cliente);
		}).orElseThrow(() -> new ResourceNotFoundException
				("Cliente "+id+" no encontrado"));
		
	}
	
	@DeleteMapping(value = "/clientes/{id}")
	public ResponseEntity<?> deleteCliente(@PathVariable Integer id){

		return clienteRepository.findById(id).map(cliente -> {
		clienteRepository.delete(cliente);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
        ).orElseThrow(() -> new ResourceNotFoundException
        		("Cliente "+id+" no encontrado"));
	
    }
	

}
