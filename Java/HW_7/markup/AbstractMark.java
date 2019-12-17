package markup;

import java.util.*;

public abstract class AbstractMark implements Mark {
	protected List<Mark> list;

	protected AbstractMark(List<Mark> list) {
		this.list = list;
	}

	@Override
	public void toMarkdown(StringBuilder wrapper) {
		wrapper.append(getMarkdownTag());
		for (Mark mark : list) {
			mark.toMarkdown(wrapper);
		}
		wrapper.append(getMarkdownTag());
	}

	@Override
	public void toHtml(StringBuilder wrapper) {
		wrapper.append("<").append(getHtmlTag()).append(">");
		for (Mark mark : list) {
			mark.toHtml(wrapper);
		}
		wrapper.append("</").append(getHtmlTag()).append(">");
	}

	protected abstract void getHtmlTag(StringBuilder wrapper);

	protected abstract void getMarkdownTag(StringBuilder wrapper);
	
}