package com.ticketlog.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ticketlog.domain.Cidade;
import com.ticketlog.services.CidadeService;

import javassist.tools.rmi.ObjectNotFoundException;

@RestController
@RequestMapping("/api/cidades")
@CrossOrigin("*")
public class CidadeResource {
	
	// !!!!!!! Implementar os tratamentos de exceção após finalizar a api
	
	@Autowired
	private CidadeService service;
	
	@PostMapping("/estado={idEstado}")
	public ResponseEntity<Cidade> insert(@PathVariable Integer idEstado, @Valid @RequestBody Cidade obj) throws ObjectNotFoundException {
		Cidade newObj = service.insert(obj, idEstado);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(newObj);
	}
	
	@GetMapping("/estado={id}/dolar={valorDoDolar}")
	public ResponseEntity<List<Cidade>> findAllByEstado(@PathVariable Integer id, @PathVariable Double valorDoDolar) throws ObjectNotFoundException {
		List<Cidade> list = service.findAllByEstado(id, valorDoDolar);
		return ResponseEntity.ok().body(list);
	}
	
	@DeleteMapping("/delete={id}")
	public ResponseEntity<Void> deleteById(@PathVariable Integer id) throws ObjectNotFoundException {
		service.deleteById(id);
		return ResponseEntity.noContent().build();
	}

}
