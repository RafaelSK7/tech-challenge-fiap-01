package fiap.tech.challenge.restaurant_manager.utils;

import fiap.tech.challenge.restaurant_manager.application.DTOs.request.userTypes.CreateUserTypeRequest;
import fiap.tech.challenge.restaurant_manager.application.DTOs.response.userTypes.UserTypeResponse;
import fiap.tech.challenge.restaurant_manager.infrastructure.persistence.entites.UserTypesEntity;

public class UserTypeUtils {

    public static UserTypesEntity getValidUserType() {
        UserTypesEntity userType = new UserTypesEntity();
        userType.setUserTypeId(1L);
        userType.setUserTypeName("ADMIN");
        return userType;
    }

    public static UserTypesEntity getClientUserType() {
        UserTypesEntity userType = new UserTypesEntity();
        userType.setUserTypeId(2L);
        userType.setUserTypeName("CLIENT");
        return userType;
    }

    public static CreateUserTypeRequest getValidCreateUserTypeRequest() {
        return new CreateUserTypeRequest("ADMIN");
    }

    public static UserTypeResponse getValidUserTypeResponse() {
        UserTypesEntity userType = getValidUserType();
        return new UserTypeResponse(
                userType.getUserTypeId(),
                userType.getUserTypeName());
    }
}