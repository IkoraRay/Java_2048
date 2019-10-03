import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;

public class DesenhosAuxiliares {  //enquadra numeros nos blocos

	private DesenhosAuxiliares(){
	}
	
	public static int recebeLarguraMensagem(String mensagem, Font fonte, Graphics2D graficos) {
		graficos.setFont(fonte);
		Rectangle2D limites = graficos.getFontMetrics().getStringBounds(mensagem, graficos);
		return (int)limites.getWidth();
	}
	
	public static int recebeAlturaMensagem(String mensagem, Font fonte, Graphics2D graficos) {
		graficos.setFont(fonte);
		if(mensagem.length() == 0) return 0;
		TextLayout lt = new TextLayout(mensagem, fonte, graficos.getFontRenderContext());
		return (int)lt.getBounds().getHeight();
	}
	
}
