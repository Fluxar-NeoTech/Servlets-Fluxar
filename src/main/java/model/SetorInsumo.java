package model;


public class SetorInsumo {
    private int codSetor;
    private int codInsumo;

    public SetorInsumo() {}

    public SetorInsumo(int codSetor, int codInsumo) {
        this.codSetor = codSetor;
        this.codInsumo = codInsumo;
    }

    public int getCodSetor() { return codSetor; }
    public void setCodSetor(int codSetor) { this.codSetor = codSetor; }
    public int getCodInsumo() { return codInsumo; }
    public void setCodInsumo(int codInsumo) { this.codInsumo = codInsumo; }
}