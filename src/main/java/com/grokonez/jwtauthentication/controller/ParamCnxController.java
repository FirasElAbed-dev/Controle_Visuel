package com.grokonez.jwtauthentication.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.attijari.demo.exception.ResourceNotFoundException;
import com.grokonez.jwtauthentication.model.ParamCnxDB;
import com.grokonez.jwtauthentication.repository.IParamCnxRepository;
import com.grokonez.jwtauthentication.security.services.ParamCnxService;



@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ParamCnxController {
	@Autowired
	private  ParamCnxService paramCnxService;
	@Autowired
	private  IParamCnxRepository paramCnxDBRepository;
	
	@GetMapping("/params")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public List<ParamCnxDB> getAllParamCnx() {
		return paramCnxService.getAllparamCnxDB();
	}
	
	@GetMapping("/params/id/{paramCnxDBId}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public Optional<ParamCnxDB> getParamCnxDBById(@PathVariable(value = "paramCnxDBId") Long paramCnxDBId) {
		return paramCnxDBRepository.findById(paramCnxDBId);
	}

	
	@PostMapping("/params")
	@PreAuthorize("hasRole('ADMIN')")
	public ParamCnxDB createParamCnxDB(@RequestBody ParamCnxDB param) {
		return paramCnxDBRepository.save(param);
	}

	@PutMapping("/params/{paramCnxDBId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ParamCnxDB updateParamCnxDB(@PathVariable Long paramCnxDBId, @RequestBody ParamCnxDB paramCnxDBRequest) {
		return paramCnxDBRepository.findById(paramCnxDBId).map(paramCnxDB -> {
			paramCnxDB.setUrl(paramCnxDBRequest.getUrl());
			paramCnxDB.setPort(paramCnxDBRequest.getPort());
			paramCnxDB.setSid(paramCnxDBRequest.getSid());
			paramCnxDB.setUser(paramCnxDBRequest.getUser());
			paramCnxDB.setPswd(paramCnxDBRequest.getPswd());
			return paramCnxDBRepository.save(paramCnxDB);
		}).orElseThrow(() -> new ResourceNotFoundException("ParamCnxDBId " + paramCnxDBId + " not found"));
	}


	@DeleteMapping("/params/{paramCnxDBId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteParamCnxDB(@PathVariable Long paramCnxDBId) {
		return paramCnxDBRepository.findById(paramCnxDBId).map(paramCnxDB -> {
			paramCnxDBRepository.delete(paramCnxDB);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("ParamCnxDBId " + paramCnxDBId + " not found"));
	}
	
	
	
	
	
	
	@GetMapping("/params/test")
	@ResponseBody
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public boolean testParamCnxDB(@RequestParam String url,@RequestParam int port,@RequestParam String sid,@RequestParam String user,@RequestParam String pswd ) {
		System.out.println(port);		
		String paramURL =url;
		int paramPort = port;
		String paramSid = sid;
		String paramUser = user;
		String paramPswd = pswd;
		
		return ParamCnxService.checkDBcnx(paramURL, paramPort, paramSid, paramUser, paramPswd);
	}
	

}
