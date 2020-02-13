package md2html;

public class Md2Html {
    public static void main(String[] args) {
        Converter converter = new Converter();
        converter.convert(args[0], args[1]);
    }
}