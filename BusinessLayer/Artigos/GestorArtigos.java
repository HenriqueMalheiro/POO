import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class GestorArtigos implements Serializable{
    private Map<String,Artigo> artigos;

    //Contrutores
    public GestorArtigos(){
        this.artigos= new HashMap<>();
    }
    
    public GestorArtigos(Map<String,Artigo> novo) {
        this.artigos = new HashMap<>();
        for (Map.Entry<String,Artigo> entry: novo.entrySet()){
            this.artigos.put(entry.getKey(), entry.getValue().clone());
        }
    }

    public GestorArtigos(GestorArtigos gereArtigo) {
        this.artigos = gereArtigo.getArtigos();
    }

    //get
    public Map<String, Artigo> getArtigos() {
        Map<String,Artigo> novo = new HashMap<>();
        for(Map.Entry<String,Artigo> entry: this.artigos.entrySet()){
            novo.put(entry.getKey(),entry.getValue().clone());
        }
        return novo;
    }

    //set
    public void setArtigo(Map<String,Artigo> artigos) {
        this.artigos=new HashMap<>();
        for(Map.Entry<String,Artigo> entry: artigos.entrySet()){
            this.artigos.put(entry.getKey(),entry.getValue().clone());
        }
    }

    //clone
    public GestorArtigos clone(){
        return new GestorArtigos(this);
    }

    //equals
    public boolean equals(Object o){
        if (this==o) 
            return true;
        if ((o==null) || (this.getClass() != o.getClass())) 
            return false;
        GestorArtigos artigo = (GestorArtigos) o;
        return (artigo.getArtigos().equals(this.artigos));
    }

    //toString
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(artigos.size()).append("\n");
    
        for(Map.Entry<String,Artigo> entry: artigos.entrySet()){
            sb.append(entry.getKey().toString()).append("\n");
        }
    
        return sb.toString();
    }

    //Outros métodos
    public String toString_withoutSize(){
        StringBuilder sb = new StringBuilder();
    
        for(Map.Entry<String,Artigo> entry: artigos.entrySet()){
            sb.append(entry.getKey().toString()).append("\n");
        }
    
        return sb.toString();
    }

    public void addArtigo(Artigo artigo) throws AddException{
        Artigo previousValue = artigos.putIfAbsent(artigo.getCod_barras(),artigo.clone());
        if (previousValue != null) { //se a chave já existir no map
            throw new AddException("Já existe no sistema um artigo com esse código de barras!");
        }
    }

    public void removeArtigo(String codBarras) throws RemoveException{
        Artigo artigoRemovido = this.artigos.remove(codBarras);
        if (artigoRemovido == null){
            throw new RemoveException("Não existe um artigo com esse codigo de barras");
        }
    }

    public Artigo getArtigo(String cod_barras) throws GetException{
        Artigo artigo = this.artigos.get(cod_barras);
        if (artigo == null){
            throw new GetException("Não existe um artigo com esse codigo de barras");
        }
        return artigo.clone();
    }

    public double calcularPrecoFinalEncomenda(Encomenda encomenda) {
        int quantidadeNovos = 0;
        int quantidadeUsados = 0;
        double precototal = 0;
        GestorArtigos gestorArtigos=encomenda.getGestorArtigos();
        for (Map.Entry<String, Artigo> entry:gestorArtigos.getArtigos().entrySet()) {
            if (entry.getValue().getArtigo_novo()) {
                quantidadeNovos++;
            }else {
                quantidadeUsados++;
            }
            precototal += entry.getValue().getPreco_final();
        }
        return ((precototal + (quantidadeNovos * 0.5) + (quantidadeUsados * 0.25)) + encomenda.getTaxaGarantia() + encomenda.getCustoExpedicao());
    }

    public double ValorFaturado_Encomenda(){
        double valor=0;
        for(Map.Entry<String, Artigo> entry:this.artigos.entrySet()){
            valor+=entry.getValue().getPreco_base();
        }
        return valor;
    }
}