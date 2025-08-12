package fiap.tech.challenge.restaurant_manager.utils;

import fiap.tech.challenge.restaurant_manager.entites.UserType;
import fiap.tech.challenge.restaurant_manager.DTOs.request.userTypes.CreateUserTypeRequest;
import fiap.tech.challenge.restaurant_manager.DTOs.response.userTypes.UserTypeResponse;

public class UserTypeUtils {

    public static UserType getValidUserType() {
        UserType userType = new UserType();
        userType.setUserTypeId(1L);
        userType.setUserTypeName("ADMIN");
        return userType;
    }

    public static UserType getClientUserType() {
        UserType userType = new UserType();
        userType.setUserTypeId(2L);
        userType.setUserTypeName("CLIENT");
        return userType;
    }

    public static CreateUserTypeRequest getValidCreateUserTypeRequest() {
        return new CreateUserTypeRequest("ADMIN");
    }

    public static UserTypeResponse getValidUserTypeResponse() {
        UserType userType = getValidUserType();
        return new UserTypeResponse(
                userType.getUserTypeId(),
                userType.getUserTypeName());
    }
}