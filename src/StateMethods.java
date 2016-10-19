import javax.crypto.Mac;

/**
 * Created by NiGHTRiDER on 19/10/16.
 */
public interface StateMethods {

    void rendreMonnaie(MachineACafe m);
    void selectionnerBoisson(ToucheBoisson t, MachineACafe c);
    void entrerMonnaie(Piece piece ,MachineACafe m) ;
}
