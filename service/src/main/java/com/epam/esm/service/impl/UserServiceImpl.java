package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dao.entity.User;
import com.epam.esm.service.UserService;
import com.epam.esm.service.dto.SearchOptions;
import com.epam.esm.service.dto.UserDto;
import com.epam.esm.service.exception.ext.NoSuchObjectException;
import com.epam.esm.service.util.mapper.impl.UserDetailEntityMapper;
import com.epam.esm.service.util.mapper.impl.UserDtoEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.epam.esm.service.constant.ExceptionMessages.NO_SUCH_OBJECT;
import static com.epam.esm.service.util.pagination.Pager.toPage;
import static com.epam.esm.service.util.pagination.validator.PageValidator.validate;
import static com.epam.esm.service.util.validator.ArgumentValidator.SearchOptionsValidator.validateRead;
import static com.epam.esm.service.util.validator.ArgumentValidator.validateRead;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserDao dao;
    private final UserDtoEntityMapper mapper;

    private final UserDetailEntityMapper detailEntityMapper;
    @Override
    public PagedModel<UserDto> read(SearchOptions options) {
        validateRead(options);

        int pageSize = options.getPageSize();
        int pageNumber = options.getPageNumber();
        int offset = pageSize * (options.getPageNumber() - 1);
        String name = options.getSubname();

        long totalElements = dao.count(name);
        validate(totalElements, pageSize, pageNumber);
        List<User> entities = dao.read(offset, options.getPageSize(), name);
        return toPage(mapper.mapToModels(entities), pageNumber, pageSize, totalElements);
    }

    @Override
    public UserDto read(long id) {
        validateRead(id);
        User user = dao.read(id).orElseThrow(() -> new NoSuchObjectException(NO_SUCH_OBJECT));
        return mapper.mapToModel(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = dao.readByStrictName(username);
        return user.map(detailEntityMapper::mapToModel).orElse(null);
    }
}
