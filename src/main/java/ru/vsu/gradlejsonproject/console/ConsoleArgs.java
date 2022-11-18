package ru.vsu.gradlejsonproject.console;

import com.beust.jcommander.Parameter;
import lombok.Getter;

@Getter
public class ConsoleArgs {
    @Parameter(names = {"--crud-type", "-ct"}, description = "Crud type of operation", required = true, converter = CrudTypeConverter.class)
    private CrudType crudType;

    @Parameter(names = {"--id", "-i"}, description = "Id of player")
    private Long id;

    @Parameter(names = {"--input-file", "-if"}, description = "Path to input file")
    private String inputFile;

    @Parameter(names = {"--output-file", "-of"}, description = "Path to output file")
    private String outputFile;

    @Parameter(names = {"--need-exit", "-e"}, arity = 1)
    private Boolean needToExit = false;

    @Parameter(names = "--help", help = true)
    private Boolean help = true;
}
