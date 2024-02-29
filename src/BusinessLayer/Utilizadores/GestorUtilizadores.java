import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class GestorUtilizadores implements Serializable{
    private Map<String,Utilizador> utilizadores;

    //Contrutores
    public GestorUtilizadores(){
        this.utilizadores= new HashMap<>();
    }

    public GestorUtilizadores(Map<String,Utilizador> novo) {
        this.utilizadores = new HashMap<>();
        for (Map.Entry<String,Utilizador> entry: novo.entrySet()){
            this.utilizadores.put(entry.getKey(), entry.getValue().clone());
        }
    }

    public GestorUtilizadores(GestorUtilizadores gereUtilizador) {
        this.utilizadores = gereUtilizador.getUtilizadores();
    }

    //get
    public Map<String, Utilizador> getUtilizadores() {
        Map<String,Utilizador> novo = new HashMap<>();
        for(Map.Entry<String,Utilizador> entry: this.utilizadores.entrySet()){
            novo.put(entry.getKey(),entry.getValue().clone());
        }
        return novo;
    }

    //set
    public void setUtilizadores(Map<String,Utilizador> utilizadores) {
        this.utilizadores=new HashMap<>();
        for(Map.Entry<String,Utilizador> entry: utilizadores.entrySet()){
            this.utilizadores.put(entry.getKey(),entry.getValue().clone());
        }
    }

    //clone
    public GestorUtilizadores clone(){
        return new GestorUtilizadores(this);
    }

    //equals
    public boolean equals(Object o){
        if (this==o) 
            return true;
        if ((o==null) || (this.getClass() != o.getClass())) 
            return false;
        GestorUtilizadores utilizador = (GestorUtilizadores) o;
        return (utilizador.getUtilizadores().equals(this.utilizadores));
    }

    //toString
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(utilizadores.size()).append("\n");
    
        for(Map.Entry<String,Utilizador> entry: utilizadores.entrySet()){
            sb.append(entry.getKey().toString()).append("\n");
        }
    
        return sb.toString();
    }

    //Outros métodos
    public void addUtilizador(Utilizador utilizador) throws AddException{
        Utilizador previousValue = utilizadores.putIfAbsent(utilizador.getCodUtilizador(),utilizador.clone());
        if (previousValue != null) { //se a chave já existir no map
            throw new AddException("Já existe no sistema um utilizador com esse código de Utilizador!");
        }
    }

    public void removeUtilizador(String codUtilizador) throws RemoveException{
        Utilizador utilizadorRemovido = this.utilizadores.remove(codUtilizador);
        if (utilizadorRemovido == null){
            throw new RemoveException("Não existe um utilizador com esse codigo de Utilizador!");
        }
    }

    public Utilizador getUtilizador(String codUtilizador) throws GetException{
        Utilizador utilizador = utilizadores.get(codUtilizador);
        if (utilizador == null){
            throw new GetException("Não existe um utilizador com esse codigo de Utilizador!");
        }

        return utilizador.clone();
    }

    //artigos_AVenda
    public String artigos_AVenda(){
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String,Utilizador> entry: utilizadores.entrySet()){
            sb.append(entry.getValue().artigos_AVenda());
        }
        return sb.toString();
    }

    //setHistorico_Comprador
    public void setHistorico_Comprador(Utilizador comprador,Artigo artigo) throws AddException{
        utilizadores.get(comprador.getCodUtilizador()).artigo_Comprado(artigo);
    }

    //setHistorico_Vendedor
    public void setHistorico_Vendedor(Utilizador vendedor,Artigo artigo) throws AddException, RemoveException{
        utilizadores.get(vendedor.getCodUtilizador()).artigo_Vendido(artigo);
    }


    //set_aVenda
    public void set_aVenda(Utilizador vendedor,Artigo artigo) throws AddException, RemoveException{
        utilizadores.get(vendedor.getCodUtilizador()).artigo_aVenda(artigo);
    }
}