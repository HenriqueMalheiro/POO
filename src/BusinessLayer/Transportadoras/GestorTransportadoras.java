import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class GestorTransportadoras implements Serializable{
    private Map<String, Transportadora> transportadoras; //nome e a chave

    // Construtores
    public GestorTransportadoras() {
        this.transportadoras = new HashMap<>();
    }

    public GestorTransportadoras(Map<String, Transportadora> novo) {
        this.transportadoras = new HashMap<>();
        for (Map.Entry<String, Transportadora> entry : novo.entrySet()) {
            this.transportadoras.put(entry.getKey(), entry.getValue().clone());
        }
    }

    public GestorTransportadoras(GestorTransportadoras gereGereTransportadoras) {
        this.transportadoras = gereGereTransportadoras.getTransportadoras();
    }

    // get
    public Map<String, Transportadora> getTransportadoras() {
        Map<String, Transportadora> novo = new HashMap<>();
        for (Map.Entry<String, Transportadora> entry : this.transportadoras.entrySet()) {
            novo.put(entry.getKey(), entry.getValue().clone());
        }
        return novo;
    }

    // set
    public void setTransportadoras(Map<String, Transportadora> transportadoras) {
        this.transportadoras = new HashMap<>();
        for (Map.Entry<String, Transportadora> entry : transportadoras.entrySet()) {
            this.transportadoras.put(entry.getKey(), entry.getValue().clone());
        }
    }

    // clone
    public GestorTransportadoras clone() {
        return new GestorTransportadoras(this);
    }

    // equals
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if ((o == null) || (this.getClass() != o.getClass()))
            return false;
        GestorTransportadoras gt = (GestorTransportadoras) o;
        return (gt.getTransportadoras().equals(this.transportadoras));
    }

    // toString
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(transportadoras.size()).append("\n");

        for (Map.Entry<String, Transportadora> entry : transportadoras.entrySet()) {
            sb.append(entry.getKey().toString()).append("\n");
        }

        return sb.toString();
    }

    // Outros métodos
    public void addTransportadora(Transportadora transportadora) throws AddException{
        Transportadora previousValue = transportadoras.putIfAbsent(transportadora.getNome(),transportadora.clone());
        if (previousValue != null) { //se a chave já existir no map
            throw new AddException("Já existe no sistema uma Transportadora com esse nome!");
        }  
    }

    public void removeTransportadora(String nome) throws RemoveException{
        Transportadora transportadoraRemovida = transportadoras.remove(nome);
        if (transportadoraRemovida == null){
            throw new RemoveException("Não existe uma Transportadora com esse nome!");
        }
    }

    public Transportadora getTransportadora(String nome) throws GetException{
        Transportadora transportadora = transportadoras.get(nome);
        if (transportadora == null){
            throw new GetException("Não existe uma Transportadora com esse nome!");
        }
        return transportadora.clone();
    }

}
