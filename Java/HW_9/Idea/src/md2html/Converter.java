package md2html;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Converter {
    private int current;
    private int prev;
    private boolean isCloseTag;

    private StringBuilder currentLine = new StringBuilder();

    private Deque<Character> marks = new LinkedList<>();

    private final Set<Character> SINGLEMARK = Set.of('`', '*', '_');
    private final Map<Character, String> TOKENS = Map.of(
            '*', "em",
            '_', "em",
            '`', "code", '-', "s", '+', "u");
    private final Map<Character, String> PAIRS = Map.of('*', "strong", '_', "strong");
    private final Map<Character, String> SPECIALSYMBOLS = Map.of('<', "&lt;", '>', "&gt;", '&', "&amp;");

    public void convert(String inputText, String outputText) {
        File input = new File(inputText);
        File output = new File(outputText);

        List<String> page = new ArrayList<>();
        try {
            ScannerChar in = new ScannerChar(input);
            try {
                int endLines = 0;
                current = in.read();
                while (current != -1) {
                    if (skipEmptyLines(in)) {
                        break;
                    }
                    String tag = findAndIdentifyBlock(in);
                    while (current != -1) {
                        if ((char) current == '\r') {
                            toRead(in);
                            continue;
                        }
                        endLines = current == '\n' ? endLines + 1 : 0;
                        if (endLines == 2) {
                            endLines = 0;
                            break;
                        }
                        if (current == '\\') {
                            toRead(in);
                            if (!TOKENS.containsKey((char) current)) {
                                currentLine.append('\\');
                            }
                            if (current == -1) {
                                break;
                            }
                            currentLine.append((char) current);
                        } else if (TOKENS.containsKey((char) current)) {
                            int temp = prev;
                            toRead(in);
                            if (current == -1 && Character.isWhitespace(temp)) {
                                currentLine.append((char) prev);
                                break;
                            }
                            if (Character.isWhitespace(current) && (Character.isWhitespace(temp) || currentLine.length() == 0)) {
                                currentLine.append((char) prev).append((char) current);
                                toRead(in);
                                continue;
                            }
                            isCloseTag = marks.size() != 0 && marks.peek() == prev;
                            int currentMark = current == prev ? current :
                                    SINGLEMARK.contains((char) prev) ? prev : -1;
                            if (currentMark != -1) {
                                writeHtmlTag((char) currentMark);
                            } else {
                                currentLine.append((char) prev);
                            }
                            if (current != prev) {
                                continue;
                            }
                        } else {
                            currentLine.append(SPECIALSYMBOLS.getOrDefault((char) current, String.valueOf((char) current)));
                        }
                        toRead(in);
                    }
                    if (currentLine.length() > 0 && currentLine.charAt(currentLine.length() - 1) == '\n') {
                        currentLine.deleteCharAt(currentLine.length() - 1);
                    }
                    String blockTag = tag + ">";
                    page.add("<" + blockTag + currentLine + "</" + blockTag + '\n');
                    currentLine.delete(0, currentLine.length());
                    marks.clear();
                }
            } finally {
                in.close();
            }
        } catch (IOException e) {
            System.out.println("Problem with input file: " + e.getMessage());
            return;
        }

        try {
            Writer out = new Writer(output);
            try {
                for (String block : page) {
                    out.write(block);
                }
            } finally {
                out.close();
            }
        } catch (IOException e) {
            System.out.println("Problem with output file: " + e.getMessage());
        }
    }

    private void writeHtmlTag(char mark) {
        if (isCloseTag) {
            marks.pop();
        } else {
            marks.push(mark);
        }
        currentLine.append(isCloseTag ? "</" : "<").append(current == prev && PAIRS.containsKey(mark) ? PAIRS.get(mark) : TOKENS.get(mark)).append(">");
    }

    private String findAndIdentifyBlock(ScannerChar in) throws  IOException {
        if (current == '#') {
            String temp = in.readSame('#');
            if (Character.isWhitespace(temp.charAt(temp.length() - 1))) {
                toRead(in);
                return "h" + temp.length();
            } else {
                current = temp.charAt(temp.length() - 1);
                currentLine.append("#").append(temp, 0, temp.length() - 1);
                return "p";
            }
        } else {
            return "p";
        }
    }

    private boolean skipEmptyLines(ScannerChar in) throws IOException {
        while (current == '\n' || current == '\r') {
            current = in.read();
        }
        return current == -1;
    }

    private void toRead(ScannerChar in) throws IOException {
        prev = current;
        current = in.read();
    }
}
