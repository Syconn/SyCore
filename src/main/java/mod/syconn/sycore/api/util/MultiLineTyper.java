package mod.syconn.sycore.api.util;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;

import java.util.ArrayList;
import java.util.List;

import static mod.syconn.sycore.api.util.ColorFormattedLine.letterPixels;

public class MultiLineTyper {

    private List<ColorFormattedString> lines = new ArrayList<>();
    private List<String> inputLines = new ArrayList<>();
    private final int lineLng;
    private final int lineTop;
    private final int lineBottom;

    public MultiLineTyper(int lineLng, int t, int b) {
        this.lineLng = lineLng;
        this.lineTop = t;
        this.lineBottom = b;
    }

    public void render(PoseStack pStack, Font font, int x, int y, int topLine){
        int window = lineBottom - lineTop;
        int totLines = window / letterPixels;
        for (int i = topLine; i < topLine + totLines; i++) {
            if (size() > i) {
                ColorFormattedLine.renderLine(lines.get(i).getString(), i, pStack, font, x, y, topLine);
            }
        }
    }

    public void addLine(ColorFormattedString msg){
        lines.addAll(addParagraph(msg));
    }

    public void addLine(ColoredString msg){
        lines.addAll(addParagraph(new ColorFormattedString(msg)));
    }

    public void addLine(String msg){
        lines.addAll(addParagraph(new ColorFormattedString(new ColoredString(msg))));
        inputLines.add(msg);
    }

    public int size(){
        return lines.size();
    }

    public List<String> getInputLines() {
        return inputLines;
    }

    public List<ColorFormattedString> getLines(){
        return lines;
    }

    public void setLines(List<ColorFormattedString> l){
        lines = l;
    }

    private List<ColorFormattedString> addParagraph(ColorFormattedString msg){
        List<ColorFormattedString> lines = new ArrayList<>();
        int l = 0;
        ColorFormattedString string = new ColorFormattedString();
        for (ColoredString str : msg.getString()){
            l += str.getString().length();
            if (l < lineLng) {
                string.addString(str);
                l++;
            } else {
                lines.add(string);
                string = new ColorFormattedString();
                string.addString(str);
                l = 0;
            }
        }
        if (!string.getString().isEmpty()) lines.add(string);
        return lines;
    }
}
