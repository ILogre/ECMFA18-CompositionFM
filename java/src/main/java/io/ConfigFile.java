package io;

import criteria.CriteriaDescriptor;
import criteria.ScalarDescription;
import criteria.VectorialDescription;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ConfigFile {

    private File configFile;

    public ConfigFile(File configFile) { this.configFile = configFile; }

    public ConfigFile() {
        this(new File(ConfigFile.class.getClassLoader().getResource("features.txt").getFile()));
    }

    public Set<CriteriaDescriptor> criteria() throws IOException {
        Set<CriteriaDescriptor> configuration = new HashSet<>();
        return Files.lines(Paths.get(configFile.getPath()))
                .map(String::trim).filter(s -> !(s.equals("") || s.startsWith("#")))
                .map( this::extractCriteria )
                .collect(Collectors.toSet());
    }

    private CriteriaDescriptor extractCriteria(String line) {
        CriteriaDescriptor result;
        if (line.contains(":")) {
            String feature = line.split(":")[0];
            String[] candidates = line.split(":")[1].trim().split(" ");
            result = new VectorialDescription(feature, candidates);
        } else {
            result = new ScalarDescription(line);
        }
        return result;
    }
}