package com.example.demo.service.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.domain.Client;
import com.example.demo.domain.DTO.ClientNewDTO;
import com.example.demo.domain.enums.ClientType;
import com.example.demo.repositories.ClientRepository;
import com.example.demo.resources.exception.FieldMessage;
import com.example.demo.service.validation.utils.BR;

public class ClientInsertValidation implements ConstraintValidator<ClientInsert, ClientNewDTO> {
	
	@Autowired
	private ClientRepository repo;
	
	@Override
	 public void initialize(ClientInsert ann) {
	 }
	 @Override
	 public boolean isValid(ClientNewDTO objDto, ConstraintValidatorContext context) {
	 List<FieldMessage> list = new ArrayList<>();
	 
	 if (objDto.getClientType().equals(ClientType.PHYSICALPERSON.getCod()) && !BR.isValidCPF(objDto.getCpfOrCnpj())) {
		 list.add(new FieldMessage("cnpjOrCnpj", "Invalid CPF")); 
	 }
	 
	 if (objDto.getClientType().equals(ClientType.LEGALPERSON.getCod()) && !BR.isValidCNPJ(objDto.getCpfOrCnpj())) {
		 list.add(new FieldMessage("cnpjOrCnpj", "Invalid CNPJ")); 
	 }
	 
	 Client aux = repo.findByEmail(objDto.getEmail());
	 
	 if( aux != null) {
		 list.add(new FieldMessage("email", "Existing email"));
	 }
	 
	 

	 for (FieldMessage e : list) {
	 context.disableDefaultConstraintViolation();
	 context.buildConstraintViolationWithTemplate(e.getMessage())
	 .addPropertyNode(e.getFieldName()).addConstraintViolation();
	 }
	 return list.isEmpty();
	 }

}
