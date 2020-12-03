package com.ticketlog.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ticketlog.domain.Cidade;
import com.ticketlog.domain.Estado;
import com.ticketlog.repositories.CidadeRepository;
import com.ticketlog.repositories.EstadoRepository;
import com.ticketlog.services.exceptions.DataIntegratyViolatonException;
import com.ticketlog.services.exceptions.ObjectNotFounfdException;

import javassist.tools.rmi.ObjectNotFoundException;

@Service
public class CidadeService {
	
	@Autowired
	private CidadeRepository repository;
	@Autowired
	private EstadoService estadoService;
	@Autowired
	private EstadoRepository estadoRepository;
	

	public Cidade insert(Cidade obj, Integer idEstado) throws ObjectNotFoundException {
		
		// Validando número no nome da cidade
		if(!obj.getNome().matches("[a-zA-ZáàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ' ']+")){
			throw new DataIntegratyViolatonException("Falha ao cadastrar cidade! Nome aceita apenas letras");
		}
		
		// Validando se a cidade já está cadastrada no sistema
		List<Cidade> cidades = repository.findAllByEstado(idEstado);
		for (Cidade cidade : cidades) {
			if(cidade.getNome().equals(obj.getNome())) {
				throw new DataIntegratyViolatonException("Falha ao cadastrar cidade! A mesma já possui uma instância na base de dados.");
			}
		}
		
		Estado estado = estadoService.findById(idEstado);
		obj.setId(null);
		obj.setEstado(estado);
		obj = repository.save(obj);
		estado.getCidades().add(obj);
		estadoRepository.save(estado);
		return obj;
	}


	public List<Cidade> findAllByEstado(Integer id, Double ValorDoDolar) throws ObjectNotFoundException {
		estadoService.findById(id);
		Double valorDoDolar = ValorDoDolar;
		Double custoPorCidadao = 123.45 * valorDoDolar;
		
		List<Cidade> list = repository.findAllByEstado(id);
		
		for (Cidade cidade : list) {
			if(cidade.getPopulacao() < 50001) {
				cidade.setCustoCidadeUS(custoPorCidadao * cidade.getPopulacao());
			} else {
				double aux = ((cidade.getPopulacao() - 50000) * (custoPorCidadao - custoPorCidadao * 0.123)) + (50000 * custoPorCidadao);
				cidade.setCustoCidadeUS(aux);
			}
		}
		return list;
	}
	
	public void deleteById(Integer id) throws ObjectNotFoundException {
		Cidade obj = findById(id);
		if(obj.getEstado().getNome().equals("Rio Grande do Sul")) {
			throw new DataIntegratyViolatonException("Falha ao excluir cidade! Cidades do Rio Grande do Sul não podem ser excluídas.");
		}
		repository.deleteById(id);
	}
	
	private Cidade findById(Integer id) throws ObjectNotFoundException {
		Optional<Cidade> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFounfdException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cidade.class.getName()));
	}
	

}
