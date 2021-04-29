package com.voxloud.provisioning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.voxloud.provisioning.service.IProvisioningService;

@RestController
@RequestMapping("/api/v1/provisioning")
public class ProvisioningController {

	@Autowired
	IProvisioningService iProvisioningService;
    
	@GetMapping("/{macAddress}")
	public ResponseEntity<String> getProvisioningFile(@PathVariable("macAddress") String macAddress) {
		String file = iProvisioningService.getProvisioningFile(macAddress);
		if(org.apache.commons.lang3.StringUtils.isNotEmpty(file)) {
			return ResponseEntity.ok(file);
		}
		return ResponseEntity.notFound().build();
	}
}