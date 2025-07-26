package fiap.tech.challenge.restaurant_manager.services.usecase.userType;

import fiap.tech.challenge.restaurant_manager.entites.UserType;
import fiap.tech.challenge.restaurant_manager.entites.response.UserTypeResponse;
import fiap.tech.challenge.restaurant_manager.exceptions.custom.UserTypeNotFoundException;
import fiap.tech.challenge.restaurant_manager.repositories.UserTypeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReadUserTypeUseCase {

    private UserTypeRepository userTypeRepository;

    public ReadUserTypeUseCase(UserTypeRepository userTypeRepository) {

        this.userTypeRepository = userTypeRepository;
    }

    public Page<UserTypeResponse> findAll(Pageable page) {

        Page<UserType> userPages = userTypeRepository.findAll(page);

        List<UserType> userList = userPages.getContent();

        List<UserTypeResponse> responseList = userList.stream().map(this::toResponse).collect(Collectors.toList());

        return new PageImpl<>(responseList, page, userPages.getTotalElements());

    }


    public UserTypeResponse findById(Long id) {

        return toResponse(userTypeRepository.findById(id).orElseThrow(() -> new UserTypeNotFoundException(id)));
    }

    public UserType findUserTypeById(Long id){

        return userTypeRepository.findByUserTypeId(id)
                .orElseThrow(() -> new UserTypeNotFoundException(id));
    }

    public Optional<UserType> findDuplicateUserType(String userTypeName) {
        return userTypeRepository.findByUserTypeName(userTypeName.trim().toUpperCase());
    }

    private UserTypeResponse toResponse(UserType userType) {

        return new UserTypeResponse(userType.getUserTypeId(), userType.getUserTypeName());
    }
}
