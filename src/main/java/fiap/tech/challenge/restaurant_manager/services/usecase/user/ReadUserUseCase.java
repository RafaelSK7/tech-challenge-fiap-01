package fiap.tech.challenge.restaurant_manager.services.usecase.user;

import fiap.tech.challenge.restaurant_manager.entites.User;
import fiap.tech.challenge.restaurant_manager.entites.request.LoginRequest;
import fiap.tech.challenge.restaurant_manager.entites.response.AddressResponse;
import fiap.tech.challenge.restaurant_manager.entites.response.LoginResponse;
import fiap.tech.challenge.restaurant_manager.entites.response.UserResponse;
import fiap.tech.challenge.restaurant_manager.exceptions.custom.InvalidLogonException;
import fiap.tech.challenge.restaurant_manager.exceptions.custom.UserNotFoundException;
import fiap.tech.challenge.restaurant_manager.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class  ReadUserUseCase {

	private UserRepository userRepository;

	public ReadUserUseCase(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public Page<UserResponse> findAll(Pageable page) {
		Page<User> userPages = userRepository.findAll(page);

		List<User> userList = userPages.getContent();

		List<UserResponse> responseList = userList.stream().map(this::toResponse).collect(Collectors.toList());

		Page<UserResponse> responsePages = new PageImpl<UserResponse>(responseList, page, userPages.getTotalElements());

		return responsePages;
	}

	public UserResponse findById(Long id) {
		return toResponse(userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id)));
	}

	public User findByIdEntity(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
	}
	
	public LoginResponse findByLoginAndPassword(LoginRequest loginRequest) {
		return toLoginResponse(userRepository.findByLoginAndPassword(loginRequest.login(), loginRequest.password()).orElseThrow(() -> new InvalidLogonException()));
	}

	private LoginResponse toLoginResponse(User user) {
		return new LoginResponse(user.getId(), user.getName(), user.getLogin(), user.getUserType());
	}

	private UserResponse toResponse(User user) {
		AddressResponse addressResponse = null;

		if (user.getAddress() != null) {
			addressResponse = new AddressResponse(user.getAddress().getStreet(), user.getAddress().getNumber(),
					user.getAddress().getNeighborhood(), user.getAddress().getCity(), user.getAddress().getState(),
					user.getAddress().getZipCode(), user.getAddress().getCountry());
		}

		return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getLogin(),
				user.getUserType().name(), addressResponse, user.getRestaurants());
	}

}
