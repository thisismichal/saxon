package saxoncs;

import net.sf.saxon.Configuration;
import net.sf.saxon.Transform;
import net.sf.saxon.lib.LocalizerFactory;
import net.sf.saxon.lib.Numberer;
import net.sf.saxon.s9api.Processor;

public class SaxonCS {
    public static void main(String[] args) {
        SaxonCS.transform(args);

    }
    
    public static void transform(String[] args) {
        Configuration config = new Configuration();
        config.setLocalizerFactory(new LocalizerFactory() {
            public Numberer getNumberer(String language, String country) {
                if (language.equals("cs")) {
                    return new Numberer_cs();
                }
                return null;
            }
        });
        
        Transform_cs tr = new Transform_cs();
        tr.initializeConfiguration(config);
        tr.doTransform(args, "");
    }
}
