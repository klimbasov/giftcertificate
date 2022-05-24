package com.epam.esm.service.util.validator;

import com.epam.esm.service.dto.CertificateDto;
import com.epam.esm.service.dto.TagDto;

import java.util.Arrays;
import java.util.Objects;

import static java.util.Objects.isNull;

public class ArgumentValidator {
    private static final int MIN_NAME_LENGTH = 3;
    private static final int MAX_NAME_LENGTH = 25;
    private static final int MIN_DESCRIPTION_LENGTH = 3;
    private static final int MAX_DESCRIPTION_LENGTH = 50;
    private static final int MIN_DURATION = 7;
    private static final int MAX_DURATION = 256;
    private static final float MIN_PRICE = 0.03f;
    private static final float MAX_PRICE = 10000f;
    private static final int MIN_ID = 1;

    public static void validateCreate(CertificateDto certificateDto){
        throwNullable(certificateDto);
        throwFieldInconsistencyCreate(certificateDto);
    }
    public static void validateCreate(TagDto tagDto){
        throwNullable(tagDto);
        throwFieldInconsistencyCreate(tagDto);
    }


    public static void validateUpdate(CertificateDto certificateDto){
        throwNullable(certificateDto);
        throwFieldInconsistencyUpdate(certificateDto);
    }

    public static void validateUpdate(TagDto tagDto){
        throwNullable(tagDto);
        throwFieldInconsistencyUpdate(tagDto);
    }

    public static void validateRead(Integer id){
        throwNullable(id);
    }

    public static void validateDelete(Integer id){
        throwNullable(id);
    }

    private static void throwFieldInconsistencyCreate(CertificateDto certificateDto){
        if(hasNullFieldRequiredCreate(certificateDto)){
            throw new IllegalArgumentException("some required params were null.");
        }
    }

    private static void throwFieldInconsistencyCreate(TagDto tagDto){
        if(hasNullFieldRequiredCreate(tagDto)){
            throw new IllegalArgumentException("some required params were null.");
        }
    }

    private static void throwNullableRequiredFieldCreate(CertificateDto certificateDto){
        if(hasNullFieldRequiredCreate(certificateDto)){
            throw new IllegalArgumentException("some required params were null.");
        }
    }

    private static void throwNullableRequiredFieldCreate(TagDto tagDto){
        if(hasNullFieldRequiredCreate(tagDto)){
            throw new IllegalArgumentException("some required params were null.");
        }
    }

    private static void throwFieldInconsistencyUpdate(CertificateDto certificateDto){
        if(hasNullFieldRequiredUpdate(certificateDto)){
            throw new IllegalArgumentException("some required params were null.");
        }
    }

    private static void throwFieldInconsistencyUpdate(TagDto tagDto){
        if(hasNullFieldRequiredUpdate(tagDto)){
            throw new IllegalArgumentException("some required params were null.");
        }
    }

    private static void throwNullableRequiredFieldUpdate(CertificateDto certificateDto){
        if(hasNullFieldRequiredUpdate(certificateDto)){
            throw new IllegalArgumentException("some required params were null.");
        }
    }

    private static void throwNullableRequiredFieldUpdate(TagDto tagDto){
        if(hasNullFieldRequiredUpdate(tagDto)){
            throw new IllegalArgumentException("some required params were null.");
        }
    }

    private static boolean hasNullFieldRequiredCreate(CertificateDto certificateDto){
        return Arrays.stream(new Object[]{certificateDto.getName(),
                certificateDto.getDescription(),
                certificateDto.getPrice(),
                certificateDto.getDuration(),
                certificateDto.getCreateDate(),
                certificateDto.getLastUpdateDate()}).anyMatch(Objects::isNull);
    }

    private static boolean hasNullFieldRequiredCreate(TagDto tagDto){
        return isNull(tagDto.getName());
    }

    private static boolean hasNullFieldRequiredUpdate(CertificateDto certificateDto){
        return isNull(certificateDto.getId()) || Arrays.stream(new Object[]{certificateDto.getName(),
                certificateDto.getDescription(),
                certificateDto.getPrice(),
                certificateDto.getDuration(),
                certificateDto.getCreateDate(),
                certificateDto.getLastUpdateDate()}).allMatch(Objects::isNull);
    }

    private static boolean hasNullFieldRequiredUpdate(TagDto tagDto){
        return isNull(tagDto.getId()) || isNull(tagDto.getName());
    }

    private static boolean hasFieldInconsistencyCreate(CertificateDto certificateDto){
        return certificateDto.getName().length() > MAX_NAME_LENGTH
                || certificateDto.getName().length() < MIN_NAME_LENGTH
                || certificateDto.getDuration() > MAX_DURATION
                || certificateDto.getDuration() < MIN_DURATION
                || certificateDto.getDescription().length() > MAX_DESCRIPTION_LENGTH
                || certificateDto.getDescription().length() < MIN_DESCRIPTION_LENGTH;
    }

    private static boolean hasFieldInconsistencyCreate(TagDto tagDto){
        return tagDto.getName().length() > MAX_NAME_LENGTH;
    }

    private static boolean hasFieldInconsistencyUpdate(CertificateDto certificateDto){
        return certificateDto.getId() > MIN_ID;
    }

    private static boolean hasFieldInconsistencyUpdate(TagDto tagDto){
        return tagDto.getId() > MIN_ID;
    }

    private static void throwNullable(Object o){
        if(isNull(o)){
            throw new IllegalArgumentException("null passed to the parameters");
        }
    }
}
