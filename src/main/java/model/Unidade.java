package model;


public class Unidade {
    private int codigo;
    private String pais;
    private String estado;
    private String municipio;
    private String rua;
    private String numero;
    private String comentarios;
    private int codEmpresa;

    public Unidade() {}

    public Unidade(int codigo, String pais, String estado, String municipio, String rua, String numero, String comentarios, int codEmpresa) {
        this.codigo = codigo;
        this.pais = pais;
        this.estado = estado;
        this.municipio = municipio;
        this.rua = rua;
        this.numero = numero;
        this.comentarios = comentarios;
        this.codEmpresa = codEmpresa;
    }

    public int getCodigo() { return codigo; }
    public void setCodigo(int codigo) { this.codigo = codigo; }
    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getMunicipio() { return municipio; }
    public void setMunicipio(String municipio) { this.municipio = municipio; }
    public String getRua() { return rua; }
    public void setRua(String rua) { this.rua = rua; }
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
    public String getComentarios() { return comentarios; }
    public void setComentarios(String comentarios) { this.comentarios = comentarios; }
    public int getCodEmpresa() { return codEmpresa; }
    public void setCodEmpresa(int codEmpresa) { this.codEmpresa = codEmpresa; }
}