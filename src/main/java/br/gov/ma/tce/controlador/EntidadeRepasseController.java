package br.gov.ma.tce.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.gov.ma.tce.dto.Form;
import br.gov.ma.tce.modelo.fns.Bloco;
import br.gov.ma.tce.modelo.fns.EntidadeRepasse;
import br.gov.ma.tce.modelo.fns.ImportacaoService;
import br.gov.ma.tce.modelo.fns.Repasse;
import br.gov.ma.tce.repository.BlocoRepository;
import br.gov.ma.tce.repository.EntidadeRepasseRepository;
import br.gov.ma.tce.repository.RepasseRepository;

@RestController
@RequestMapping("/entidadesRepasse")
public class EntidadeRepasseController {
	
	@Autowired
	private EntidadeRepasseRepository entidadeRepasseRepository;
	
	@Autowired
	private BlocoRepository blocoRepository;
	
	@Autowired
	private RepasseRepository repasseRepository;
	
	@Autowired
	private ImportacaoService importacaoService;
	

	@ResponseStatus(value=HttpStatus.OK)
	@PostMapping
	public void importar(@RequestBody Form form) {
		importacaoService.importar(form.getAno());
	}
	
	
	@GetMapping
	public ResponseEntity<List<EntidadeRepasse>>listar(){
		List<EntidadeRepasse> lista =  (List<EntidadeRepasse>) entidadeRepasseRepository.findAll();
		return ResponseEntity.ok(lista);
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<EntidadeRepasse>recuperar(@PathVariable("id")Long id){
		EntidadeRepasse entidadeRepasse =  entidadeRepasseRepository.getOne(id);
		return ResponseEntity.ok(entidadeRepasse );
	}
	
	
	@ResponseStatus(value=HttpStatus.OK)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id")Long id){
		entidadeRepasseRepository.deleteById(id);
	} 
	
	@GetMapping("/{id}/repasses")
	public ResponseEntity<List<Repasse>> repassesPorEntidadeRepasse(@PathVariable("id")Long id){
		List<Repasse> repasses =  repasseRepository.findByEntidadeRepasse(id);
		return ResponseEntity.ok(repasses);
	} 
	
	@GetMapping("/{id}/blocos")
	public ResponseEntity<List<Bloco>> blocosPorEntidadeRepasse(@PathVariable("id")Long id){
		List<Bloco> blocos =  blocoRepository.findByEntidadeRepasse(id);
		return ResponseEntity.ok(blocos);
	} 
	
}
