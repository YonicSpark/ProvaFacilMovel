package exemplo.restcliente.beans;

/**
 * Created by DANILLO on 27/06/2017.
 */

public class Alternativa {
    private int altCodigo;
    private String altText;
    private int queCodigo;

    public int getAltCodigo() {
        return altCodigo;
    }

    public void setAltCodigo(int altCodigo) {
        this.altCodigo = altCodigo;
    }

    public String getAltText() {
        return altText;
    }

    public void setAltText(String altText) {
        this.altText = altText;
    }

    public int getQueCodigo() {
        return queCodigo;
    }

    public void setQueCodigo(int queCodigo) {
        this.queCodigo = queCodigo;
    }

    public boolean isAltCorreta() {
        return altCorreta;
    }

    public void setAltCorreta(boolean altCorreta) {
        this.altCorreta = altCorreta;
    }

    private boolean altCorreta;
}
