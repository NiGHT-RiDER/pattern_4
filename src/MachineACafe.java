public class MachineACafe {

    private STATE state = STATE.IDLE;

	private int montantEnCours = 0;
	private ToucheBoisson boisson = null;

	public void afficherMontant() {
		System.out.println(montantEnCours + " cents disponibles");
	}
	
	public void afficherRetour() {
		System.out.println(montantEnCours + " cents rendus");
	}
	
	public void afficherPasAssez(ToucheBoisson t) {
		System.out.println("Vous n'avez pas introduit un montant suffisant pour un " + t);
		System.out.println("Il manque encore " + (t.getPrix() - montantEnCours) + " cents");
	}

	public void afficherBoisson(ToucheBoisson t) {
		System.out.println("Voici un " + t);
		
	}

	public void entrerMonnaie(Piece piece) {
        state.entrerMonnaie(piece , this);
	}
	
	public void selectionnerBoisson(ToucheBoisson t) {
        state.selectionnerBoisson(t , this);
	}
	
	public void rendreMonnaie() {
        state.rendreMonnaie(this);
	}

	private enum STATE implements StateMethods{
		IDLE {
            @Override
            public void selectionnerBoisson(ToucheBoisson t, MachineACafe c) {
                c.afficherPasAssez(t);
            }
        } ,
		PAS_ASSEZ {
            @Override
            public void entrerMonnaie(Piece piece , MachineACafe m) {
                m.montantEnCours += piece.getValeur();
                m.afficherMontant();
                m.state = COLLECTE;
                if (m.boisson.getPrix() > m.montantEnCours) {
                    m.afficherPasAssez(m.boisson);
                } else {
                    m.montantEnCours -= m.boisson.getPrix();
                    m.afficherBoisson(m.boisson);
                    m.boisson = null;
                    m.afficherMontant();
                    if (m.montantEnCours == 0)
                        m.state = IDLE;
                    else
                        m.state = COLLECTE;
                }

            }
        },
		COLLECTE{
            @Override
            public void selectionnerBoisson(ToucheBoisson t, MachineACafe c) {
                if (t.getPrix() > c.montantEnCours) {
                    c.boisson = t;
                    c.afficherPasAssez(c.boisson);
                    c.boisson = t;
                    c.state = PAS_ASSEZ;
                    return;
                }
                c.montantEnCours -= t.getPrix();
                c.afficherBoisson(t);
                c.afficherMontant();
                if (c.montantEnCours == 0)
                    c.state = IDLE;
                else
                    c.state = COLLECTE;
            }
        };

        public void entrerMonnaie(Piece piece, MachineACafe m) {
            m.montantEnCours += piece.getValeur();
            m.afficherMontant();
            m.state = COLLECTE;
        }

        public void rendreMonnaie(MachineACafe m) {
            m.afficherRetour();
            m.montantEnCours = 0;
            m.boisson = null;
        }

        public void selectionnerBoisson(ToucheBoisson t, MachineACafe c){
            throw new IllegalStateException();
        }

    }
}
