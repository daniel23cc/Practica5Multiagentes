package es.ujaen.ssmmaa.ontomouserun;

import jade.content.onto.BeanOntology;
import jade.content.onto.BeanOntologyException;
import jade.content.onto.Ontology;

public class OntoMouseRun extends BeanOntology {
    private static final long serialVersionUID = 1L;

    // NOMBRE
    public static final String ONTOLOGY_NAME = "Ontologia_Mouse_Run";

    // The singleton instance of this ontology
    private static Ontology INSTANCE;

    public synchronized final static Ontology getInstance() throws BeanOntologyException {

        if (INSTANCE == null) {
            INSTANCE = new OntoMouseRun();
        }

        return INSTANCE;
    }

    /**
     * Constructor
     *
     * @throws BeanOntologyException
     */
    private OntoMouseRun() throws BeanOntologyException {

        super(ONTOLOGY_NAME);

        add("es.ujaen.ssmmaa.ontomouserun");
    }
}
