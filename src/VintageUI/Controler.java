import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.DateTimeException;

public class Controlador_Menu_Vintage {
    private Vintage vintage;

    //Carregar Estado
    public static Vintage carregaEstado(String nomeFicheiro) throws FileNotFoundException,IOException,
    ClassNotFoundException{
        return Vintage.carregaEstado(nomeFicheiro);
    }

    //Guardar estado
    public void guardaEstado(String nomeFicheiro) throws FileNotFoundException,IOException{
        vintage.guardaEstado(nomeFicheiro);
    }

    //Construtores
    public Controlador_Menu_Vintage(Vintage vintage){
        this.vintage=vintage;  //Agregação
    }

    //gets
    public Vintage getVintage(){ //Agregação
        return this.vintage;
    }

    //sets
    public void setVintage(Vintage vintage) { //Agregação
        this.vintage = vintage;
    }

    //Outros métodos
    public String avancarTempo(String date) throws DateTimeException{ 
        return vintage.avancarTempo(date);
    }

    //Q1
    public String vendedorMaisFatorou(){
        return vintage.vendedorMaisFatorou();
    }

    //Q2
    public String TransportadoraMaiorVolumeFatoracao(){
        return vintage.TransportadoraMaiorVolumeFatoracao();
    }

    //Q3
    public String encomendasComprador(String codUtilizador){
        return vintage.encomendasComprador(codUtilizador);
    }

    //Q4
    public void topVendedoresCompradores(String dataInicial, String dataFinal, int top) throws DateTimeException{
        vintage.topVendedoresCompradores(dataInicial, dataFinal, top);
    }

    //Q5
    public double ganhosVintage(){
        return vintage.ganhosVintage();
    }

    //toString_Vintage
    public String toString_Vintage(){
        return vintage.toString();
    }

    //addUtilizador
    public void addUtilizador(Utilizador utilizador) throws AddException{
        vintage.addUtilizador(utilizador);
    }

    //removeUtilizador
    public void removeUtilizador(String codUtilizador) throws RemoveException{
        vintage.removeUtilizador(codUtilizador);
    }

    //getUtilizador
    public Utilizador getUtilizador(String codUtilizador) throws GetException{
        return vintage.getGestorUtilizadores().getUtilizador(codUtilizador);
    }

    //addTransportadora
    public void addTransportadora(Transportadora transportadora) throws AddException{
        vintage.addTransportadora(transportadora);
    }

    //removerTransportadora
    public void removeTransportadora(String nome) throws RemoveException{
        vintage.removeTransportadora(nome);
    }

    //getTransportadora
    public Transportadora getTransportadora(String nome) throws GetException{
        return vintage.getGestorTransportadoras().getTransportadora(nome);
    }

    //toString_Transportadoras
    public String toString_Transportadoras(){
        return vintage.getGestorTransportadoras().toString();
    }

    //getArtigo
    public Artigo getArtigo(String codBarras) throws GetException{
        return vintage.getGestorArtigos().getArtigo(codBarras);
    }

    //exists_Utilizador
    public boolean exists_Utilizador(String codUtilizador){
        return vintage.getGestorUtilizadores().getUtilizadores().containsKey(codUtilizador);
    }

    //artigos_AVenda
    public String artigos_AVenda_Vintage(){
        return vintage.artigos_AVenda();
    }

    //addEncomenda_ArtigoVintage
    public void addEncomenda_withArtigo_Vintage(Encomenda encomenda,Artigo artigo,
    Utilizador comprador) throws GetException, AddException, RemoveException{
        vintage.addEncomenda_withArtigo(encomenda,artigo,comprador);
    }

    //addArtigo_VendedorVintage
    public void addArtigo_VendedorVintage(Artigo artigo,Utilizador vendedor) throws 
    GetException, AddException, RemoveException{
        vintage.addArtigo_withVendedor(artigo,vendedor);
    }
}