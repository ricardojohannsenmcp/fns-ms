package br.gov.ma.tce.controlador;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import br.gov.ma.tce.modelo.fns.EnteFns;
import br.gov.ma.tce.modelo.fns.EnteWrapper;
import br.gov.ma.tce.repository.EnteFnsRepository;

@Controller
@RequestMapping("/enteFns")
public class EnteFnsController {

	@Autowired
	private EnteFnsRepository enteFnsRepository;
	
	@GetMapping
	public ResponseEntity<Collection<EnteFns>> listar(){
		Collection<EnteFns> lista = enteFnsRepository.findAll();
		return ResponseEntity.ok(lista);
	}
	
	@Autowired
	private RestTemplate  restTemplate;
	
	
	@GetMapping("/pop")
	public ResponseEntity<EnteWrapper> popular() {
		
		EnteWrapper wr  = restTemplate.getForObject("https://consultafns.saude.gov.br/recursos/municipios/uf/MA", EnteWrapper.class);
		enteFnsRepository.saveAll( wr.getResultado())  ;
	    return new ResponseEntity<>(wr,HttpStatus.OK);
		
	}
}
