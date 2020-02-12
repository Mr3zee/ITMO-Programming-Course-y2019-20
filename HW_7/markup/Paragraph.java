package markup;

import java.util.*;

public class Paragraph implements Mark {
	List<Mark> list;

	public Paragraph(List<Mark> list) {
		this.list = list;
	}

	@Override
	public void toMarkdown(StringBuilder wrapper) {
		for (Mark mark : list) {
			mark.toMarkdown(wrapper);
		}
	}

	@Override
	public void toHtml(StringBuilder wrapper) {
		for (Mark mark : list) {
			mark.toHtml(wrapper);
		}
	}
}