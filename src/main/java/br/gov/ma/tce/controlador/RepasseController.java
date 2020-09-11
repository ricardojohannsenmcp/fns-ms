package br.gov.ma.tce.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.gov.ma.tce.modelo.fns.EntidadeRepasse;
import br.gov.ma.tce.repository.EntidadeRepasseRepository;

@Controller
@RequestMapping("/repasses")
public class RepasseController {

	@Autowired
	private EntidadeRepasseRepository entidadeRepasseRepository;
	

	
	
	@GetMapping
	ResponseEntity<List<EntidadeRepasse>> listar(){
		List<EntidadeRepasse> entidades =  entidadeRepasseRepository.findAll();
		return ResponseEntity.ok(entidades);
	}


}
