package br.gov.ma.tce.modelo.fns;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.gov.ma.tce.repository.BlocoRepository;
import br.gov.ma.tce.repository.EnteFnsRepository;
import br.gov.ma.tce.repository.EntidadeRepasseRepository;

@Service
public class ImportacaoService {


	@Autowired
	private EntidadeRepasseRepository entidadeRepasseRepository;

	@Autowired
	private BlocoRepository blocoRepository;

	@Autowired
	private RestTemplate  restTemplate;

	@Autowired
	private EnteFnsRepository enteRepository;



	public void importar(Integer ano){

		System.out.println("ano: "+ ano);
		List<EnteFns> entes = enteRepository.findAll();
		for(EnteFns ente : entes) {
			try {
				save(ente,ano);
			} catch (Exception e) {
				continue;
			}
		}
	}

	public void importarEnte(EnteFns ente,Integer ano){
		save(ente,ano);		
	}

	@Transactional
	private void save(EnteFns ente, Integer ano) {
		try {

			EntidadeRepasse er =  entidadeRepasseRepository.findByCodigoMunicipioIBGEAndExercicio(ente.getCoMunicipioIbge().toString(), ano).orElse(null);
			if(er != null) {

				entidadeRepasseRepository.deleteById(er.getEntidadeRepasseId());
			}


			System.out.println("Ente: "+ente.getNoMunicipio());
			EntidadeRepasseWrapper entidadeRepasseWrapper = restTemplate.getForObject("https://consultafns.saude.gov.br/recursos/consulta-consolidada/entidades?ano="+ano+"&coMunicipioIbge="+ente.getCoMunicipioIbge()+"&coTipoRepasse=M&count=10&page=1&sgUf=MA",EntidadeRepasseWrapper.class );
			EntidadeRepasse entidadeRepasse = entidadeRepasseWrapper.getResultado().getDados().get(0);
			//blocos
			ResponseEntity<String> response = restTemplate.getForEntity("https://consultafns.saude.gov.br/recursos/consulta-consolidada/detalhe-entidade/?ano="+ano+"&coMunicipioIbge="+ente.getCoMunicipioIbge()+"&coTipoRepasse=M", String.class);
			RepasseWrapper repasseWraper = restTemplate.getForObject("https://consultafns.saude.gov.br/recursos/consulta-consolidada/repasse-bloco?ano="+ano+"&coMunicipioIbge="+ente.getCoMunicipioIbge()+"&coTipoRepasse=M&count=10&page=1&sgUf=MA", RepasseWrapper.class);
			List<Repasse> repasses = repasseWraper.getResultado();

			//System.out.println("::::: Repasses = "+repasses.size());
			//cada repasse pertence a uma unidade
			ArrayList<Bloco> blocosCusteios = new ArrayList<>();
			ArrayList<Bloco> blocosInvestimentos = new ArrayList<>();
			String json = response.getBody();

			//System.out.println(json);
			if(json != null && !json.isEmpty()) {
				//System.out.println("json não é nulo  e não é vazio");
				ObjectMapper mapper = new ObjectMapper();
				JsonNode rootNode = mapper.readTree(json);
				JsonNode node1 =  null;
				if(rootNode != null) {
					node1 = rootNode.elements().next();
				}
				List<JsonNode> result = new ArrayList<JsonNode>();
				if(node1 != null ) {
					//System.out.println("result não é nulo e não é vazio");
					node1.elements().forEachRemaining(result::add);
					JsonNode nodePrincipal = result.get(0);


					List<JsonNode> grupos =  new ArrayList<>();
					if(nodePrincipal != null) {
						//System.out.println("node principal não é nulo");
						nodePrincipal.elements().forEachRemaining(n ->{
							grupos.add(n);
						});
					}
					JsonNode custeios = null;
					if(!grupos.isEmpty() && grupos.get(0) != null ) {
						custeios = grupos.get(0);
					}
					if(custeios != null) {
						//System.out.println("custeios não é nulo");
						blocosCusteios =  mapper.readValue("["+custeios.toPrettyString()+"]", new TypeReference<ArrayList<Bloco>>() {});
					}
					JsonNode investimentos = null;
					if(grupos.size() > 1 && grupos.get(1) != null) {
						
						investimentos = grupos.get(1);

					}
					
					
					if(investimentos != null) {
						//System.out.println("investimento não é nulo");
						blocosInvestimentos =  mapper.readValue("["+investimentos.toPrettyString()+"]", new TypeReference<ArrayList<Bloco>>() {});
					}
				}
			}

			//System.out.println("===> Custeios: "+blocosCusteios.size());
			//System.out.println("---> Investimentos; "+blocosInvestimentos.size());



			List<Bloco> blocos = new ArrayList<>();

			for(Bloco bloco : blocosCusteios) {

				blocos.add(bloco);
			}

			for(Bloco bloco : blocosInvestimentos) {
				blocos.add(bloco);
			}

			if(!repasses.isEmpty()) {
				entidadeRepasse.setRepasses(repasses);
				for(Repasse r : entidadeRepasse.getRepasses()) {
					r.setEntidadeRepasse(entidadeRepasse);
					for(ItemRepasse i : r.getRepasses()) {

						i.setRepasse(r);
					}
				}
			}
			/*entidadeRepasse.setBlocos(new ArrayList<>());
			if(!blocosCusteios.isEmpty()) {
				entidadeRepasse.getBlocos().addAll(blocosCusteios);
			}*/
			/*if(!blocosInvestimentos.isEmpty()) {
				entidadeRepasse.getBlocos().addAll( blocosInvestimentos);
			}*/
			
			entidadeRepasse.setExercicio(ano);
			entidadeRepasse.setTipoRepasse("MUNICIPAL");
			EntidadeRepasse entidadeSalva = entidadeRepasseRepository.save(entidadeRepasse);



			for(Bloco b : blocos) {

				b.setEntidadeRepasse(entidadeSalva);

				//System.out.println("====== Bloco =======");
				//System.out.println("cod "+b.getCodigo()+" desc "+b.getDescricao());

				for(Grupo g : b.getListaGrupo()) {
					//  System.out.println("====== Grupo =======");
					//System.out.println("cod "+g.getCodigo()+" desc "+g.getDescricao());
					g.setBloco(b);
					for(Componente c : g.getListaGrupo()) {
						c.setGrupo(g);
						//System.out.println("====== Componente =======");
						//System.out.println("co bloco "+c.getCoBloco()+" desc "+b.getDescricao());

					}

				}
			}

			blocoRepository.saveAll(blocos);


		} catch (Exception e) {

			e.printStackTrace();
			throw new RuntimeException("xibica");
		}

	}




}
