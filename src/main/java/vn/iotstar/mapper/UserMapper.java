package vn.iotstar.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import vn.iotstar.dto.UserDTO;
import vn.iotstar.entity.User;

/** MapStruct mapper theo dung yeu cau cua VD2. */
@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "roleName", source = "role.name")
    UserDTO toDTO(User user);
}
