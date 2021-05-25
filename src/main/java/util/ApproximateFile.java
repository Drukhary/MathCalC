package util;

import lombok.Getter;

import java.io.File;

enum ApproximateFile {
    LINEAR("linear"),
    EXPONENTIAL("exponential"),
    LOGARITHMIC("logarithmic"),
    QUADRATIC("quadratic"),
    POWERFUL("powerful");
    private final static String pathRoot = "src/main/resources/";
    @Getter
    private final String path;
    @Getter
    private final File file;

    private ApproximateFile(String path){
        this.path = pathRoot+path;
        this.file = new File(this.path);
    }
}
