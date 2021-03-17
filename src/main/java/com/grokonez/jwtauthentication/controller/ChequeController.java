
package com.grokonez.jwtauthentication.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Lob;

import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
//import javax.validation.Valid;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.attijari.demo.exception.ResourceNotFoundException;
import com.attijari.demo.metier.DiskFileExplorer;
import com.grokonez.jwtauthentication.model.Cheque;
import com.grokonez.jwtauthentication.model.Cheque;
import com.grokonez.jwtauthentication.repository.IChequeRepository;
import com.grokonez.jwtauthentication.security.services.ChequeService;

@RestController

@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ChequeController {

	@Autowired
	private IChequeRepository chequeRepository;
	@Autowired
	private ChequeService ChequeService;
	
	
	
	
	
	@GetMapping("/getImages")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ArrayList getImages() {
		
		//return ChequeService.getAllPictFromDirectory("//bank-sud.tn/shared_doc/FirasControleVisuelTst");
		
		return ChequeService.getChequesData();
	}
	
	
	
	@GetMapping("/Cheques")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public List<Cheque> getAllCheques() {
    	ArrayList<ArrayList<String>> listOLists = new ArrayList<ArrayList<String>>();
    	List<Cheque> cheques=new ArrayList<Cheque>();

		String pathToExplore = "//bank-sud.tn/shared_doc/FirasControleVisuelTst";
  	  DiskFileExplorer diskFileExplorer = new DiskFileExplorer(pathToExplore, true);
  	  Long start = System.currentTimeMillis();
  	 // listOLists= diskFileExplorer.list();
		for (ArrayList<String> l : listOLists) {
	    	Cheque cheq = new Cheque();
	    	File file = new File("D://testDirectory/new.jpg");
			byte[] bFile = new byte[(int) file.length()];
	    	cheq.setNcpTireur(l.get(2));
    		cheq.setNumChq(l.get(3));

    		try {
				FileInputStream fileInputStream = new FileInputStream(file);
				// convert file into array of bytes
				fileInputStream.read(bFile);
				fileInputStream.close();


			} catch (Exception e) {
				e.printStackTrace();
			}	
    		cheq.setRecto(bFile);
    		chequeRepository.save(cheq);
	}
		
		return chequeRepository.findAll();
	}

	@GetMapping("/Cheques/{ChequeId}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public Optional<Cheque> getChequeById(@PathVariable(value = "ChequeId") Long ChequeId) {
		return chequeRepository.findById(ChequeId);
	}

	@PostMapping("/Cheques")
	//@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public Cheque createCheque(@RequestBody Cheque Cheque) {
		return chequeRepository.save(Cheque);
	}
	@PostMapping("/ChequesTest")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	//@PreAuthorize("PermitAll")
	public void createCheques() {
//		 ChequeService.addCheques();
	}
	

	@PutMapping("/Cheques/{ChequeId}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public Cheque updateCheque(@PathVariable Long ChequeId, @RequestBody Cheque ChequeRequest) {
		return chequeRepository.findById(ChequeId).map(cheque -> {
			

			cheque.setNumChq(ChequeRequest.getNumChq());
			cheque.setRibTireur(ChequeRequest.getRibTireur());
			cheque.setRibBenif(ChequeRequest.getRibBenif());
			cheque.setCodeBanqBenif(ChequeRequest.getCodeBanqBenif());
			cheque.setMontant(ChequeRequest.getMontant());
			cheque.setMotif1(ChequeRequest.getMotif1());
			cheque.setMotif2(ChequeRequest.getMotif2());
			cheque.setMotif3(ChequeRequest.getMotif3());
			cheque.setMotif4(ChequeRequest.getMotif4());


			return chequeRepository.save(cheque);
		}).orElseThrow(() -> new ResourceNotFoundException("ChequeId " + ChequeId + " not found"));
	}

	@DeleteMapping("/Cheques/{ChequeId}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> deleteCheque(@PathVariable Long ChequeId) {
		return chequeRepository.findById(ChequeId).map(Cheque -> {
			chequeRepository.delete(Cheque);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("ChequeId " + ChequeId + " not found"));
	}

	
	
	
	
	
}
