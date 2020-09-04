package br.gov.ma.tce.controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.gov.ma.tce.modelo.fns.Bloco;
import br.gov.ma.tce.modelo.fns.EnteFns;
import br.gov.ma.tce.modelo.fns.EntidadeRepasse;
import br.gov.ma.tce.modelo.fns.EntidadeRepasseWrapper;
import br.gov.ma.tce.modelo.fns.Repasse;
import br.gov.ma.tce.modelo.fns.RepasseWrapper;
import br.gov.ma.tce.repository.EnteFnsRepository;
import br.gov.ma.tce.repository.EntidadeRepasseRepository;
import br.gov.ma.tce.repository.RepasseRepository;

@Controller
@RequestMapping("/repasses")
public class RepasseController {


	@Autowired
	private RepasseRepository repasseRepository;



	@Autowired
	private EntidadeRepasseRepository eer;


	public static final String URL_ENTES_FNS =  "https://consultafns.saude.gov.br/recursos/municipios/uf/MA";



	@Autowired
	private RestTemplate  restTemplate;

	/*@GetMapping
	public ResponseEntity<RepasseWrapper> salva(){

		RepasseWrapper repasseWraper = restTemplate.getForObject("https://consultafns.saude.gov.br/recursos/consulta-consolidada/repasse-bloco?ano=2020&coMunicipioIbge=211130&coTipoRepasse=M&count=10&page=1&sgUf=MA", RepasseWrapper.class);
		List<Repasse> repasses = repasseWraper.getResultado();
		//repasseRepository.saveAll(repasses);

		return new ResponseEntity<RepasseWrapper>(repasseWraper,HttpStatus.OK);
	}
	 */

	@Autowired
	private EnteFnsRepository repos;



	@GetMapping("/detalhar")
	public ResponseEntity<?> exibir() throws IOException{
		


		List<EnteFns> entes = repos.findAll();

		for(EnteFns ente : entes) {


			try {
				save(ente);
			} catch (Exception e) {
				continue;
			}





		}

		return  ResponseEntity.ok().body("ok");
	}


	@org.springframework.transaction.annotation.Transactional
	private void save(EnteFns ente) {


		try {
			EntidadeRepasseWrapper entidadeRepasseWrapper = restTemplate.getForObject("https://consultafns.saude.gov.br/recursos/consulta-consolidada/entidades?ano=2020&coMunicipioIbge="+ente.getCoMunicipioIbge()+"&coTipoRepasse=M&count=10&page=1&sgUf=MA",EntidadeRepasseWrapper.class );

			EntidadeRepasse entidadeRepasse = entidadeRepasseWrapper.getResultado().getDados().get(0);
	


			//blocos
			ResponseEntity<String> response = restTemplate.getForEntity("https://consultafns.saude.gov.br/recursos/consulta-consolidada/detalhe-entidade/?ano=2020&coMunicipioIbge="+ente.getCoMunicipioIbge()+"&coTipoRepasse=M", String.class);

			
			

			RepasseWrapper repasseWraper = restTemplate.getForObject("https://consultafns.saude.gov.br/recursos/consulta-consolidada/repasse-bloco?ano=2020&coMunicipioIbge="+ente.getCoMunicipioIbge()+"&coTipoRepasse=M&count=10&page=1&sgUf=MA", RepasseWrapper.class);

			List<Repasse> repasses = repasseWraper.getResultado();
			//cada repasse pertence a uma unidade
			
			
			

			ArrayList<Bloco> blocosCusteios = new ArrayList<>();
			ArrayList<Bloco> blocosInvestimentos = new ArrayList<>();

			String json = response.getBody();

			if(json != null && !json.isEmpty()) {

				ObjectMapper mapper = new ObjectMapper();
				JsonNode rootNode = mapper.readTree(json);
				JsonNode node1 = rootNode.elements().next();
				List<JsonNode> result = new ArrayList<JsonNode>();

				if(result != null && !result.isEmpty()) {
					node1.elements().forEachRemaining(result::add);
					JsonNode nodePrincipal = result.get(0);
					List<JsonNode> grupos =  new ArrayList<>();
					if(nodePrincipal != null) {

						nodePrincipal.elements().forEachRemaining(n ->{
							grupos.add(n);

						});
					}
					JsonNode custeios = grupos.get(0);
					if(custeios != null) {
					blocosCusteios =  mapper.readValue("["+custeios.toPrettyString()+"]", new TypeReference<ArrayList<Bloco>>() {});
					}

					JsonNode investimentos = grupos.get(1);
					if(investimentos != null) {
					blocosInvestimentos =  mapper.readValue("["+investimentos.toPrettyString()+"]", new TypeReference<ArrayList<Bloco>>() {});
					}

				}

			}





           if(!repasses.isEmpty()) {

			entidadeRepasse.setRepasses(repasses);
           }
			
			
			entidadeRepasse.setBlocos(new ArrayList<>());
			if(!blocosCusteios.isEmpty()) {
			entidadeRepasse.getBlocos().addAll(blocosCusteios);
			}
			if(!blocosInvestimentos.isEmpty()) {
			entidadeRepasse.getBlocos().addAll( blocosInvestimentos);
			}
			eer.save(entidadeRepasse);
		} catch (Exception e) {

			throw new RuntimeException("xibica");
		}

	}







}




//ObjectReader reader = mapper.readerFor(new TypeReference<Collection<Grupo>>() {});

//Collection<Grupo> groups = reader.readValue(custeios.toPrettyString());













// JsonNode node1 = rootNode.elements().next();



// JsonNode xibicaNode = node1.elements().next();


// System.out.println(xibicaNode.toPrettyString());



/*xibicaNode.elements().forEachRemaining(i ->{


	JsonNode xibiuNode = xibicaNode.elements().next();



	xibiuNode.elements().forEachRemaining(x ->{

		System.out.println("==========================================================");
		JsonNode xerecaNode = xibiuNode.elements().next();
		System.out.println(xerecaNode.toPrettyString());
		System.out.println("==========================================================");




	});


});*/

// ListaGrupoWrapper w = mapper.treeToValue(xibicaNode, ListaGrupoWrapper.class);




/*	rootNode.elements().forEachRemaining(i -> {


	System.out.println(i.toPrettyString());

});*/

/* 

 myMap = mapper.readValue(json, HashMap.class);


//System.out.println("Map is: "+myMap);




	ArrayList<Object> list = (ArrayList<Object>) myMap.get("resultado");
	//System.out.println(obj.toString());


	System.out.println(list.size());


	Object obj1 = list.get(0);

	//System.out.println(obj1.toString());



	Object obj2 = list.get(1);

	//System.out.println(obj2.toString());



	Object obj3 = list.get(2);

	//System.out.println(obj3.toString());

    Object obj4 = list.get(3);

	System.out.println(obj4.toString());*/


