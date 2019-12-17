package markup;

public interface Mark {
	void toMarkdown(StringBuilder wrapper);
	void toHtml(StringBuilder wrapper);
}