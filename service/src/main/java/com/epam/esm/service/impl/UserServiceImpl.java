package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dao.entity.User;
import com.epam.esm.service.UserService;
import com.epam.esm.service.dto.SearchOptions;
import com.epam.esm.service.dto.UserDto;
import com.epam.esm.service.exception.ext.NoSuchObjectException;
import com.epam.esm.service.util.mapper.UserDtoEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.epam.esm.service.constant.ExceptionMessages.NO_SUCH_OBJECT;
import static com.epam.esm.service.util.validator.ArgumentValidator.SearchOptionsValidator.validateRead;
import static com.epam.esm.service.util.validator.ArgumentValidator.validateRead;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final UserDtoEntityMapper mapper;

    @Autowired
    public UserServiceImpl(UserDao userDao, UserDtoEntityMapper mapper) {
        this.userDao = userDao;
        this.mapper = mapper;
    }

    @Override
    public PagedModel<UserDto> read(SearchOptions options) {
        validateRead(options);
        int offset = options.getPageSize() * (options.getPageNumber() - 1);
        String name = options.getSubname();
        List<User> users = userDao.read(offset, options.getPageSize(), name);
        long totalElements = userDao.count(name);
        return PagedModel.of(mapper.mapToDto(users), new PagedModel.PageMetadata(options.getPageSize(), options.getPageNumber(), totalElements));
    }

    @Override
    public UserDto read(long id) {
        validateRead(id);
        User user = userDao.read(id).orElseThrow(() -> new NoSuchObjectException(NO_SUCH_OBJECT));
        return mapper.mapToDto(user);
    }
}
