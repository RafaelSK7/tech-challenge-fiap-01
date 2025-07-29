package fiap.tech.challenge.restaurant_manager.validations.impl.adress;

import fiap.tech.challenge.restaurant_manager.DTOs.request.restaurants.CreateRestaurantRequest;
import fiap.tech.challenge.restaurant_manager.validations.ValidateRestaurantService;
import org.springframework.stereotype.Service;

import fiap.tech.challenge.restaurant_manager.DTOs.request.address.CreateAddressRequest;
import fiap.tech.challenge.restaurant_manager.DTOs.request.users.CreateUserRequest;
import fiap.tech.challenge.restaurant_manager.exceptions.custom.InvalidAddressException;
import fiap.tech.challenge.restaurant_manager.validations.ValidateUserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AddressValidateUser implements ValidateUserService, ValidateRestaurantService {

	@Override
	public void validate(CreateUserRequest request) {
		validateAddressFields(request.address());
	}

	@Override
	public void validate(CreateRestaurantRequest request) {
		validateAddressFields(request.address());
	}

	public void validateAddressFields(CreateAddressRequest addressRequest) {
		log.info("Validando campos do endereço");
		validateNullAddress(addressRequest);
		validateStreetAndNumber(addressRequest);
		validateNeighborhood(addressRequest);
		validateCity(addressRequest);
		validateState(addressRequest);
		validateCountry(addressRequest);
		validateZipCode(addressRequest);
	}

	private void validateNullAddress(CreateAddressRequest addressRequest) {
		log.info("Validando endereço nulo");
		if (addressRequest == null)
			throw new InvalidAddressException("Endereco invalido");

	}

	private void validateStreetAndNumber(CreateAddressRequest addressRequest) {
		log.info("Validando logradouro");
		if ((addressRequest.street() == null) || (addressRequest.street().isEmpty())
				|| (addressRequest.street().isBlank()) || (addressRequest.number() == null)
				|| (addressRequest.number().isEmpty()) || (addressRequest.number().isBlank()))
			throw new InvalidAddressException("Logradouro ou numero invalidos");

	}

	private void validateNeighborhood(CreateAddressRequest addressRequest) {
		log.info("Validando bairro");
		if ((addressRequest.neighborhood() == null) || (addressRequest.neighborhood().isEmpty())
				|| (addressRequest.neighborhood().isBlank()))
			throw new InvalidAddressException("Bairro invalido");

	}

	private void validateCity(CreateAddressRequest addressRequest) {
		log.info("Validando cidade");
		if ((addressRequest.city() == null) || (addressRequest.city().isEmpty()) || (addressRequest.city().isBlank()))
			throw new InvalidAddressException("Cidade invalida");

	}

	private void validateState(CreateAddressRequest addressRequest) {
		log.info("Validando estado");
		if ((addressRequest.state() == null) || (addressRequest.state().isEmpty())
				|| (addressRequest.state().isBlank()))
			throw new InvalidAddressException("Estado invalido");

	}

	private void validateCountry(CreateAddressRequest addressRequest) {
		log.info("Validando pais");
		if ((addressRequest.country() == null) || (addressRequest.country().isEmpty())
				|| (addressRequest.country().isBlank()))
			throw new InvalidAddressException("Pais invalido");

	}

	private void validateZipCode(CreateAddressRequest addressRequest) {
		log.info("Validando cep");
		if ((addressRequest.zipCode() == null) || (addressRequest.zipCode().isEmpty())
				|| (addressRequest.zipCode().isBlank()))
			throw new InvalidAddressException("CEP invalido");

	}

}
