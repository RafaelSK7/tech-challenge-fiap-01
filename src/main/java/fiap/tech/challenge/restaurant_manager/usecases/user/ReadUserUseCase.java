package fiap.tech.challenge.restaurant_manager.usecases.user;

import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import fiap.tech.challenge.restaurant_manager.entites.User;
import fiap.tech.challenge.restaurant_manager.DTOs.request.login.LoginRequest;
import fiap.tech.challenge.restaurant_manager.DTOs.response.address.AddressResponse;
import fiap.tech.challenge.restaurant_manager.DTOs.response.login.LoginResponse;
import fiap.tech.challenge.restaurant_manager.DTOs.response.users.UserResponse;
import fiap.tech.challenge.restaurant_manager.exceptions.custom.InvalidLogonException;
import fiap.tech.challenge.restaurant_manager.exceptions.custom.UserNotFoundException;
import fiap.tech.challenge.restaurant_manager.repositories.UserRepository;

@Service
@Slf4j
public class ReadUserUseCase {

	private UserRepository userRepository;

	public ReadUserUseCase(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public Page<UserResponse> findAll(Pageable page) {
		log.info("Entrou no use case de busca de usuarios.");
		log.info("Buscando usuarios.");
		Page<User> userPages = userRepository.findAll(page);
		log.info("Montando DTO da lista de usuarios.");
		List<UserResponse> responseList = userPages.getContent().stream().map(this::toResponse).collect(Collectors.toList());
		log.info("Retornando a lista.");
		return new PageImpl<>(responseList, page, userPages.getTotalElements());
	}

	public UserResponse findById(Long id) {
		log.info("Entrou no use case de busca de usuario");
		log.info("Buscando usuario informado.");
		return toResponse(userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id)));
	}

	public User findByIdEntity(Long id) {
		log.info("Entrou no use case de busca de entidade.");
		log.info("Buscando usuario informado.");
		return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
	}
	
	public LoginResponse findByLoginAndPassword(LoginRequest loginRequest) {
		log.info("Entrou no use case de busca de usuario por login e senha");
		log.info("Buscando usuario informado.");
		return toLoginResponse(userRepository.findByLoginAndPassword(loginRequest.login(), loginRequest.password()).orElseThrow(InvalidLogonException::new));
	}

	private LoginResponse toLoginResponse(User user) {
		log.info("Montando o DTO de retorno do login");
		return new LoginResponse(user.getId(), user.getName(), user.getLogin(), user.getUserType().getUserTypeId());
	}

	private UserResponse toResponse(User user) {
		log.info("Montando o DTO de retorno do usuario");
		AddressResponse addressResponse = null;

		if (user.getAddress() != null) {
			addressResponse = new AddressResponse(user.getAddress().getStreet(), user.getAddress().getNumber(),
					user.getAddress().getNeighborhood(), user.getAddress().getCity(), user.getAddress().getState(),
					user.getAddress().getZipCode(), user.getAddress().getCountry());
		}

		return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getLogin(),
				user.getUserType().getUserTypeId(), addressResponse, user.getRestaurants());
	}

}
