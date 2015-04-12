package servernode.example.com.projetm1;

import com.projet.M1.Module.Module;
import com.projet.M1.action.Action;
import com.projet.M1.evenement.Capteur;

/**
 * Created by clement on 08/04/2015.
 */
public interface Communicator {

    public Capteur getCapteur();
    public void setCapteur(Capteur capteur);
    public Action getAction();
    public void setAction(Action capteur);
    public void addModule(Module module);

}
