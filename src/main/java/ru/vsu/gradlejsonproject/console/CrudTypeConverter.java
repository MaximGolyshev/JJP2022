package ru.vsu.gradlejsonproject.console;


import com.beust.jcommander.IStringConverter;

public class CrudTypeConverter implements IStringConverter<CrudType> {
    @Override
    public CrudType convert(String value) {
        return CrudType.valueOf(value);
    }
}
