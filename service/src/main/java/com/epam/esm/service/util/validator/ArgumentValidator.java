package com.epam.esm.service.util.validator;

import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.dto.SearchOptions;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.exception.ext.IllegalArgumentException;
import org.springframework.lang.NonNull;

import java.util.Arrays;
import java.util.Objects;

import static com.epam.esm.service.util.validator.Messages.ILLEGAL_ARGUMENT;
import static java.util.Objects.isNull;

public class ArgumentValidator {
    private static final int MIN_ID = 1;

    private ArgumentValidator(){}

    public static void validateRead(Integer id) {
        validateId(id);
    }

    public static void validateDelete(Integer id) {
        validateId(id);
    }

    private static void validateId(Integer id) {
        throwIfNull(id);
        throwInconsistentId(id);
    }

    private static void throwInconsistentId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException();
        }
    }

    private static void throwIfNull(Object o) {
        if (isNull(o)) {
            throw new IllegalArgumentException("null passed to the parameters");
        }
    }

    public static class CertificateDtoValidator {
        private static final int MIN_NAME_LENGTH = 3;
        private static final int MAX_NAME_LENGTH = 25;
        private static final int MIN_DESCRIPTION_LENGTH = 3;
        private static final int MAX_DESCRIPTION_LENGTH = 50;
        private static final int MIN_DURATION = 7;
        private static final int MAX_DURATION = 256;
        private static final float MIN_PRICE = 0.03f;
        private static final float MAX_PRICE = 10000f;

        private CertificateDtoValidator(){}

        public static void validateCreate(CertificateDto certificateDto) {
            throwIfNull(certificateDto);
            throwFieldInconsistencyCreate(certificateDto);
        }

        public static void validateUpdatePreMap(CertificateDto certificateDto) {
            throwIfNull(certificateDto);
            throwFieldInconsistencyUpdate(certificateDto);
        }

        private static void throwFieldInconsistencyCreate(CertificateDto certificateDto) {
            if (hasFieldInconsistencyCreate(certificateDto)) {
                throw new IllegalArgumentException(ILLEGAL_ARGUMENT);
            }
        }

        private static void throwFieldInconsistencyUpdate(@NonNull CertificateDto certificateDto) {
            if (hasNullFieldRequiredUpdate(certificateDto)
                    || hasFieldInconsistencyUpdate(certificateDto)) {
                throw new IllegalArgumentException(ILLEGAL_ARGUMENT);
            }
        }

        private static boolean hasNullFieldRequiredRead(@NonNull CertificateDto certificateDto) {
            return Arrays.stream(new Object[]{certificateDto.getName(),
                    certificateDto.getDescription(),
                    certificateDto.getPrice(),
                    certificateDto.getDuration()}).anyMatch(Objects::isNull);
        }

        private static boolean hasFieldInconsistencyUpdate(@NonNull CertificateDto certificateDto) {
            return certificateDto.getId() < MIN_ID;
        }

        private static boolean hasNullFieldRequiredUpdate(@NonNull CertificateDto certificateDto) {
            return isNull(certificateDto.getId()) || Arrays.stream(new Object[]{certificateDto.getName(),
                    certificateDto.getDescription(),
                    certificateDto.getPrice(),
                    certificateDto.getDuration(),
                    certificateDto.getCreateDate(),
                    certificateDto.getLastUpdateDate()}).allMatch(Objects::isNull);
        }

        private static boolean hasFieldInconsistencyCreate(@NonNull CertificateDto certificateDto) {
            return hasNullFieldRequiredRead(certificateDto)
                    || certificateDto.getName().length() > MAX_NAME_LENGTH
                    || certificateDto.getName().length() < MIN_NAME_LENGTH
                    || certificateDto.getDuration() > MAX_DURATION
                    || certificateDto.getDuration() < MIN_DURATION
                    || certificateDto.getDescription().length() > MAX_DESCRIPTION_LENGTH
                    || certificateDto.getDescription().length() < MIN_DESCRIPTION_LENGTH
                    || certificateDto.getPrice() < MIN_PRICE
                    || certificateDto.getPrice() > MAX_PRICE;
        }
    }

    public static class TagDtoValidator {
        private static final int MIN_NAME_LENGTH = 3;
        private static final int MAX_NAME_LENGTH = 25;

        private TagDtoValidator(){}

        public static void validateCreate(TagDto tagDto) {
            throwIfNull(tagDto);
            throwFieldInconsistencyCreate(tagDto);
        }

        private static void throwFieldInconsistencyCreate(TagDto tagDto) {
            if (hasFieldInconsistencyCreate(tagDto)) {
                throw new IllegalArgumentException(ILLEGAL_ARGUMENT);
            }
        }

        private static boolean hasNullFieldRequiredRead(@NonNull TagDto tagDto) {
            return isNull(tagDto.getName());
        }

        private static boolean hasFieldInconsistencyCreate(@NonNull TagDto tagDto) {
            return hasNullFieldRequiredRead(tagDto)
                    || tagDto.getName().length() > MAX_NAME_LENGTH
                    || tagDto.getName().length() < MIN_NAME_LENGTH;
        }
    }

    public static class SearchOptionsValidator {

        private SearchOptionsValidator(){}

        public static void validateRead(SearchOptions searchOptions) {
            throwIfNull(searchOptions);
            throwFieldInconsistencyRead(searchOptions);
        }


        private static void throwFieldInconsistencyRead(@NonNull SearchOptions searchOptions) {
            if (hasNullFieldRequiredRead(searchOptions)) {
                throw new IllegalArgumentException(ILLEGAL_ARGUMENT);
            }
        }

        private static boolean hasNullFieldRequiredRead(@NonNull SearchOptions searchOptions) {
            return isNull(searchOptions.getSorting())
                    || isNull(searchOptions.getSubdescription())
                    || isNull(searchOptions.getSubname());
        }
    }


}

class Messages{

    private Messages(){}

    static final String ILLEGAL_ARGUMENT = "some required params were null.";
}
