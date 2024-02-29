import java.io.Serializable;

public class Utilizador implements Serializable{
    private String codUtilizador;
    private String email;
    private String nome;
    private String morada;
    private int numFiscal;
    private Transportadora transportadora;

    private GestorArtigos historicoCompras; //Historico de artigos comprados
    private GestorArtigos historicoVendas; //Historico de artigos vendidos
    private GestorArtigos aVenda; //Artigos ainda à venda.

    private static int contador = 1;
    
    //Construtores
    public Utilizador(){
        this.codUtilizador="";
        this.email="";
        this.nome="";
        this.morada="";
        this.numFiscal=0;
        this.transportadora=new Transportadora();
        this.historicoCompras= new GestorArtigos();
        this.historicoVendas= new GestorArtigos();
        this.aVenda= new GestorArtigos();
    }

    public Utilizador(String nome,String morada,
    int numFiscal,GestorArtigos historicoCompras,GestorArtigos historicoVendas,
    GestorArtigos aVenda,Transportadora transportadora){
        this.codUtilizador="u"+contador++;
        this.email=nome+"@gmail.com";
        this.nome=nome;
        this.morada=morada;
        this.numFiscal=numFiscal;
        this.historicoCompras=historicoCompras.clone();
        this.historicoVendas=historicoVendas.clone();
        this.aVenda=aVenda.clone();
        this.transportadora=transportadora.clone();
    }

    public Utilizador(Utilizador umUtilizador){
        this.codUtilizador=umUtilizador.getCodUtilizador();
        this.email=umUtilizador.getEmail();
        this.nome=umUtilizador.getNome();
        this.morada=umUtilizador.getMorada();
        this.numFiscal=umUtilizador.getNumFiscal();
        this.historicoCompras=umUtilizador.getHistoricoCompras();
        this.historicoVendas=umUtilizador.getHistoricoVendas();
        this.aVenda=umUtilizador.getaVenda();
        this.transportadora=umUtilizador.getTransportadora();
    }

    //gets
    public String getCodUtilizador() {
        return this.codUtilizador;
    }

    public String getEmail() {
        return this.email;
    }

    public String getNome() {
        return this.nome;
    }

    public String getMorada() {
        return this.morada;
    }

    public int getNumFiscal() {
        return this.numFiscal;
    }

    public GestorArtigos getHistoricoCompras() {
        return this.historicoCompras.clone();
    }

    public GestorArtigos getHistoricoVendas() {
        return this.historicoVendas.clone();
    }

    public GestorArtigos getaVenda() {
        return this.aVenda.clone();
    }

    public Transportadora getTransportadora() {
        return this.transportadora.clone();
    }

    public static int getContador() {
        return contador;
    }

    //sets
    public void setCodUtilizador(String codUtilizador) {
        this.codUtilizador = codUtilizador;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public void setNumFiscal(int numFiscal) {
        this.numFiscal = numFiscal;
    }

    public void setHistoricoCompras(GestorArtigos historicoCompras) {
        this.historicoCompras=historicoCompras.clone();
    }

    public void setHistoricoVendas(GestorArtigos historicoVendas) {
        this.historicoVendas=historicoVendas.clone();
    }

    public void setaVenda(GestorArtigos aVenda) {
        this.historicoVendas=aVenda.clone();
    }

    public void setTransportadora(Transportadora transportadora) {
        this.transportadora = transportadora.clone();
    }

    public static void setContador(int contador) {
        Utilizador.contador = contador;
    }

    //clone
    public Utilizador clone(){
        return new Utilizador(this);
    } 

    //equals
    public boolean equals(Object o){
        if (this==o) 
            return true;
        if ((o==null) || (this.getClass() != o.getClass())) 
            return false;
        Utilizador utilizador = (Utilizador) o;
        return (utilizador.getCodUtilizador().equals(this.codUtilizador) && 
        utilizador.getEmail().equals(this.email) && 
        utilizador.getNome().equals(this.nome) &&
        utilizador.getMorada().equals(this.morada) &&
        utilizador.getNumFiscal()==this.numFiscal &&
        utilizador.getHistoricoCompras().equals(this.historicoCompras) &&
        utilizador.getHistoricoVendas().equals(this.historicoVendas) &&
        utilizador.getaVenda().equals(this.aVenda) &&
        utilizador.getTransportadora().equals(this.transportadora));
    }

    //toString
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("\n........Utilizador......\n");
        sb.append("Código de Utilizador: "+this.getCodUtilizador()+"\n");
        sb.append("Email: "+this.getEmail()+"\n");
        sb.append("Nome: "+this.getNome()+"\n");
        sb.append("Morada: "+this.getMorada()+"\n");
        sb.append("Número Fiscal "+this.getNumFiscal()+"\n");
        sb.append("\nHistórico Compras: "+this.getHistoricoCompras().toString()+"\n");
        sb.append("Histórico Vendas: "+this.getHistoricoVendas().toString()+"\n");
        sb.append("À Venda: "+this.getaVenda().toString()+"\n");
        sb.append("Transportadora: "+this.getTransportadora().getNome()+"\n");

        return sb.toString();
    }

    //Publicar artigos à venda
    public void artigo_aVenda(Artigo artigo) throws AddException{
        this.aVenda.addArtigo(artigo);
    }

    //Artigo vendido por um Vendedor
    public void artigo_Vendido(Artigo artigo) throws AddException, RemoveException{
        this.aVenda.removeArtigo(artigo.getCod_barras());
        this.historicoVendas.addArtigo(artigo);
    }

    //Artigo comprado por um Comprador
    public void artigo_Comprado(Artigo artigo) throws AddException{
        this.historicoCompras.addArtigo(artigo);
    }

    public String artigos_AVenda(){
        return aVenda.toString_withoutSize();
    }
}